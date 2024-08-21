import org.example.FlightDurationCalculator;
import org.example.PriceAnalyzer;
import org.example.TicketDeserializer;
import org.example.TicketEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FlightAnalyzerImplTest {

    @Test
    public void testCalculateMinFlightTime() {
        TicketEntity ticket1 = new TicketEntity();
        ticket1.setOrigin("Vladivostok");
        ticket1.setDestination("Tel Aviv");
        ticket1.setDepartureTime(LocalDateTime.parse("2024-08-20T10:00:00"));
        ticket1.setArrivalTime(LocalDateTime.parse("2024-08-20T15:00:00"));
        ticket1.setCarrier("Airline A");

        TicketEntity ticket2 = new TicketEntity();
        ticket2.setOrigin("Vladivostok");
        ticket2.setDestination("Tel Aviv");
        ticket2.setDepartureTime(LocalDateTime.parse("2024-08-21T10:00:00"));
        ticket2.setArrivalTime(LocalDateTime.parse("2024-08-21T14:00:00"));
        ticket2.setCarrier("Airline B");

        TicketEntity ticket3 = new TicketEntity();
        ticket3.setOrigin("Vladivostok");
        ticket3.setDestination("Tel Aviv");
        ticket3.setDepartureTime(LocalDateTime.parse("2024-08-22T10:00:00"));
        ticket3.setArrivalTime(LocalDateTime.parse("2024-08-22T15:30:00"));
        ticket3.setCarrier("Airline A");

        List<TicketEntity> tickets = Arrays.asList(ticket1, ticket2, ticket3);

        FlightDurationCalculator durationCalculator = new FlightDurationCalculator();
        Map<String, Duration> result = durationCalculator.calculateMinFlightTime(tickets, "Vladivostok", "Tel Aviv");

        assertEquals(Duration.ofHours(5), result.get("Airline A"));
        assertEquals(Duration.ofHours(4), result.get("Airline B"));
    }

    @Test
    public void testCalculatePriceDifference() {
        TicketEntity ticket1 = new TicketEntity();
        ticket1.setOrigin("Vladivostok");
        ticket1.setDestination("Tel Aviv");
        ticket1.setPrice(100);

        TicketEntity ticket2 = new TicketEntity();
        ticket2.setOrigin("Vladivostok");
        ticket2.setDestination("Tel Aviv");
        ticket2.setPrice(200);

        TicketEntity ticket3 = new TicketEntity();
        ticket3.setOrigin("Vladivostok");
        ticket3.setDestination("Tel Aviv");
        ticket3.setPrice(300);

        List<TicketEntity> tickets = Arrays.asList(ticket1, ticket2, ticket3);

        PriceAnalyzer priceAnalyzer = new PriceAnalyzer();
        double result = priceAnalyzer.calculatePriceDifference(tickets, "Vladivostok", "Tel Aviv");

        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testDeserializeTickets()  {
        TicketDeserializer deserializer = new TicketDeserializer();
        String jsonFile = "C:\\Users\\Umar\\IdeaProjects\\TestTicketsManager\\src\\main\\resources\\ticketsTest.json";
        try {

            List<TicketEntity> tickets = deserializer.deserializeTickets(jsonFile);

            assertNotNull(tickets);
            assertEquals(2, tickets.size());

            TicketEntity firstTicket = tickets.get(0);
            assertEquals("Vladivostok", firstTicket.getOrigin());
            assertEquals("Tel Aviv", firstTicket.getDestination());
            assertEquals("Aeroflot", firstTicket.getCarrier());
            assertEquals(500, firstTicket.getPrice());
            assertEquals("2024-08-20T10:15", firstTicket.getDepartureTime().toString());
            assertEquals("2024-08-20T14:45", firstTicket.getArrivalTime().toString());


            TicketEntity secondTicket = tickets.get(1);
            assertEquals("Vladivostok", secondTicket.getOrigin());
            assertEquals("Tel Aviv", secondTicket.getDestination());
            assertEquals("A7", secondTicket.getCarrier());
            assertEquals(550, secondTicket.getPrice());
            assertEquals("2024-08-21T11:15", secondTicket.getDepartureTime().toString());
            assertEquals("2024-08-21T15:45", secondTicket.getArrivalTime().toString());

        } catch (IOException e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }
}