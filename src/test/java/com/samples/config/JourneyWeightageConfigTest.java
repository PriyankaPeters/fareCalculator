package com.samples.config;

import com.samples.domains.Journey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.samples.domains.Zone.negativeTestZone;
import static com.samples.domains.Zone.zone2;
import static org.junit.jupiter.api.Assertions.*;

class JourneyWeightageConfigTest {

        JourneyWeightageConfig journeyWeightageConfig = new JourneyWeightageConfig();

        @Test
        public void testGetWeightage(){
            assertEquals(1, journeyWeightageConfig.getWeightage(new Journey(LocalDateTime.now(),
                    zone2, zone2)));
            assertEquals(0, journeyWeightageConfig.getWeightage(new Journey(LocalDateTime.now(),
                    negativeTestZone, negativeTestZone)));
        }

}