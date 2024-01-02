package com.example.llt.dao;

import com.example.llt.entity.Employee;
import com.example.llt.utils.SnowflakeIdUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeDaoTest {

    @Autowired
    EmployeeDao employeeDao;

    @Test
    void getEmployeeByUsername() {
        Employee admin = employeeDao.getEmployeeByUsername("admin");
        System.out.println(admin);
    }

    @Test
    void getALlEmployee() {
        List<Employee> employeeList = employeeDao.getALlEmployee();
        employeeList.forEach(System.out::println);
    }

    @Test
    void insertEmployee() {
        Employee employee = new Employee(null, "啊啊", "test", null, 22, null, 0, null, 0, 0);
        employee.setId(new SnowflakeIdUtils(1,1,1).nextId());
        employeeDao.insertEmployee(employee);
    }

    @Test
    void alterEmployeeStatus() {
    }
}