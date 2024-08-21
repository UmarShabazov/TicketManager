package org.example;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightDurationCalculator {

    /**
     * Method for calculating the minimum flight duration between two cities for each carrier.
     *
     * @param tickets     List of tickets.
     * @param origin      Departure city.
     * @param destination Destination city.
     * @return A map where the key is the carrier's name, and the value is the minimum flight duration.
     * Carriers without applicable tickets will not be included in the map.
     */

    public Map<String, Duration> calculateMinFlightTime(List<TicketEntity> tickets, String origin, String destination) {
        return tickets.stream()
                .filter(ticket -> ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination))
                .collect(Collectors.groupingBy(
                        TicketEntity::getCarrier,
                        Collectors.mapping(
                                TicketEntity::getFlightDuration,
                                Collectors.minBy(Duration::compareTo)
                        )
                ))

// Filtration for carriers without applicable tickets

                .entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get()
                ));
    }
}
