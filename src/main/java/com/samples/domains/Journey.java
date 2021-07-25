package com.samples.domains;

import java.time.LocalDateTime;
import java.util.Objects;

public class Journey {

    private final LocalDateTime startDateTime;
    private final Zone fromZone;
    private final Zone toZone;

    public Journey(LocalDateTime startDateTime, Zone fromZone, Zone toZone) {
        mandatoryCheck(startDateTime, fromZone, toZone);
        this.startDateTime = startDateTime;
        this.fromZone = fromZone;
        this.toZone = toZone;
    }

    private void mandatoryCheck(LocalDateTime startDateTime, Zone fromZone, Zone toZone){
        if(Objects.isNull(startDateTime) ||
                Objects.isNull(fromZone) ||
                Objects.isNull(toZone)){
            throw new RuntimeException("Mandatory journey details missing.");
        }
    }

    @Override
    public String toString() {
        return "Journey{" +
                "startDateTime=" + startDateTime +
                ", fromZone=" + fromZone +
                ", toZone=" + toZone +
                '}';
    }

    public Zone getToZone() {
        return this.toZone;
    }

    public Zone getFromZone() {
        return this.fromZone;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

}
