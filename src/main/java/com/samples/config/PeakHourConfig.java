package com.samples.config;

import com.samples.domains.PeakHourDuration;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.time.DayOfWeek.*;

public class PeakHourConfig {

    private static List<PeakHourDuration> peakHours;

    static {
            List<DayOfWeek> weekendDays = List.of(SATURDAY, SUNDAY);
            List<DayOfWeek> weekDays = List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
            peakHours = List.of(
                    new PeakHourDuration(weekendDays, LocalTime.of(9,0), LocalTime.of(11,0)),
                    new PeakHourDuration(weekendDays, LocalTime.of(18,0), LocalTime.of(22,0)),
                    new PeakHourDuration(weekDays, LocalTime.of(7,0), LocalTime.of(10,30)),
                    new PeakHourDuration(weekDays, LocalTime.of(17,0), LocalTime.of(20,0))
            );
    }

    public boolean isPeakHour(LocalDateTime startDateTime) {
        for(PeakHourDuration peakHour : peakHours){
            if(peakHour.isPeakHour(startDateTime)){
                return true;
            }
        }
        return false;
    }

}
