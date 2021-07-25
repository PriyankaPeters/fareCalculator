package com.samples.config;

import com.samples.domains.Journey;
import com.samples.domains.Zone;
import org.apache.commons.collections.map.MultiKeyMap;

import java.util.Objects;

public class JourneyWeightageConfig {
    private static final MultiKeyMap journeyWeightages = new MultiKeyMap();
    {
        journeyWeightages.put(Zone.zone1, Zone.zone1, 2);
        journeyWeightages.put(Zone.zone1, Zone.zone2, 3);
        journeyWeightages.put(Zone.zone2, Zone.zone1, 3);
        journeyWeightages.put(Zone.zone2, Zone.zone2, 1);
    }

    private Integer getWeightage(Zone fromZone, Zone toZone){
        Object journeyWeightage = journeyWeightages.get(fromZone, toZone);
        return Objects.isNull(journeyWeightage)? 0 : (Integer) journeyWeightage;
    }

    public Integer getWeightage(Journey journey){
        return getWeightage(journey.getFromZone(), journey.getToZone());
    }

}
