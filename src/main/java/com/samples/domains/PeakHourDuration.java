package com.samples.domains;

import org.apache.commons.collections.CollectionUtils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class PeakHourDuration {

        private final List<DayOfWeek> daysOfWeek;
        private final LocalTime startTime;
        private final LocalTime endTime;

    public PeakHourDuration(List<DayOfWeek> daysOfWeek, LocalTime startTime, LocalTime endTime){
            if(CollectionUtils.isEmpty(daysOfWeek) || Objects.isNull(startTime) || Objects.isNull(endTime)) {
                throw new RuntimeException("Empty parameters in peak hour configuration");
            }
            this.daysOfWeek = daysOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public boolean isPeakHour(LocalDateTime journeyStartDateTime) {
            DayOfWeek journeyDay = journeyStartDateTime.getDayOfWeek();
            if(daysOfWeek.contains(journeyDay)){
                LocalTime startTimeOfJourney = journeyStartDateTime.toLocalTime();
                // TODO: Can use between() in the Period class
               return startTimeOfJourney.equals(startTime) || startTimeOfJourney.equals(endTime) ||
                       (startTimeOfJourney.isAfter(startTime) && startTimeOfJourney.isBefore(endTime));
            }
            return false;
        }

}
