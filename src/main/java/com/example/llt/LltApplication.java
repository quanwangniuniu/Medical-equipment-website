package com.example.llt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.llt.dao")
public class LltApplication {

    public static void main(String[] args) {
        SpringApplication.run(LltApplication.class, args);
    }

}
