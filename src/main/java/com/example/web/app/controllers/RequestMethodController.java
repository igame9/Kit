package com.example.web.app.controllers;

import com.example.web.app.api.request.InputRequest;
import com.example.web.app.api.request.Man;
import com.example.web.app.dao.DbSqlite;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class RequestMethodController {
    private final DbSqlite dbSqlite;

    public RequestMethodController(DbSqlite dbSqlite) {
        this.dbSqlite = dbSqlite;
    }
    public static final String REQUEST_METHOD_VIEW_NAME = "request_method";


    @ApiOperation(value = "Получить словарь значений по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InputRequest.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "requestmethod", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String requestMethodGet() {
        return "/index";
    }


    @ApiOperation(value = "Регистация")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InputRequest.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "requestmethod", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String requestMethodPost(@RequestBody InputRequest inputRequest) { //requestbody во в счех пост должно быть ,отправляем в модель greeting
        Man man = new Man();
        Gson gs = new Gson();
        man.setFamily(inputRequest.getFamily());
        man.setName(inputRequest.getName());
        man.setUniversity(inputRequest.getUniversity());
        man.setSecondname(inputRequest.getSecondname());
        man.setGroup(inputRequest.getGroup());
        man.setAge(inputRequest.getAge());
        man.setCourse(inputRequest.getCourse());
        man.setLogin(inputRequest.getLogin());
        man.setPassword(inputRequest.getPassword());
        man.setGender(inputRequest.getGender());
        man.setKindeducation(inputRequest.getKindeducation());
        man.setRole("USER");

        String name = man.getName();
        String fam = man.getFamily();
        String secondName = man.getSecondname();
        String university = man.getUniversity();
        Integer age = man.getAge();
        Integer course = man.getCourse();
        String group = man.getGroup();
        String login = man.getLogin();
        String gender = man.getGender();
        String kindofeducation = man.getKindeducation().trim();
        BCryptPasswordEncoder coder = new BCryptPasswordEncoder();
        String password = man.getPassword();
        String coderpass = coder.encode(password);
        String role = man.getRole();
        String succes = "Вы зарегистрированы";

       if(checkData(name,fam,secondName,university,age,course,group,login,password,role).isEmpty()){
           DbSqlite.insertMan(name,fam,secondName,university,age,course,group,login,coderpass,gender,kindofeducation,role);
           return  gs.toJson(succes);

       }
       else{
           return gs.toJson(checkData(name,fam,secondName,university,age,course,group,login,password,role));
       }


    }
    public List<String> checkData(String name, String fam, String secondName, String university, Integer age, Integer course, String group, String login, String password, String role) {

        List<String> errors = new ArrayList<>();
        List<String> nick = dbSqlite.getUsersLogin();

        if(name == null || name.trim().isEmpty()||name.length()<4) errors.add("Некорректное имя");
        if(fam == null || fam.trim().isEmpty()) errors.add("Некорректная фамилия");
        if(secondName == null || secondName.trim().isEmpty()) errors.add("Некорректное отчество");
        if(university == null || university.trim().isEmpty()) errors.add("Некорректное место учебы ");
        if(group == null || group.trim().isEmpty())errors.add("Некорректный номер группы");
        if(login == null || login.trim().isEmpty()||login.length() > 6)errors.add("Логин не может превышать 6 символов");
        if(password == null || password.trim().isEmpty()||password.length() != 4)errors.add("Пароль должен содержать 4 цифры");
        if(course > 6) errors.add("Номер курса не может быть больше 6 ");
        if(age > 120) errors.add("Возраст не может быть больше 120 лет");
        if(role == null || role.trim().isEmpty())errors.add("Ошибка роли");
        if(nick.contains(login))errors.add("Такой логин уже существует");

        return errors;
    }

}