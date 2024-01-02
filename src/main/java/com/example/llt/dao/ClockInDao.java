package com.example.llt.dao;

import com.example.llt.entity.ClockIn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClockInDao {
    @Select("select * from clock_in where employee_id = #{userId}")
    List<ClockIn> getClockInById(Long userId);

    @Insert("insert into clock_in values(#{id}, #{employee_id}, #{name}, #{time})")
    Integer insertClockIn(ClockIn clockIn);

    @Select("SELECT count(*) FROM clock_in WHERE employee_id = #{employee_id} and time " +
            "BETWEEN CONCAT(CURDATE(), ' 00:00:00') AND NOW() ;")
    Integer getTodayClockInCountById(Long employee_id);

    @Insert("insert into clock_in_rate values(#{date},#{rate})")
    Integer insertClockInRate(String date, Double rate);

    @Select("SELECT date FROM clock_in_rate ORDER BY date DESC LIMIT 30;")
    List<String> getRecentClockInDate();

    @Select("SELECT rate FROM clock_in_rate ORDER BY date DESC LIMIT 30;")
    List<Double> getRecentClockInRate();

    @Select("SELECT COUNT(*) FROM clock_in WHERE DATE(time) = CURDATE();")
    Integer getTodayClockInCount();
}
