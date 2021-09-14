package com.samples.services;

import com.samples.domains.Journey;
import com.samples.domains.Zone;
import org.apache.commons.collections.map.MultiKeyMap;

import java.util.List;
import java.util.Map;

public class WeekFareWithCapCalculator {

    private static MultiKeyMap weekFareCaps = new MultiKeyMap();

    static {
        weekFareCaps.put(Zone.zone1, Zone.zone1, 500);
        weekFareCaps.put(Zone.zone1, Zone.zone2, 600);
        weekFareCaps.put(Zone.zone2, Zone.zone1, 600);
        weekFareCaps.put(Zone.zone2, Zone.zone2, 400);
    }
    private final DayFaresCalculator dayFaresCalculator;

    public WeekFareWithCapCalculator(DayFaresCalculator dayFaresCalculator) {
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
        Integer cap = journeysInTheWeek.stream().mapToInt(journey ->
                (Integer) weekFareCaps.get(journey.getFromZone(),journey.getToZone())).max().getAsInt();
        return cap;
    }
}
