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
class WeekFaresCalculatorTest {

    @Mock
    private WeekFareWithCapCalculator weekFareWithCapCalculator;

    @InjectMocks
    private WeekFaresCalculator weekFaresCalculator;

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
        assertEquals(Map.of(), weekFaresCalculator.calculate(List.of()));
    }

    @Test
    public void testFareCalcSingleJourney(){
        when(weekFareWithCapCalculator.calculate(any())).thenReturn(testFare);
        Map<Integer, Integer> resultMap = weekFaresCalculator.calculate(List.of(testJourney));
        assertEquals(testFare, resultMap.get(CommonUtil.getWeek(testJourney.getStartDateTime())));
    }

    @Test
    public void testFareCalcMultiSameDayJourney(){
        int resultFare = testFare*2;
        Map<Integer, Integer> faresPerDay =
                Map.of(testJourney.getStartDateTime().getDayOfWeek().getValue(), resultFare);
        when(weekFareWithCapCalculator.calculate(any())).thenReturn(resultFare);
        Map<Integer, Integer> resultMap = weekFaresCalculator.calculate(List.of(testJourney, testJourney));
        assertEquals(resultFare, resultMap.get(CommonUtil.getWeek(testJourney.getStartDateTime())));
    }

    @Test
    public void testFareCalcMultiDiffDayJourney() {
        int resultFare = testFare*2;
        Map<Integer, Integer> faresPerWeek =
                Map.of(testJourney.getStartDateTime().getDayOfWeek().getValue(), testFare,
                        testJourneyDiffDayOfWeek.getStartDateTime().getDayOfWeek().getValue(), testFare);
        when(weekFareWithCapCalculator.calculate(any())).thenReturn(resultFare);
        Map<Integer, Integer> resultMap = weekFaresCalculator.calculate(List.of(testJourney, testJourneyDiffDayOfWeek));
        assertEquals(resultFare, resultMap.get(CommonUtil.getWeek(testJourney.getStartDateTime())));
    }

    @Test
    public void testFareCalcMultiDiffDayDiffWeekJourney() {
        int resultFare = testFare*2;
        Map<Integer, Integer> faresForWeek1 = Map.of(
                        testJourneyPrevWeek.getStartDateTime().getDayOfWeek().getValue(), testFare);
        Map<Integer, Integer> faresForWeek2 =
                Map.of(testJourney.getStartDateTime().getDayOfWeek().getValue(), testFare,
                        testJourneyDiffDayOfWeek.getStartDateTime().getDayOfWeek().getValue(), testFare);
        when(weekFareWithCapCalculator.calculate(List.of(testJourney, testJourneyDiffDayOfWeek))).thenReturn(resultFare);
        when(weekFareWithCapCalculator.calculate(List.of(testJourneyPrevWeek))).thenReturn(testFare);
        Map<Integer, Integer> resultMap = weekFaresCalculator.calculate(List.of(testJourney, testJourneyDiffDayOfWeek, testJourneyPrevWeek));
        assertEquals(2, resultMap.size());
        assertEquals(testFare, resultMap.get(CommonUtil.getWeek(testJourneyPrevWeek.getStartDateTime())));
        assertEquals(resultFare, resultMap.get(CommonUtil.getWeek(testJourney.getStartDateTime())));
    }

}