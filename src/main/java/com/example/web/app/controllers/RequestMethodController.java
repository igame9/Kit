package com.example.web.app.controllers;

import com.example.web.app.api.request.Greeting;
import com.example.web.app.api.request.Man;
import com.example.web.app.dao.DbSqlite;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RequestMethodController {
    private final DbSqlite dbSqlite;

    public RequestMethodController(DbSqlite dbSqlite) {
        this.dbSqlite = dbSqlite;
    }
    public static final String REQUEST_METHOD_VIEW_NAME = "request_method";

    @ApiOperation(value = "Получить словарь значений по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Greeting.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "requestmethod", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String requestMethodGet() {
        return "/index";
    }


    @ApiOperation(value = "Получить словарь значений по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Greeting.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "requestmethod", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String requestMethodPost(@RequestBody Greeting greeting) { //requestbody во в счех пост должно быть ,отправляем в модель greeting
        Man man = new Man();
        Gson gs = new Gson();
        man.setFamily(greeting.getFamily());
        man.setName(greeting.getName());
        man.setUniversity(greeting.getUniversity());
        man.setSecondname(greeting.getSecondname());
        man.setGroup(greeting.getGroup());
        man.setAge(greeting.getAge());
        man.setCourse(greeting.getCourse());


        String name = man.getName();
        String fam = man.getFamily();
        String secondName = man.getSecondname();
        String university = man.getUniversity();
        int age = man.getAge();
        int course = man.getCourse();
        String group = man.getGroup();


        DbSqlite.insertMan(name,fam,secondName,university,age,course,group);
    return gs.toJson(greeting);

    }
}