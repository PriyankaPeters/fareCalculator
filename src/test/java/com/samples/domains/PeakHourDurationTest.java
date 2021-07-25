package com.samples.domains;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static java.time.DayOfWeek.MONDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PeakHourDurationTest {

    private List<DayOfWeek> testDayOfWeeks = List.of(MONDAY);

    @Test
    public void testCreationException(){
        assertThrows(RuntimeException.class, () ->
                new PeakHourDuration(null, LocalTime.now(), LocalTime.now()));
        assertThrows(RuntimeException.class, () ->
                new PeakHourDuration(testDayOfWeeks, null, LocalTime.now()));
        assertThrows(RuntimeException.class, () ->
                new PeakHourDuration(testDayOfWeeks, LocalTime.now(), null));
    }

    @ParameterizedTest
    @ArgumentsSource(value = TestPeakHourDurationProvider.class)
    public void testIsPeakHour(LocalDateTime testLocalDateTime, boolean result) {
        PeakHourDuration peakHourDuration = new PeakHourDuration(
                testDayOfWeeks, LocalTime.of(9,0), LocalTime.of(10,0));
        assertEquals(result, peakHourDuration.isPeakHour(testLocalDateTime));
    }

    private static class TestPeakHourDurationProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(LocalDateTime.of(2021, 07, 26, 9, 30), true),
                    Arguments.of(LocalDateTime.of(2021, 07, 26, 9, 00), true),
                    Arguments.of(LocalDateTime.of(2021, 07, 26, 10, 00), true),
                    Arguments.of(LocalDateTime.of(2021, 07, 25, 9, 30), false),
                    Arguments.of(LocalDateTime.of(2021, 07, 26, 11, 30), false));
        }
    }

}