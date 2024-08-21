package org.example.entity;

import java.time.Duration;
import java.util.List;

public class ConnectingFlightEntity extends TicketEntity {

    private List<FlightSegmentEntity> segments;

    public List<FlightSegmentEntity> getSegments() {
        return segments;
    }

    public void setSegments(List<FlightSegmentEntity> segments) {
        this.segments = segments;
    }

    @Override
    public int getPrice() {
        // Если цена должна собираться из цен сегментов
        return segments.stream()
                .mapToInt(FlightSegmentEntity::getPrice)
                .sum();
    }


    @Override
    public Duration getTotalFlightDuration() {
        return segments.stream()
                .map(segment -> Duration.between(segment.getDepartureTime(), segment.getArrivalTime()))
                .reduce(Duration.ZERO, Duration::plus);
    }

    // getters and setters
}