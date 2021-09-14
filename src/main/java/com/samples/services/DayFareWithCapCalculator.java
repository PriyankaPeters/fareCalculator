package com.samples.services;

import com.samples.domains.Journey;
import com.samples.domains.Zone;
import org.apache.commons.collections.map.MultiKeyMap;

import java.util.Map;

public class DayFareWithCapCalculator {

    private static MultiKeyMap dayFareCaps = new MultiKeyMap();
    static{
        dayFareCaps.put(Zone.zone1, Zone.zone1, 100);
        dayFareCaps.put(Zone.zone1, Zone.zone2, 120);
        dayFareCaps.put(Zone.zone2, Zone.zone1, 120);
        dayFareCaps.put(Zone.zone2, Zone.zone2, 80);
    }

    private final FareCalculator fareCalculator;

    public DayFareWithCapCalculator(FareCalculator fareCalculator) {
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
        Integer cap = (Integer) dayFareCaps.get(journey.getFromZone(), journey.getToZone());
        return cap;
    }
}
