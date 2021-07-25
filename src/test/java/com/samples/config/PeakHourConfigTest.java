package com.samples.config;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PeakHourConfigTest {

        private PeakHourConfig peakHourConfig = new PeakHourConfig();

        @ParameterizedTest
        @ArgumentsSource(value = PeakHourTestArgumentsProvider.class)
        public void testIsPeakHour(LocalDateTime startDateTime, boolean result){
            assertEquals(result, peakHourConfig.isPeakHour(startDateTime));
        }

        private static class PeakHourTestArgumentsProvider implements ArgumentsProvider {

            @Override
            public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
                return Stream.of(
                        Arguments.of(LocalDateTime.of(2021,07,23,8,00), true),
                        Arguments.of(LocalDateTime.of(2021,07,22,13,00), false),
                        Arguments.of(LocalDateTime.of(2021,07,24,10,55), true),
                        Arguments.of(LocalDateTime.of(2021,07,24,11,05), false));
            }
        }
}