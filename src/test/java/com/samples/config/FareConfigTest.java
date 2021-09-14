package com.samples.config;

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

import static com.samples.domains.Zone.zone1;
import static com.samples.domains.Zone.zone2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FareConfigTest {

    @Mock
    PeakHourConfig peakHourConfig;

    @Mock
    private FareFreeDaysConfig fareFreeDaysConfig;

    @InjectMocks
    private FareConfig fareConfig;

    private LocalDateTime testJourneyStart = LocalDateTime.now();


    @ParameterizedTest
    @ArgumentsSource(value = FaresTestArgumentsProvider.class)
    public void testGetFares(Zone testZone, boolean isPeakHour, Integer result){
        when(peakHourConfig.isPeakHour(testJourneyStart)).thenReturn(isPeakHour);
        assertEquals(result, fareConfig.getFare(testZone, testZone, testJourneyStart));
    }

    private static class FaresTestArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(zone1, true, 30),
                    Arguments.of(zone1, false, 25),
                    Arguments.of(zone2, true, 25),
                    Arguments.of(zone2, false, 20),
                    Arguments.of(null, false, 0));
        }
    }

    @ParameterizedTest
    @ArgumentsSource(value = FaresFreeDaysArgumentsProvider.class)
    public void testGetFaresForFareFreeDays(boolean isFareFreeDay, Integer result){
        when(fareFreeDaysConfig.isFareFreeDay(any())).thenReturn(isFareFreeDay);
        lenient().when(peakHourConfig.isPeakHour(testJourneyStart)).thenReturn(false);
        assertEquals(result, fareConfig.getFare(zone1, zone1, testJourneyStart));
    }

    private static class FaresFreeDaysArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(true, 0),
                    Arguments.of(false, 25));
        }
    }

}