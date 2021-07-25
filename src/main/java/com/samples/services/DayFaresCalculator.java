package com.samples.services;

import com.samples.domains.Journey;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayFaresCalculator {

    private DayFareWithCapCalculator dayFareWithCapCalculator;

    public DayFaresCalculator(DayFareWithCapCalculator dayFareWithCapCalculator) {
        this.dayFareWithCapCalculator = dayFareWithCapCalculator;
    }

    public Map<Integer, Integer> calculate(List<Journey> journeysEveryWeek) {
        Map<DayOfWeek, List<Journey>> journeysPerDay = getJourneysPerDay(journeysEveryWeek);
        return journeysPerDay.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getValue(),
                        entry ->  getFareForTheDay(entry.getValue())));
    }

    private int getFareForTheDay(List<Journey> journeysInTheDay) {
        Integer totalDayFare = 0;
        for(Journey journey : journeysInTheDay){
            totalDayFare+= dayFareWithCapCalculator.calculate(totalDayFare, journey);
        }
        return totalDayFare;
    }

    private Map<DayOfWeek, List<Journey>> getJourneysPerDay(List<Journey> journeysEveryWeek) {
        return journeysEveryWeek.stream().collect(Collectors.groupingBy(journey -> journey.getStartDateTime().getDayOfWeek()));
    }
}
