package com.samples.config;

import com.samples.domains.Zone;
import org.apache.commons.collections.map.MultiKeyMap;

import java.time.LocalDateTime;
import java.util.Objects;

public class FareConfig {

    private final PeakHourConfig peakHourConfig;

    // TODO: Nit picking here but can the name indicate that a combination of zones ?
    private static final MultiKeyMap fares = new MultiKeyMap();
    {
        fares.put(Zone.zone1, Zone.zone1, true, 30);
        fares.put(Zone.zone1, Zone.zone2, true, 35);
        fares.put(Zone.zone2, Zone.zone1, true, 35);
        fares.put(Zone.zone2, Zone.zone2, true, 25);
        fares.put(Zone.zone1, Zone.zone1, false, 25);
        fares.put(Zone.zone1, Zone.zone2, false, 30);
        fares.put(Zone.zone2, Zone.zone1, false, 30);
        fares.put(Zone.zone2, Zone.zone2, false, 20);
    }

    public FareConfig(PeakHourConfig peakHourConfig) {
        this.peakHourConfig = peakHourConfig;
    }

    public Integer getFare(Zone fromZone, Zone toZone, LocalDateTime journeyStartDate){
        boolean isPeakHour = peakHourConfig.isPeakHour(journeyStartDate);
        Object fare = fares.get(fromZone, toZone, isPeakHour);
        return Objects.isNull(fare)? 0 : (Integer) fare;
    }
}
