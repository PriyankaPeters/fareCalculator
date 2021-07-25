package com.samples.services;

import com.samples.domains.Journey;
import com.samples.domains.Zone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DayFaresCalculatorTest {

    @Mock
    private DayFareWithCapCalculator dayFareWithCapCalculator;

    @InjectMocks
    private DayFaresCalculator dayFaresCalculator;

    private Journey testJourney;
    private Journey testJourneyDiffDayOfWeek;
    private Journey testJourneyPrevWeek;

    private int testFare = 30;

    {
            testJourney = new Journey(LocalDateTime.of(2021,07,23,1,0), Zone.zone1, Zone.zone1);
            testJourneyDiffDayOfWeek = new Journey(LocalDateTime.of(2021,07,22,1,0), Zone.zone1, Zone.zone1);
            testJourneyPrevWeek = new Journey(LocalDateTime.of(2021,07,15,1,0), Zone.zone1, Zone.zone1);
    }

    @Test
    public void testFareCalcEmptyList() {
        assertEquals(Map.ofEntries(), dayFaresCalculator.calculate(List.of()));
    }

    @Test
    public void testFareCalcSingleJourney(){
        when(dayFareWithCapCalculator.calculate(any(),any())).thenReturn(testFare);
        Map<Integer, Integer> resultMap = dayFaresCalculator.calculate(List.of(testJourney));
        assertEquals(testFare, resultMap.get(testJourney.getStartDateTime().getDayOfWeek().getValue()));
    }

    @Test
    public void testFareCalcMultiSameDayJourney() {
        int resultFare = testFare*2;
        when(dayFareWithCapCalculator.calculate(any(),any())).thenReturn(testFare);
        Map<Integer, Integer> resultMap = dayFaresCalculator.calculate(List.of(testJourney, testJourney));
        assertEquals(resultFare, resultMap.get(testJourney.getStartDateTime().getDayOfWeek().getValue()));
    }

    @Test
    public void testFareCalcMultiDiffDayJourney() {
        when(dayFareWithCapCalculator.calculate(any(),any())).thenReturn(testFare);
        Map<Integer, Integer> resultMap = dayFaresCalculator.calculate(List.of(testJourney, testJourneyDiffDayOfWeek));
        assertEquals(2, resultMap.size());
        assertEquals(testFare, resultMap.get(testJourneyDiffDayOfWeek.getStartDateTime().getDayOfWeek().getValue()));
        assertEquals(testFare, resultMap.get(testJourney.getStartDateTime().getDayOfWeek().getValue()));
    }

}