package com.example.llt.controller;


import com.alibaba.fastjson.JSON;
import com.example.llt.entity.Result;
import com.example.llt.service.CheckLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping("/login")
public class LoginController{
    private final CheckLoginService checkLoginService;

    public LoginController(CheckLoginService checkLoginService) {
        this.checkLoginService = checkLoginService;
    }

    @PostMapping
    public String login(@RequestBody Map<String, String> loginInfo, HttpServletRequest request){
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        Result result = checkLoginService.checkLogin(username, password, request);
        return JSON.toJSONString(result);
    }

}


