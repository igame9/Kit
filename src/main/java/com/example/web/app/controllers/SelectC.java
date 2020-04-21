package com.example.web.app.controllers;

import com.example.web.app.api.request.InputRequest;
import com.example.web.app.api.request.Man;
import com.example.web.app.api.request.UserByIdRequest;
import com.example.web.app.api.request.UserByIdRequestB;
import com.example.web.app.dao.DbSqlite;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SelectC {
    private final DbSqlite dbSqlite;

    public SelectC(DbSqlite dbSqlite) {
        this.dbSqlite = dbSqlite;
    }

    public static final String REQUEST_METHOD_VIEW_NAME = "request_method";

    @ApiOperation(value = "Выбор пользователя по id из БД")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InputRequest.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "SelectUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String requestMethodPost(@RequestBody UserByIdRequest userByIdRequest) { //requestbody во в счех пост должно быть ,отправляем в модель greeting
        Gson gs = new Gson();
        int curid = userByIdRequest.getId();
        List<Integer> allid = dbSqlite.getUsersId();
        int last = allid.get(allid.size() - 1);
        if (curid > last) {
            Man lastmanindb = dbSqlite.selectUserById(last);
            return gs.toJson(lastmanindb);
        }
        Man man = dbSqlite.selectUserById(curid);
        return gs.toJson(man);
    }

    @ApiOperation(value = "Проверка id на наличие в БД для функции next() js")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InputRequest.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "CheckidF", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String Checkid(@RequestBody UserByIdRequest userByIdRequest) {
        Gson gs = new Gson();
        Integer curid = userByIdRequest.getId();
        Man man = dbSqlite.selectUserById(userByIdRequest.getId());
        List<Integer> ids = man.getAllid();
        int last = ids.get(ids.size() - 1);
        if (curid == last) {
            return gs.toJson(last);
        }
        int index = ids.indexOf(curid);
        int inc = index + 1;
        int id = ids.get(inc);
        return gs.toJson(id);
    }

    @ApiOperation(value = "Проверка id на наличие в БД для функции back() js")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InputRequest.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "CheckidB", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String Checkid(@RequestBody UserByIdRequestB userByIdRequestB) {
        Gson gs = new Gson();
        Integer curid = userByIdRequestB.getId();
        Man man = dbSqlite.selectUserById(userByIdRequestB.getId());
        List<Integer> ids = man.getAllid();
        int last = 1;
        if (ids.indexOf(curid) == 0) {
            return gs.toJson(last);
        }
        int index = ids.indexOf(curid);
        int dec = index - 1;
        int id = ids.get(dec);
        return gs.toJson(id);
    }

    @ApiOperation(value = "Загрузка формы пользователя на его страницу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InputRequest.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "GetMe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String GetMe(@RequestBody InputRequest inputRequest) {
        Gson gs = new Gson();
        String nick = SecurityContextHolder.getContext().getAuthentication().getName();
        Man man = DbSqlite.selectUserByLogin(nick);

        return gs.toJson(man);
    }
    @ApiOperation(value = "Изменение профиля пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InputRequest.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "changeDB", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String changeUser(@RequestBody InputRequest inputRequest) {
        Gson gs = new Gson();
        Man man = new Man();
        man.setFamily(inputRequest.getFamily());
        man.setName(inputRequest.getName());
        man.setUniversity(inputRequest.getUniversity());
        man.setSecondname(inputRequest.getSecondname());
        man.setGroup(inputRequest.getGroup());
        man.setAge(inputRequest.getAge());
        man.setCourse(inputRequest.getCourse());
        man.setLogin(inputRequest.getLogin());

        String name = man.getName();
        String fam = man.getFamily();
        String secondName = man.getSecondname();
        String university = man.getUniversity();
        Integer age = man.getAge();
        Integer course = man.getCourse();
        String group = man.getGroup();
        String login = man.getLogin();
        String succes = "Профиль изменен";
        String me = SecurityContextHolder.getContext().getAuthentication().getName();
        if(checkchangeableData(name,fam,secondName,university,age,course,group,login).isEmpty()){
            DbSqlite.changeTable(name,fam,secondName,university,age,course,group,login,me);
            return gs.toJson(succes);
        }
        else{
            return gs.toJson(checkchangeableData(name,fam,secondName,university,age,course,group,login));
        }
    }
    public List<String> checkchangeableData(String name,String family,String secondname,String university,Integer age,Integer course,String group,String login) {

        List<String> errors = new ArrayList<>();

        if(name == null || name.trim().isEmpty()||name.length()<4) errors.add("Некорректное имя");
        if(family == null || name.trim().isEmpty()||name.length()<4) errors.add("Некорректное имя");
        if(secondname == null || name.trim().isEmpty()||name.length()<4) errors.add("Некорректное имя");
        if(university == null || university.trim().isEmpty()) errors.add("Некорректное место учебы ");
        if(group == null || group.trim().isEmpty())errors.add("Некорректный номер группы");
        if(login == null || login.trim().isEmpty()||login.length() > 6)errors.add("Логин не может превышать 6 символов");
        if(course > 6) errors.add("Номер курса не может быть больше 6 ");
        if(age > 120 || age <= 0) errors.add("Возраст не может быть больше 120 лет или меньше (или равный) 0");

        return errors;
    }

}
