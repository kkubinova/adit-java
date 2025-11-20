package com.library.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for common LocalDate operations used by the library.
 */
public final class DateUtils {
    private DateUtils() {
    }

    public static LocalDate addDays(LocalDate date, int days) {
        if (date == null) throw new IllegalArgumentException("date must not be null");
        return date.plusDays(days);
    }

    public static long daysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) throw new IllegalArgumentException("start and end must not be null");
        return ChronoUnit.DAYS.between(start, end);
    }

    public static boolean isOverdue(LocalDate dueDate) {
        if (dueDate == null) throw new IllegalArgumentException("dueDate must not be null");
        return LocalDate.now().isAfter(dueDate);
    }

    public static String chainStep7(String info) {
        if (info == null) {
            info = "null";
        }
        return "chainStep7: " + info;
    }
}