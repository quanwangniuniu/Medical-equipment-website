package com.example.llt.dao;

import com.example.llt.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao {

    @Select("select * from employee where username = #{username}")
    Employee getEmployeeByUsername(@Param("username") String username);

    @Select("select * from employee")
    List<Employee> getALlEmployee();

    @Insert("insert into employee value(#{id}, #{username}, MD5(#{password}), #{name}" +
            ", #{age}, #{phone}, #{sex}, #{create_time}, #{type}, #{status})")
    Integer insertEmployee(Employee employee);

    @Update("update employee set status = abs(1-status) where id = #{id}")
    Integer alterEmployeeStatus(@Param("id") Long id);

    @Select("select count(*) from employee where type = 1")
    Integer getEmployeeCount();

}
