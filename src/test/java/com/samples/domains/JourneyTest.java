package com.samples.domains;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JourneyTest {

    private static Zone testZone = Zone.zone1;
    private static LocalDateTime testTime = LocalDateTime.now();
    private static Journey testJourney;

    static {
            testJourney = new Journey(testTime, testZone, testZone);
    }

    @Test
    void testToString(){
        assertEquals(getResultString(),
                        testJourney.toString());
    }

    private String getResultString() {
        return String.format("Journey{startDateTime=%s, fromZone=%s, toZone=%s}",
                testTime.toString(), testZone.toString(), testZone.toString());
    }

    private static class MandatoryTestArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(null, testZone, testZone),
                    Arguments.of(testTime, null, testZone),
                    Arguments.of(testTime, testZone, null),
                    Arguments.of(null, testZone, testZone));
        }
    }

    @ParameterizedTest
    @ArgumentsSource(value = MandatoryTestArgumentsProvider.class)
    public void mandatoryCheckTest(LocalDateTime inputStartDateTime, Zone inputFromZone,
                                   Zone inputToZone){
        assertThrows(RuntimeException.class, () -> new Journey(inputStartDateTime,
                 inputFromZone, inputToZone));
    }
}