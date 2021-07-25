package com.samples.services;

import com.samples.domains.Journey;
import com.samples.util.CommonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeekFaresCalculator {

    private WeekFareWithCapCalculator weekFareWithCapCalculator;

    public WeekFaresCalculator(WeekFareWithCapCalculator weekFareWithCapCalculator) {
        this.weekFareWithCapCalculator = weekFareWithCapCalculator;
    }

    public Map<Integer, Integer> calculate(List<Journey> journeys) {
        Map<Integer, List<Journey>> journeysPerWeek = getJourneysPerWeek(journeys);
        return getFaresPerWeek(journeysPerWeek);
    }

    private Map<Integer, Integer> getFaresPerWeek(Map<Integer, List<Journey>> journeysPerWeek) {
        Map<Integer, Integer> faresPerWeek = new HashMap<>();
        for(Map.Entry<Integer, List<Journey>> entry : journeysPerWeek.entrySet()){
            Integer weekFare = weekFareWithCapCalculator.calculate(entry.getValue());
            faresPerWeek.put(entry.getKey(), weekFare);
        }
        return faresPerWeek;
    }

    private Map<Integer, List<Journey>> getJourneysPerWeek(List<Journey> journeys) {
        return journeys.stream().collect(Collectors.groupingBy(journey ->
                CommonUtil.getWeek(journey.getStartDateTime())));
    }

}
