package com.example.llt.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateGenerator {

    public static String generateRandomDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the earliest date one year ago
        LocalDate earliestDate = currentDate.minusYears(1);

        // Get a random date between earliestDate and currentDate
        long minDay = earliestDate.toEpochDay();
        long maxDay = currentDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        // Format the random date as a string in YYYY-MM-DD format
        String dateString = randomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return dateString;
    }
}
