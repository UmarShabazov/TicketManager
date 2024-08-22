

import org.example.entity.RouteNode;
import org.example.service.PriceAnalyzerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceAnalyzerServiceTest {

    private PriceAnalyzerService priceAnalyzerService;

    @BeforeEach
    public void setUp() {
        priceAnalyzerService = new PriceAnalyzerService();
    }

    @Test
    public void testGetSortedPricesFromRoutes() {
        RouteNode route1 = createRouteNodeWithPrice(6000);
        RouteNode route2 = createRouteNodeWithPrice(4000);
        RouteNode route3 = createRouteNodeWithPrice(7000);

        List<RouteNode> routes = Arrays.asList(route1, route2, route3);

        List<Integer> sortedPrices = priceAnalyzerService.getSortedPricesFromRoutes(routes);

        assertEquals(Arrays.asList(4000, 6000, 7000), sortedPrices);
    }

    @Test
    public void testCalculateAveragePrice() {
        List<Integer> prices = Arrays.asList(4000, 6000, 7000);

        double averagePrice = priceAnalyzerService.calculateAveragePrice(prices);

        assertEquals(5666.67, averagePrice, 0.01);
    }

    @Test
    public void testCalculateMedianPriceWithOddNumberOfPrices() {
        List<Integer> prices = Arrays.asList(4000, 6000, 7000);

        double medianPrice = priceAnalyzerService.calculateMedianPrice(prices);

        assertEquals(6000, medianPrice, 0.01);
    }

    @Test
    public void testCalculateMedianPriceWithEvenNumberOfPrices() {
        List<Integer> prices = Arrays.asList(4000, 6000, 7000, 8000);

        double medianPrice = priceAnalyzerService.calculateMedianPrice(prices);

        assertEquals(6500, medianPrice, 0.01);
    }

    @Test
    public void testCalculatePriceDifference() {
        RouteNode route1 = createRouteNodeWithPrice(6000);
        RouteNode route2 = createRouteNodeWithPrice(4000);
        RouteNode route3 = createRouteNodeWithPrice(7000);

        List<RouteNode> routes = Arrays.asList(route1, route2, route3);

        double priceDifference = priceAnalyzerService.calculatePriceDifference(routes);

        assertEquals(5666.67 - 6000, priceDifference, 0.01);
    }

    private RouteNode createRouteNodeWithPrice(int price) {
        return new RouteNode("dummyCity", Duration.ZERO, new ArrayList<>(), null) {
            @Override
            public int getTotalPrice() {
                return price;
            }
        };
    }
}
