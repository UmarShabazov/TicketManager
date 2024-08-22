package org.example.service;

import org.example.entity.RouteNode;
import org.example.entity.TicketEntity;
import org.example.entity.FlightGraph;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The `FlightRouteFinderService` class is responsible for finding all possible flight routes from an origin to a destination for each carrier.
 * It constructs these routes by exploring all possible connections while respecting minimum and maximum connection times between flights.
 * The routes are grouped by the carrier, allowing us to evaluate the best routes for each airline.
 */

public class FlightRouteFinderService {

    private static final Duration MIN_CONNECTION_TIME = Duration.ofHours(2);
    private static final Duration MAX_CONNECTION_TIME = Duration.ofHours(23);

    public Map<String, List<RouteNode>> findAllRoutesForEachCarrier(FlightGraph graph, String origin, String destination) {

        Map<String, List<RouteNode>> allRoutesByCarrier = new HashMap<>();

        PriorityQueue<RouteNode> queue = new PriorityQueue<>(Comparator.comparing(RouteNode::getTotalDuration));
        queue.add(new RouteNode(origin, Duration.ZERO, new ArrayList<>(), null));

        while (!queue.isEmpty()) {
            RouteNode currentNode = queue.poll();

            String currentCity = currentNode.getCity();
            String currentCarrier = currentNode.getRoute().isEmpty() ? "" : currentNode.getRoute().get(0).getCarrier();

            if (currentCity.equals(destination)) {
                allRoutesByCarrier
                        .computeIfAbsent(currentCarrier, k -> new ArrayList<>())
                        .add(currentNode);
                continue;
            }

            List<TicketEntity> flights = graph.getFlightsFromCity(currentCity);

            for (TicketEntity flight : flights) {
                Duration waitTime = currentNode.getArrivalTime() != null ?
                        Duration.between(currentNode.getArrivalTime(), flight.getDepartureTime()) :
                        Duration.ZERO;

                if (isConnectingPossible(currentNode.getArrivalTime(), flight.getDepartureTime(), waitTime)) {
                    Duration newDuration = currentNode.getTotalDuration().plus(waitTime).plus(flight.getFlightDuration());

                    List<TicketEntity> newRoute = new ArrayList<>(currentNode.getRoute());
                    newRoute.add(flight);
                    RouteNode newNode = new RouteNode(flight.getDestination(), newDuration, newRoute, flight.getArrivalTime());
                    queue.add(newNode);
                }
            }
        }

        return allRoutesByCarrier;
    }

    private boolean isConnectingPossible(LocalDateTime arrivalTime, LocalDateTime departureTime, Duration waitTime) {
        return (arrivalTime == null || (!departureTime.isBefore(arrivalTime.plus(MIN_CONNECTION_TIME)) && waitTime.compareTo(MAX_CONNECTION_TIME) <= 0));
    }
}
