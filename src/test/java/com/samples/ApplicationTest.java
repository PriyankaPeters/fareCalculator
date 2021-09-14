package com.samples;

import com.samples.config.FareConfig;
import com.samples.config.FareFreeDaysConfig;
import com.samples.config.PeakHourConfig;
import com.samples.domains.Journey;
import com.samples.services.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.samples.domains.Zone.zone1;
import static com.samples.domains.Zone.zone2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

     TotalFareCalculator totalFareCalculator = new TotalFareCalculator(
            new WeekFaresCalculator(
                    new WeekFareWithCapCalculator(
                            new DayFaresCalculator(new DayFareWithCapCalculator(
                                    new FareCalculator(new FareConfig(new PeakHourConfig(), new FareFreeDaysConfig())))))));

    @ParameterizedTest
    @ArgumentsSource(IntegratedTestArgumentProvider.class)
    public void testCalculate(List<Journey> journeys, Integer totalFare){
        assertEquals(totalFare, totalFareCalculator.calculate(journeys));
    }

    private static class IntegratedTestArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
                return Stream.of(
                        Arguments.of(getDailyCapReachedJourneyList(), 120),
                        Arguments.of(getWeeklyCapReachedJourneyList(), 700));
        }

        private List<Journey> getDailyCapReachedJourneyList(){
            return List.of(
                    new Journey(LocalDateTime.of(2021,07,19,10,20),zone2, zone1),
                    new Journey(LocalDateTime.of(2021,07,19,10,45),zone1, zone1),
                    new Journey(LocalDateTime.of(2021,07,19,16,15),zone1, zone1),
                    new Journey(LocalDateTime.of(2021,07,19,18,15),zone1, zone1),
                    new Journey(LocalDateTime.of(2021,07,19,19,0),zone1, zone2)
            );
        }

        private List<Journey> getWeeklyCapReachedJourneyList(){
            return List.of(
                    new Journey(LocalDateTime.of(2021,07,19,10,20),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,19,10,20),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,19,10,20),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,19,10,20),zone1, zone2),

                    new Journey(LocalDateTime.of(2021,07,20,10,20),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,20,10,20),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,20,10,20),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,20,10,20),zone1, zone2),

                    new Journey(LocalDateTime.of(2021,07,21,10,21),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,21,10,21),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,21,10,21),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,21,10,21),zone1, zone2),

                    new Journey(LocalDateTime.of(2021,07,22,10,22),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,22,10,22),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,22,10,22),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,22,10,22),zone1, zone2),

                    new Journey(LocalDateTime.of(2021,07,23,10,23),zone1, zone1),
                    new Journey(LocalDateTime.of(2021,07,23,13,00),zone1, zone1),
                    new Journey(LocalDateTime.of(2021,07,23,13,20),zone1, zone1),

                    new Journey(LocalDateTime.of(2021,07,24,13,00),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,24,13,20),zone1, zone2),

                    new Journey(LocalDateTime.of(2021,07,25,13,00),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,25,13,20),zone1, zone2),

                    new Journey(LocalDateTime.of(2021,07,26,10,00),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,26,10,20),zone1, zone2),
                    new Journey(LocalDateTime.of(2021,07,26,13,00),zone1, zone2)
                    );
        }
    }

}
