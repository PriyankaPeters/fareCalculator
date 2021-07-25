package com.samples.services;

import com.samples.domains.Journey;
import com.samples.domains.Zone;
import com.samples.util.CommonUtil;
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
class TotalFareCalculatorTest {

    @Mock
    private WeekFaresCalculator weekFaresCalculator;

    @InjectMocks
    private TotalFareCalculator totalFareCalculator;

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
        assertEquals(0, totalFareCalculator.calculate(List.of()));
    }

    @Test
    public void testFareCalcSingleJourney() {
        Map<Integer, Integer> faresPerWeek =
                Map.of(CommonUtil.getWeek(testJourney.getStartDateTime()), testFare);
        when(weekFaresCalculator.calculate(any())).thenReturn(faresPerWeek);
        assertEquals(testFare, totalFareCalculator.calculate(List.of(testJourney)));
    }

    @Test
    public void testFareCalcMultiSameDayJourney() {
        int resultFare = testFare*2;
        Map<Integer, Integer> faresPerWeek =
                Map.of(CommonUtil.getWeek(testJourney.getStartDateTime()), resultFare);
        when(weekFaresCalculator.calculate(any())).thenReturn(faresPerWeek);
        assertEquals(resultFare, totalFareCalculator.calculate(List.of(testJourney, testJourney)));
    }

    @Test
    public void testFareCalcMultiDiffDayJourney() {
        int resultFare = testFare*2;
        Map<Integer, Integer> faresPerWeek =
                Map.of(CommonUtil.getWeek(testJourney.getStartDateTime()), resultFare);
        when(weekFaresCalculator.calculate(any())).thenReturn(faresPerWeek);
        assertEquals(resultFare, totalFareCalculator.calculate(List.of(testJourney, testJourneyDiffDayOfWeek)));
    }

    @Test
    public void testFareCalcMultiDiffDayDiffWeekJourney() {
        int resultFare = testFare*3;
        Map<Integer, Integer> faresPerWeek =
                Map.of(CommonUtil.getWeek(testJourney.getStartDateTime()), testFare*2,
                        CommonUtil.getWeek(testJourneyPrevWeek.getStartDateTime()), testFare);
        when(weekFaresCalculator.calculate(any())).thenReturn(faresPerWeek);
        assertEquals(resultFare, totalFareCalculator.calculate(List.of(testJourney, testJourneyDiffDayOfWeek, testJourneyPrevWeek)));
    }

}