
import org.example.entity.TicketEntity;
import org.example.service.PriceAnalyzerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        TicketEntity ticket1 = createTicketEntityWithPrice(6000);
        TicketEntity ticket2 = createTicketEntityWithPrice(4000);
        TicketEntity ticket3 = createTicketEntityWithPrice(7000);

        List<TicketEntity> routes = Arrays.asList(ticket1, ticket2, ticket3);

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
        TicketEntity ticket1 = createTicketEntityWithPrice(6000);
        TicketEntity ticket2 = createTicketEntityWithPrice(4000);
        TicketEntity ticket3 = createTicketEntityWithPrice(7000);

        List<TicketEntity> routes = Arrays.asList(ticket1, ticket2, ticket3);

        double priceDifference = priceAnalyzerService.calculatePriceDifference(routes);

        assertEquals(5666.67 - 6000, priceDifference, 0.01);
    }

    private TicketEntity createTicketEntityWithPrice(int price) {
        TicketEntity ticket = new TicketEntity();
        ticket.setPrice(price);
        return ticket;
    }

}
