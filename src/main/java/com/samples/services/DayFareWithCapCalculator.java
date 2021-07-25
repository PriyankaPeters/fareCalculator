package com.samples.services;

import com.samples.config.JourneyWeightageConfig;
import com.samples.domains.Journey;

import java.util.Map;

public class DayFareWithCapCalculator {

    // TODO: Can use EnumMap to make it intuitive. THe keys are weightages right?
    private static Map<Integer, Integer> dayFareCaps = Map.of(3, 120,
            2, 100, 1, 80);

    private final JourneyWeightageConfig journeyWeightageConfig;
    private final FareCalculator fareCalculator;

    public DayFareWithCapCalculator(JourneyWeightageConfig journeyWeightageConfig,
                                    FareCalculator fareCalculator) {
        this.journeyWeightageConfig = journeyWeightageConfig;
        this.fareCalculator = fareCalculator;
    }

    public Integer calculate(Integer totalDayFare, Journey journey){
        Integer cap = getCap(journey);
        return getApplicableFare(totalDayFare, journey, cap);
    }

    private Integer getApplicableFare(Integer totalDayFare, Journey journey, Integer cap) {
        return totalDayFare >= cap ? 0 : getCappedFare(totalDayFare, cap, journey);
    }

    private Integer getCappedFare(Integer totalDayFare, Integer cap, Journey journey) {
        Integer journeyFare = fareCalculator.getFare(journey);
        Integer updatedTotalFare = totalDayFare + journeyFare;
        return updatedTotalFare <= cap ? journeyFare : (journeyFare - (updatedTotalFare-cap));
    }

    private Integer getCap(Journey journey) {
        Integer maxJourneyWeightage = journeyWeightageConfig.getWeightage(journey);
        Integer cap = dayFareCaps.get(maxJourneyWeightage);
        return cap;
    }
}
