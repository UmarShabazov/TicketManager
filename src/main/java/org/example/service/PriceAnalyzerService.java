package org.example.service;

import org.example.entity.RouteNode;

import java.util.List;
import java.util.stream.Collectors;


/**
 * The PriceAnalyzer class provides methods to analyze ticket prices between specific origin and destination cities.
 * It allows filtering and sorting prices, calculating the average and median prices, and determining the difference
 * between the average and median prices for flights between the specified cities.
 */
public class PriceAnalyzerService {

    public List<Integer> getSortedPricesFromRoutes(List<RouteNode> routes) {
        return routes.stream()
                .map(RouteNode::getTotalPrice)
                .sorted()
                .collect(Collectors.toList());
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

    public double calculatePriceDifference(List<RouteNode> routes) {
        List<Integer> prices = getSortedPricesFromRoutes(routes);
        double averagePrice = calculateAveragePrice(prices);
        double medianPrice = calculateMedianPrice(prices);

        return averagePrice - medianPrice;
    }
}
