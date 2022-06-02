package com.example.employee.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtils {

    private static String EMPTY = "";

    public static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd";

    public static DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);

    public static String format(LocalDateTime localDateTimeToFormat) {

        if (Objects.isNull(localDateTimeToFormat)) {
            return EMPTY;
        }

        return localDateTimeToFormat.format(DEFAULT_DATE_TIME_FORMATTER);
    }
}
