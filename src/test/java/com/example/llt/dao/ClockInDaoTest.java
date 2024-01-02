package com.example.llt.dao;

import com.example.llt.entity.ClockIn;
import com.example.llt.utils.SnowflakeIdUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClockInDaoTest {
    @Autowired
    ClockInDao clockInDao;

    @Test
    void getClockInById() {
        List<ClockIn> clockInList = clockInDao.getClockInById(12345L);
        clockInList.forEach(System.out::println);

    }

    @Test
    void insertClockIn() {
        ClockIn clockIn = new ClockIn(12345L, null, null, null);
        clockInDao.insertClockIn(clockIn);
    }

    @Test
    void getTodayClockInCount(){
        Integer todayClockInCount = clockInDao.getTodayClockInCountById(2L);
        System.out.println(todayClockInCount);
    }

    @Test
    void insertClockInRate(){
        List<String> recentDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 30; i++) {
            recentDates.add(currentDate.minusDays(i).format(formatter));
        }

        for(int i = 29 ; i >= 0 ; i--){
            double randomNum = 0.2 * Math.random() + 0.8;
            clockInDao.insertClockInRate(recentDates.get(i), randomNum);
        }
    }
}