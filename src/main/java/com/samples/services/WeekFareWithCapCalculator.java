package com.samples.services;

import com.samples.config.JourneyWeightageConfig;
import com.samples.domains.Journey;

import java.util.List;
import java.util.Map;

public class WeekFareWithCapCalculator {

    private static Map<Integer, Integer> weekFareCaps = Map.of(3, 600,
            2, 500, 1, 400);

    private final JourneyWeightageConfig journeyWeightageConfig;
    private final DayFaresCalculator dayFaresCalculator;

    public WeekFareWithCapCalculator(JourneyWeightageConfig journeyWeightageConfig, DayFaresCalculator dayFaresCalculator) {
        this.journeyWeightageConfig = journeyWeightageConfig;
        this.dayFaresCalculator = dayFaresCalculator;
    }

    public Integer calculate(List<Journey> journeysInTheWeek){
        Integer cap = getCap(journeysInTheWeek);
        return getCappedWeekFare(cap, journeysInTheWeek);
    }

    private Integer getCappedWeekFare(Integer cap, List<Journey> journeysInTheWeek) {
        Integer totalWeekFare = getFareForTheWeek(journeysInTheWeek);
        return totalWeekFare > cap ? cap : totalWeekFare;
    }

    private Integer getFareForTheWeek(List<Journey> journeysInTheWeek) {
        Map<Integer, Integer> faresPerDay = dayFaresCalculator.calculate(journeysInTheWeek);
        int fareForTheWeek = faresPerDay.values().stream()
                .mapToInt(farePerDay -> farePerDay).sum();
        return fareForTheWeek;
    }

    private Integer getCap(List<Journey> journeysInTheWeek) {
        Integer maxJourneyWeightage = getMaxWeightage(journeysInTheWeek);
        Integer cap = weekFareCaps.get(maxJourneyWeightage);
        return cap;
    }

    private Integer getMaxWeightage(List<Journey> journeysInTheWeek) {
        int maxWeightage = journeysInTheWeek.stream().mapToInt(
                journey -> journeyWeightageConfig.getWeightage(journey)).max().getAsInt();
        return maxWeightage;
    }
}
