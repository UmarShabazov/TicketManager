package org.example.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RouteNode {
    private String city;
    private Duration totalDuration;
    private List<TicketEntity> route;
    private LocalDateTime arrivalTime;

    public RouteNode(String city, Duration totalDuration, List<TicketEntity> route, LocalDateTime arrivalTime) {
        this.city = city;
        this.totalDuration = totalDuration;
        this.route = new ArrayList<>(route);
        this.arrivalTime = arrivalTime;
    }

    public String getCity() {
        return city;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public List<TicketEntity> getRoute() {
        return route;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public int getTotalPrice() {
        return route.stream().mapToInt(TicketEntity::getPrice).sum();
    }


}
