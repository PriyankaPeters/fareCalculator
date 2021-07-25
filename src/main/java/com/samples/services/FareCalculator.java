package com.samples.services;

import com.samples.config.FareConfig;
import com.samples.domains.Journey;

public class FareCalculator {

    private final FareConfig fareConfig;

    public FareCalculator(FareConfig fareConfig) {
        this.fareConfig = fareConfig;
    }

    public Integer getFare(Journey journey){
        return fareConfig.getFare(journey.getFromZone(), journey.getToZone(),
                journey.getStartDateTime());
    }
}
