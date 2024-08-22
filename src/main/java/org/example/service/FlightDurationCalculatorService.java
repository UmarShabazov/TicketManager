package org.example.service;

import org.example.entity.RouteNode;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightDurationCalculatorService {

    /**
     * Method for calculating the minimum flight duration between two cities for each carrier.
     *
     * @param allRoutesByCarrier Map of carrier to all possible routes.
     * @return A map where the key is the carrier's name, and the value is the minimum flight duration.
     * Carriers without applicable tickets will not be included in the map.
     */

    public Map<String, Duration> calculateMinFlightTime(Map<String, List<RouteNode>> allRoutesByCarrier) {
        return allRoutesByCarrier.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(RouteNode::getTotalDuration)
                                .min(Duration::compareTo)
                                .orElse(Duration.ZERO)
                ));
    }
}


