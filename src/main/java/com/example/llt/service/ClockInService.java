package com.example.llt.service;


import com.example.llt.dao.ClockInDao;
import com.example.llt.entity.ClockIn;
import com.example.llt.entity.Result;
import com.example.llt.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ClockInService {

    private ClockInDao clockInDao;

    public ClockInService(ClockInDao clockInDao) {
        this.clockInDao = clockInDao;
    }

    public List<ClockIn> getClockInById(Long id){
        return clockInDao.getClockInById(id);
    }

    public Boolean hasClockIn(Long id){
        return clockInDao.getTodayClockInCountById(id) > 0;
    }

    public Result getClockInRate(){
        List<String> recentClockInDate = clockInDao.getRecentClockInDate();
        List<Double> recentClockInRate = clockInDao.getRecentClockInRate();
        Collections.reverse(recentClockInDate);
        Collections.reverse(recentClockInRate);
        Map<String, Object> map = new HashMap<>();
        map.put("clockInDate", recentClockInDate);
        map.put("clockInRate", recentClockInRate);
        return Result.success(map);
    }

    public int insertClockIn(Long userId, String userName){
        //获取打卡时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        //生成打卡id
        Long id = new SnowflakeIdUtils(1,1,1).nextId();
        //生成clockIn对象交给dao层
        ClockIn clockIn = new ClockIn(id, userId, userName, time);
        return clockInDao.insertClockIn(clockIn);
    }

    public Integer getTodayClockInCount(){
        return clockInDao.getTodayClockInCount();
    }

    public Integer insertClockInRate(String date, Double rate){
        return clockInDao.insertClockInRate(date, rate);
    }
}
