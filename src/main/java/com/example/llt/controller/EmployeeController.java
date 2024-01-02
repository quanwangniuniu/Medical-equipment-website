package com.example.llt.controller;

import com.alibaba.fastjson.JSON;
import com.example.llt.entity.Employee;
import com.example.llt.entity.Result;
import com.example.llt.service.EmployeeService;
import com.example.llt.utils.SnowflakeIdUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/employee", produces = "application/json;charset=utf-8")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
            this.employeeService = employeeService;
        }

        @GetMapping
        public String getAllEmployees(){
            Result result = employeeService.getAllEmployee();
        return JSON.toJSONString(result);
    }

    @PostMapping
    public String addEmployee(@RequestBody String employeeJson){
        String str = URLDecoder.decode(employeeJson);
        str = str.substring(0, str.length() - 1);
        Employee employee = JSON.parseObject(str, Employee.class);
        employee.setId(new SnowflakeIdUtils(1,1,1).nextId());
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateformat.format(new Date());
        employee.setCreate_time(time);
        Result result = employeeService.insertEmployee(employee);
        return JSON.toJSONString(result);
    }

    @PutMapping ("/{id}")
    public String alterStatus(@PathVariable String id){
        Result result = employeeService.alterStatus(Long.valueOf(id));
        return JSON.toJSONString(result);
    }

}
