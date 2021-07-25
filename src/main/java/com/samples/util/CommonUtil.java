package com.samples.util;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

import static java.time.DayOfWeek.MONDAY;

public final class CommonUtil {

    public static Integer getWeek(LocalDateTime startDateTime) {
        WeekFields weekFields = WeekFields.of(MONDAY,7);
        TemporalField woy = weekFields.weekOfWeekBasedYear();
        Integer weekNumber = startDateTime.get(woy);
        return weekNumber;
    }

}
