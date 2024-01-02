package com.example.llt.service;

import com.example.llt.dao.LeaveDao;
import com.example.llt.entity.Leave;
import com.example.llt.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveService {
    private final LeaveDao leaveDao;

    public LeaveService(LeaveDao leaveDao) {
        this.leaveDao = leaveDao;
    }

    public Result getAllLeaveRequest(){
        List<Leave> leaveList = leaveDao.getAllLeaveRequests();
        return Result.success(leaveList);
    }

    public Result checkLeaveRequest(Long id, Integer status){
        Integer integer = leaveDao.updateLeaveRequestStatusById(id, status);
        return Result.success(integer);
    }

    public Result insertLeaveRequest(Leave leave){
        Integer request = leaveDao.insertLeaveRequest(leave);
        return Result.success(request);
    }

    public Result getLeaveAnalysis(){
        Map<String, Object> analysis = new HashMap<>();

        List<Integer> count = leaveDao.getRecentYearLeaveCount();
        analysis.put("yearCount", count);

        Integer cntBusinessLeave = leaveDao.getBusinessLeaveCount();
        Integer cntSickLeave = leaveDao.getSickLeaveCount();
        Map<String, Object> businessLeave = new HashMap<>();
        businessLeave.put("name","事假");
        businessLeave.put("value", cntBusinessLeave);

        Map<String, Object> sickLeave = new HashMap<>();
        sickLeave.put("name","病假");
        sickLeave.put("value", cntSickLeave);

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(businessLeave);
        list.add(sickLeave);

        analysis.put("typeCount", list);
        return Result.success(analysis);

    }

    public ArrayList<Leave> getLeaveById(Long id){
        return leaveDao.getLeaveById(id);
    }

}
