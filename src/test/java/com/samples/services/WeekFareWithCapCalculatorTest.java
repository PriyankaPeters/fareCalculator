package com.samples.services;

import com.samples.domains.Journey;
import com.samples.domains.Zone;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeekFareWithCapCalculatorTest {

    @Mock
    DayFaresCalculator dayFaresCalculator;

    @InjectMocks
    WeekFareWithCapCalculator weekFareWithCapCalculator;

    private Journey testJourney;

    {
            testJourney = new Journey(LocalDateTime.of(2021, 07, 23, 1, 0), Zone.zone1, Zone.zone1);
    }


    @ParameterizedTest
    @ArgumentsSource(WeekCapArgumentsProvider.class)
    public void testCalculate(Integer totalDayFare, Integer expectedResultFare){
        when(dayFaresCalculator.calculate(any())).thenReturn(
                Map.of(testJourney.getStartDateTime().getDayOfWeek().getValue(), totalDayFare));
        assertEquals(expectedResultFare, weekFareWithCapCalculator.calculate(List.of(testJourney)));
    }

    private static class WeekCapArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(30, 30),
                    Arguments.of(500, 500),
                    Arguments.of(800, 500));
        }
    }

}