package com.kiramijyan.dynamicbatch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayGround {

    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(1940, 1, 1);
        LocalDate endDate = LocalDate.of(2002, 12, 31);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Set<LocalDate> uniqueDates = new HashSet<>();

        while (uniqueDates.size() < 150) {
            LocalDate randomDate = generateRandomDate(startDate, endDate);
            uniqueDates.add(randomDate);
        }

        for (LocalDate date : uniqueDates) {
            System.out.println(date.format(formatter));
        }
    }

    public static LocalDate generateRandomDate(LocalDate start, LocalDate end) {
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        return LocalDate.ofEpochDay(randomDay);
    }
}







