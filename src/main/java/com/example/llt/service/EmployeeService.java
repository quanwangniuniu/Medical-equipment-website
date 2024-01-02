package com.example.llt.service;

import com.example.llt.dao.EmployeeDao;
import com.example.llt.entity.Employee;
import com.example.llt.entity.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Result getAllEmployee(){
        List<Employee> employeeList = employeeDao.getALlEmployee();
        return Result.success(employeeList);
    }

    public Result insertEmployee(Employee employee){
        Integer integer = employeeDao.insertEmployee(employee);
        return Result.success(integer);
    }

    public Result alterStatus(Long id){
        Integer integer = employeeDao.alterEmployeeStatus(id);
        return Result.success(integer);
    }

    public Integer getEmployeeCount(){
        return employeeDao.getEmployeeCount();
    }
}