package com.example.web.app.controllers;

import com.example.web.app.api.request.Greeting;
import com.example.web.app.api.request.Man;
import com.example.web.app.api.request.UserByIdRequest;
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
import java.util.List;

@RestController
public class SelectC {
    private final DbSqlite dbSqlite;

    public SelectC(DbSqlite dbSqlite) {
        this.dbSqlite = dbSqlite;
    }

    public static final String REQUEST_METHOD_VIEW_NAME = "request_method";

    @ApiOperation(value = "Получить словарь значений по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Greeting.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "SelectUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String requestMethodPost(@RequestBody UserByIdRequest userByIdRequest) { //requestbody во в счех пост должно быть ,отправляем в модель greeting
        Gson gs = new Gson();
        Man man = dbSqlite.selectUserById(userByIdRequest.getId());
        return gs.toJson(man);
    }

    @ApiOperation(value = "Получить словарь значений по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Greeting.class),
            @ApiResponse(code = 400, message = "Ошибка валидации входных параметров"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")})
    @RequestMapping(value = "Checkid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String Checkid(@RequestBody UserByIdRequest userByIdRequest) {
        Gson gs = new Gson();
        Man man = dbSqlite.selectUserById(userByIdRequest.getId());
        Integer curid = userByIdRequest.getId();
        List<Integer> ids = man.getAllid();
        int index = ids.indexOf(curid);
        int inc = index + 1;
        int id = ids.get(inc);

      /*  if (ids.contains(curid)){
            System.out.println(ids);
            return gs.toJson(curid);
        }
        else{
            return gs.toJson(usr.getError());
        }*/
        return gs.toJson(id);
    }
}