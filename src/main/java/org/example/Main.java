package org.example;

import org.example.entity.FlightGraph;
import org.example.entity.RouteNode;
import org.example.entity.TicketEntity;
import org.example.service.FlightDurationCalculatorService;
import org.example.service.FlightRouteFinderService;
import org.example.service.PriceAnalyzerService;
import org.example.util.TicketDeserializerUtil;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java FlightAnalyzerImpl <path_to_tickets.json>");
            return;
        }

        FlightDurationCalculatorService durationCalculator = new FlightDurationCalculatorService();
        FlightRouteFinderService routeFinder = new FlightRouteFinderService();
        PriceAnalyzerService priceAnalyzerService = new PriceAnalyzerService();

        try {

            List<TicketEntity> tickets = TicketDeserializerUtil.deserializeTickets(args[0]);

            FlightGraph graph = new FlightGraph();
            for (TicketEntity ticket : tickets) {
                graph.addEdge(ticket);
            }

            Map<String, List<RouteNode>> allRoutesByCarrier = routeFinder.findAllRoutesForEachCarrier(graph, "Vladivostok", "Tel Aviv");

            if (allRoutesByCarrier.isEmpty()) {
                System.out.println("No routes found.");
                return;
            }

            Map<String, Duration> minFlightTime = durationCalculator.calculateMinFlightTime(allRoutesByCarrier);

            List<RouteNode> allRoutes = allRoutesByCarrier.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            double priceDifference = priceAnalyzerService.calculatePriceDifference(allRoutes);

            System.out.println("Minimum flight time by carrier:");
            for (Map.Entry<String, Duration> entry : minFlightTime.entrySet()) {
                System.out.printf("%s: %d hours and %d minutes%n",
                        entry.getKey(),
                        entry.getValue().toHours(),
                        entry.getValue().toMinutesPart());
            }

            System.out.printf("Difference between average price and median price: %.2f%n", priceDifference);

        } catch (IOException e) {
            throw new RuntimeException("Application terminated due to an error during JSON deserialization.", e);
        }
    }
}