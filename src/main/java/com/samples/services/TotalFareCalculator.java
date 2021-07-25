package com.samples.services;

import com.samples.domains.Journey;

import java.util.List;
import java.util.Map;

public class TotalFareCalculator {

    private final WeekFaresCalculator weekFaresCalculator;

    public TotalFareCalculator(WeekFaresCalculator weekFaresCalculator) {
        this.weekFaresCalculator = weekFaresCalculator;
    }

    public int calculate(final List<Journey> journeys) {
        int totalFare = 0;
        Map<Integer, Integer> faresPerWeek = weekFaresCalculator.calculate(journeys);
        totalFare = faresPerWeek.values().stream().mapToInt(farePerWeek -> farePerWeek).sum();
        return totalFare;
    }

}
