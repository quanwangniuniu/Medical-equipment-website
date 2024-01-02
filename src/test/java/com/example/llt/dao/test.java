package com.example.llt.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        List<String> recentDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 30; i++) {
            recentDates.add(currentDate.minusDays(i).format(formatter));
        }

        System.out.println("Recent dates:");
        recentDates.forEach(System.out::println);
    }
}
