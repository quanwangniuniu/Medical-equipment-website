package com.example.llt.controller;

import com.alibaba.fastjson.JSON;
import com.example.llt.entity.ClockIn;
import com.example.llt.entity.Result;
import com.example.llt.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/clockIn")
public class ClockInController {
    private final ClockInService clockInService;

    public ClockInController(ClockInService clockInService) {
        this.clockInService = clockInService;
    }

    @GetMapping()
    public String getAllClockIn(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 设置utf-8编码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        // 从session中读取当前打卡用户的id
        Long id = (Long) req.getSession().getAttribute("userId");
        // 读取当前用户的所有打卡记录
        List<ClockIn> clockInList = clockInService.getClockInById(id);
        // 将该用户的所有打卡记录放入request中
        req.setAttribute("clockIn",clockInList);
        // 查询今天是否已经打卡
        Boolean hasClockIn = clockInService.hasClockIn(id);
        req.setAttribute("hasClockIn", hasClockIn);
        // 转发到打卡页面的jsp
        req.getRequestDispatcher("/pages/employee/clockIn.jsp").forward(req,resp);
        return "pages/employee/clockIn";

    }

    @PostMapping()
    public String addClockIn(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 设置utf-8编码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        // 从session中读取当前打卡用户的id和用户名
        Long id = (Long) req.getSession().getAttribute("userId");
        String name = (String) req.getSession().getAttribute("name");
        // 调用service层中的insertClockIn方法新增打卡记录
        int result = clockInService.insertClockIn(id, name);
        // 在request中放入打卡结果
        req.setAttribute("result",result);
        // 获取所有打卡记录放入request中
        List<ClockIn> clockInList = clockInService.getClockInById(id);
        req.setAttribute("clockIn",clockInList);
        // 查询今天是否已经打卡
        Boolean hasClockIn = clockInService.hasClockIn(id);
        req.setAttribute("hasClockIn", hasClockIn);
        // 转发到打卡页面的jsp
        return "pages/employee/clockIn";
    }

    @GetMapping("/rate")
    @ResponseBody
    @CrossOrigin
    public String getClockInRate(){
        Result result = clockInService.getClockInRate();
        return JSON.toJSONString(result);
    }
}
