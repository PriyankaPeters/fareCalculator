package com.samples.services;

import com.samples.config.FareConfig;
import com.samples.domains.Journey;
import com.samples.domains.Zone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FareCalculatorTest {

    @Mock
    FareConfig fareConfig;

    @InjectMocks
    private FareCalculator fareCalculator;

    @Test
    public void testGetFareOffPeak() {
        Journey testJourney = new Journey(LocalDateTime.now(), Zone.zone1, Zone.zone1);
        when(fareConfig.getFare(any(), any(), any())).thenReturn(25);
        assertEquals(25, fareCalculator.getFare(testJourney));
    }

    @Test
    public void testGetFarePeak(){
        Journey testJourney = new Journey(LocalDateTime.now(), Zone.zone1, Zone.zone1);
        when(fareConfig.getFare(any(), any(), any())).thenReturn(30);
        assertEquals(30, fareCalculator.getFare(testJourney));
    }

    @Test
    public void testGetFareNoConfig(){
        Journey testJourney = new Journey(LocalDateTime.now(), Zone.zone1, Zone.zone1);
        when(fareConfig.getFare(any(), any(), any())).thenReturn(0);
        assertEquals(0, fareCalculator.getFare(testJourney));
    }

}