package com.example.llt.dao;

import com.example.llt.entity.Leave;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LeaveDao {

    @Select("select * from `leave` where status = 0")
    ArrayList<Leave> getAllLeaveRequests();

    @Select("select * from `leave` where employee_id = #{id}")
    ArrayList<Leave> getLeaveById(Long userId);

    @Select("SELECT count from(" +
            "SELECT DATE_FORMAT(start_date, '%Y-%m') AS month, COUNT(*) AS count " +
            "FROM `leave` WHERE start_date >= DATE_SUB(NOW(), INTERVAL 11 MONTH)" +
            "GROUP BY month) as a;")
    List<Integer> getRecentYearLeaveCount();

    @Select("select count(*) from `leave` where type = 0")
    Integer getBusinessLeaveCount();

    @Select("select count(*) from `leave` where type = 1")
    Integer getSickLeaveCount();


    @Insert("insert into `leave` values(#{id},#{employee_id},#{name}," +
            "#{start_date},#{end_date}, #{reason},#{type},0)")
    Integer insertLeaveRequest(Leave leave);

    @Update("update `leave` set status = #{status} where id = #{id}")
    Integer updateLeaveRequestStatusById(Long id, Integer status);


}
