package org.example.service;

import org.example.entity.TicketEntity;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightDurationCalculatorService {

    /**
     * Method for calculating the minimum flight duration between Vladivostok (VVO) and Tel Aviv (TLV) for each carrier.
     *
     * @param tickets List of tickets from Vladivostok to Tel Aviv.
     * @return A map where the key is the carrier's name, and the value is the minimum flight duration.
     */
    public Map<String, Duration> calculateMinFlightTime(List<TicketEntity> tickets) {
        return tickets.stream()
                .collect(Collectors.groupingBy(
                        TicketEntity::getCarrier,
                        Collectors.mapping(
                                TicketEntity::getFlightDuration,
                                Collectors.minBy(Duration::compareTo)
                        )
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get()
                ));
    }
}


