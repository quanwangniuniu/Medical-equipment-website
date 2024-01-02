package com.example.llt.service;


import com.example.llt.dao.EmployeeDao;
import com.example.llt.entity.Employee;
import com.example.llt.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import javax.servlet.http.HttpServletRequest;

@Service
public class CheckLoginService {
    //构造器注入employeeDao
    private final EmployeeDao employeeDao;

    public CheckLoginService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Result checkLogin(String username, String password, HttpServletRequest req) {
        // 1. 将用户密码进行MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2.根据username查询数据库
        Employee employee = employeeDao.getEmployeeByUsername(username);

        // 3. 判断用户是否存在
        if(employee == null){
            return Result.fail("用户不存在");
        }

        // 4. 密码对比
        if(!md5Password.equals(employee.getPassword())){
            return Result.fail("密码错误");
        }

        if(employee.getStatus() == 1){
            return Result.fail("账号被禁用");
        }

        // 5. 密码正确则成功登录，将员工id存入session
        req.getSession().setAttribute("userId",employee.getId());
        req.getSession().setAttribute("name",employee.getName());
        return Result.success(employee);
    }
}
