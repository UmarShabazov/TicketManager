package org.example;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class FlightAnalyzerImpl {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java FlightAnalyzerImpl <path_to_tickets.json>");
            return;
        }

        TicketDeserializer deserializer = new TicketDeserializer();
        FlightDurationCalculator durationCalculator = new FlightDurationCalculator();
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer();

        try {
            List<TicketEntity> tickets = deserializer.deserializeTickets(args[0]);

            Map<String, Duration> minFlightTime = durationCalculator.calculateMinFlightTime(tickets, "Vladivostok", "Tel Aviv");
            double priceDifference = priceAnalyzer.calculatePriceDifference(tickets, "Vladivostok", "Tel Aviv");

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