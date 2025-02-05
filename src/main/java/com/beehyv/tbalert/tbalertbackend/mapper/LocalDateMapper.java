package com.beehyv.tbalert.tbalertbackend.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    public String toDate(LocalDate localDate) {
        return localDate.format(formatter);
    }
}
