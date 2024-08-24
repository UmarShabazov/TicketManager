package org.example;

import org.example.entity.TicketEntity;
import org.example.entity.TicketWrapper;
import org.example.service.FlightDurationCalculatorService;
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
            System.out.println("Usage: java -jar TestTicketsManager-1.0-SNAPSHOT.jar <path_to_tickets.json>");
            return;
        }

        FlightDurationCalculatorService durationCalculator = new FlightDurationCalculatorService();
        PriceAnalyzerService priceAnalyzerService = new PriceAnalyzerService();

        try {

            TicketWrapper ticketWrapper = TicketDeserializerUtil.deserializeTickets(args[0]);
            List<TicketEntity> tickets = ticketWrapper.getTickets();

            List<TicketEntity> flightsFromVVOToTLV = tickets.stream()
                    .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
                    .collect(Collectors.toList());

            if (flightsFromVVOToTLV.isEmpty()) {
                System.out.println("No flights found from Vladivostok to Tel Aviv.");
                return;
            }

            Map<String, Duration> minFlightTimeByCarrier = durationCalculator.calculateMinFlightTime(flightsFromVVOToTLV);


            double priceDifference = priceAnalyzerService.calculatePriceDifference(flightsFromVVOToTLV);

            System.out.println("Minimum flight time by carrier:");
            for (Map.Entry<String, Duration> entry : minFlightTimeByCarrier.entrySet()) {
                long hours = entry.getValue().toHours();
                long minutes = entry.getValue().toMinutesPart();

                if (minutes == 0) {
                    System.out.printf("%s: %d hours%n", entry.getKey(), hours);
                } else {
                    System.out.printf("%s: %d hours and %d minutes%n", entry.getKey(), hours, minutes);
                }
            }

            System.out.printf("Difference between average price and median price: %.2f%n", priceDifference);

        } catch (IOException e) {
            throw new RuntimeException("Application terminated due to an error during JSON deserialization.", e);
        }
    }
}