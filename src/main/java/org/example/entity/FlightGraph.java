package org.example.entity;

import java.util.*;

public class FlightGraph {
    private Map<String, List<TicketEntity>> adjacencyList = new HashMap<>();

        public void addEdge(TicketEntity ticket) {
            adjacencyList
                    .computeIfAbsent(ticket.getOrigin(), k -> new ArrayList<>())
                    .add(ticket);
        }

    public List<TicketEntity> getFlightsFromCity(String city) {
        return adjacencyList.getOrDefault(city, Collections.emptyList());
    }
}