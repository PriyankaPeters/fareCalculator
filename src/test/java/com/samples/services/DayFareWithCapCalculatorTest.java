package com.samples.services;

import com.samples.config.JourneyWeightageConfig;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DayFareWithCapCalculatorTest {

    @Mock
    JourneyWeightageConfig journeyWeightageConfig;

    @Mock
    FareCalculator fareCalculator;

    @InjectMocks
    DayFareWithCapCalculator dayFareWithCapCalculator;

    @ParameterizedTest
    @ArgumentsSource(DayCapArgumentsProvider.class)
    public void testCalculate(Integer totalDayFare, Integer maxJourneyWeightage, Integer expectedResultFare){
        Journey testJourney = new Journey(LocalDateTime.of(2021,07,23,1,0), Zone.zone1, Zone.zone1);
        when(journeyWeightageConfig.getWeightage(any())).thenReturn(maxJourneyWeightage);
        lenient().when(fareCalculator.getFare(testJourney)).thenReturn(30);
        assertEquals(expectedResultFare, dayFareWithCapCalculator.calculate(totalDayFare, testJourney));
    }

    private static class DayCapArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(30, 1, 30),
                    Arguments.of(80, 2, 20),
                    Arguments.of(100, 2, 0),
                    Arguments.of(800, 3, 0));
        }
    }

}