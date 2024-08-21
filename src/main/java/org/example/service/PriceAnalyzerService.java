package org.example.service;

import org.example.entity.ConnectingFlightEntity;
import org.example.entity.TicketEntity;

import java.util.List;
import java.util.stream.Collectors;


/**
 * The PriceAnalyzer class provides methods to analyze ticket prices between specific origin and destination cities.
 * It allows filtering and sorting prices, calculating the average and median prices, and determining the difference
 * between the average and median prices for flights between the specified cities.
 */
public class PriceAnalyzerService {

    public List<Integer> filterAndSortPrices(List<TicketEntity> tickets, String origin, String destination) {
        return tickets.stream()
                .filter(ticket -> ticketMatchesRoute(ticket, origin, destination))
                .map(TicketEntity::getPrice)
                .sorted()
                .collect(Collectors.toList());
    }

    private boolean ticketMatchesRoute(TicketEntity ticket, String origin, String destination) {
        if (ticket instanceof ConnectingFlightEntity) {
            var segments = ((ConnectingFlightEntity) ticket).getSegments();
            String firstSegmentOrigin = segments.get(0).getOrigin();
            String lastSegmentDestination = segments.get(segments.size() - 1).getDestination();
            return firstSegmentOrigin.equals(origin) && lastSegmentDestination.equals(destination);
        }
       else {
            return ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination);
        }

    }

    public double calculateAveragePrice(List<Integer> prices) {
        return prices.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public double calculateMedianPrice(List<Integer> prices) {
        int size = prices.size();
        if (size == 0) return 0.0;

        if (size % 2 == 0) {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }


    public double calculatePriceDifference(List<TicketEntity> tickets, String origin, String destination) {
        List<Integer> prices = filterAndSortPrices(tickets, origin, destination);
        double averagePrice = calculateAveragePrice(prices);
        double medianPrice = calculateMedianPrice(prices);

        return averagePrice - medianPrice;
    }
}
