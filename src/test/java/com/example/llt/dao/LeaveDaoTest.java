package com.example.llt.dao;

import com.example.llt.entity.Leave;
import com.example.llt.utils.RandomDateGenerator;
import com.example.llt.utils.SnowflakeIdUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LeaveDaoTest {
    @Autowired
    LeaveDao leaveDao;

    @Test
    void getAllLeaveRequests() {
        ArrayList<Leave> leaves = leaveDao.getAllLeaveRequests();
        leaves.forEach(System.out::println);
    }

    @Test
    void getLeaveById() {
        ArrayList<Leave> leaves = leaveDao.getLeaveById(12345L);
        leaves.forEach(System.out::println);
    }

    @Test
    void insertLeaveRequest() {
        SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(1, 1, 1);
        Random random = new Random();
        for(int i = 0 ; i < 100 ; i++){
            Long id = snowflakeIdUtils.nextId();
            String time = RandomDateGenerator.generateRandomDate();
            Integer type = random.nextInt(2);
            leaveDao.insertLeaveRequest(new Leave(id, null, null, time, time, null, type, 1));
        }
    }


    @Test
    void getRecentYearLeaveCount(){
        List<Integer> arr = leaveDao.getRecentYearLeaveCount();
        arr.forEach(System.out::println);
    }

    @Test
    void updateLeaveRequestStatusById(){
        leaveDao.updateLeaveRequestStatusById(12345L,1);
    }
}