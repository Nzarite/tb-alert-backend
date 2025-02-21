package com.beehyv.tbalert.tbalertbackend.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class LocalDateMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDate toLocalDate(String date) {
        if(date == null || date.isEmpty()) {
            return null;
        }
        return LocalDate.parse(date, formatter);
    }

    public String toDate(LocalDate localDate)
    {
        if(localDate == null) {
            return "";
        }
        return localDate.format(formatter);
    }

    public Long getYear(String date)
    {
        LocalDate localDate1 = LocalDate.parse(date, formatter);
        LocalDate localDate2 = LocalDate.now();
        return ChronoUnit.YEARS.between(localDate1, localDate2);
    }

    public String toDateTime(LocalDateTime localDateTime)
    {
        if(localDateTime == null) {
            return "";
        }
        return localDateTime.format(dateTimeFormatter);
    }
}
