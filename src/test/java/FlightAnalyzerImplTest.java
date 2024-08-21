import org.example.entity.ConnectingFlightEntity;
import org.example.entity.DirectFlightEntity;
import org.example.entity.FlightSegmentEntity;
import org.example.service.FlightDurationCalculatorService;
import org.example.service.PriceAnalyzerService;
import org.example.util.TicketDeserializerUtil;
import org.example.entity.TicketEntity;
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
        DirectFlightEntity directFlight1 = new DirectFlightEntity();
        directFlight1.setOrigin("Vladivostok");
        directFlight1.setDestination("Tel Aviv");
        directFlight1.setCarrier("Airline A");
        directFlight1.setDepartureTime(LocalDateTime.of(2024, 8, 20, 10, 0));
        directFlight1.setArrivalTime(LocalDateTime.of(2024, 8, 20, 15, 0));

        DirectFlightEntity directFlight2 = new DirectFlightEntity();
        directFlight2.setOrigin("Vladivostok");
        directFlight2.setDestination("Tel Aviv");
        directFlight2.setCarrier("Airline B");
        directFlight2.setDepartureTime(LocalDateTime.of(2024, 8, 21, 0, 0));
        directFlight2.setArrivalTime(LocalDateTime.of(2024, 8, 21, 15, 0));

        ConnectingFlightEntity connectingFlight = new ConnectingFlightEntity();
        connectingFlight.setOrigin("Vladivostok");
        connectingFlight.setDestination("Tel Aviv");
        connectingFlight.setCarrier("Airline B");

        FlightSegmentEntity segment1 = new FlightSegmentEntity();
        segment1.setOrigin("Vladivostok");
        segment1.setDestination("Bangkok");
        segment1.setDepartureTime(LocalDateTime.of(2024, 8, 22, 0, 0));
        segment1.setArrivalTime(LocalDateTime.of(2024, 8, 22, 6, 0));

        FlightSegmentEntity segment2 = new FlightSegmentEntity();
        segment2.setOrigin("Bangkok");
        segment2.setDestination("Tel Aviv");
        segment2.setDepartureTime(LocalDateTime.of(2024, 8, 22, 8, 0));
        segment2.setArrivalTime(LocalDateTime.of(2024, 8, 22, 16, 0));

        connectingFlight.setSegments(Arrays.asList(segment1, segment2));

        List<TicketEntity> tickets = Arrays.asList(directFlight1, directFlight2, connectingFlight);

        FlightDurationCalculatorService durationCalculator = new FlightDurationCalculatorService();

        Map<String, Duration> result = durationCalculator.calculateMinFlightTime(tickets, "Vladivostok", "Tel Aviv");

        assertEquals(Duration.ofHours(5), result.get("Airline A"));
        assertEquals(Duration.ofHours(14), result.get("Airline B"));
    }

    @Test
    public void testCalculatePriceDifference() {
        TicketEntity directFlight1 = new DirectFlightEntity();
        directFlight1.setOrigin("Vladivostok");
        directFlight1.setDestination("Tel Aviv");
        directFlight1.setPrice(100);

        TicketEntity directFlight2 = new DirectFlightEntity();
        directFlight2.setOrigin("Vladivostok");
        directFlight2.setDestination("Tel Aviv");
        directFlight2.setPrice(200);

        ConnectingFlightEntity connectingFlight1 = new ConnectingFlightEntity();
        connectingFlight1.setOrigin("Vladivostok");
        connectingFlight1.setDestination("Tel Aviv");

        FlightSegmentEntity segment1 = new FlightSegmentEntity();
        segment1.setOrigin("Vladivostok");
        segment1.setDestination("Moscow");
        segment1.setDepartureTime(LocalDateTime.of(2024, 9, 1, 6, 0));
        segment1.setArrivalTime(LocalDateTime.of(2024, 9, 1, 10, 0));

        FlightSegmentEntity segment2 = new FlightSegmentEntity();
        segment2.setOrigin("Moscow");
        segment2.setDestination("Tel Aviv");
        segment2.setDepartureTime(LocalDateTime.of(2024, 9, 1, 14, 0));
        segment2.setArrivalTime(LocalDateTime.of(2024, 9, 1, 18, 0));

        connectingFlight1.setSegments(Arrays.asList(segment1, segment2));
        connectingFlight1.setPrice(300);

        List<TicketEntity> tickets = Arrays.asList(directFlight1, directFlight2, connectingFlight1);

        PriceAnalyzerService priceAnalyzerService = new PriceAnalyzerService();
        double result = priceAnalyzerService.calculatePriceDifference(tickets, "Vladivostok", "Tel Aviv");

        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testDeserializeTickets()  {
        TicketDeserializerUtil deserializer = new TicketDeserializerUtil();
        String jsonFile = "C:\\Users\\Umar\\IdeaProjects\\TestTicketsManager\\src\\main\\resources\\ticketsTest.json";
        try {

            List<TicketEntity> tickets = deserializer.deserializeTickets(jsonFile);

            assertNotNull(tickets);
            assertEquals(3, tickets.size());

            TicketEntity firstTicket = tickets.get(0);
            assertInstanceOf(DirectFlightEntity.class, firstTicket);
            assertEquals("Vladivostok", firstTicket.getOrigin());
            assertEquals("Tel Aviv", firstTicket.getDestination());
            assertEquals("Aeroflot", firstTicket.getCarrier());
            assertEquals(500, firstTicket.getPrice());

            DirectFlightEntity directFlight = (DirectFlightEntity) firstTicket;
            assertEquals("2024-08-20T10:15", directFlight.getDepartureTime().toString());
            assertEquals("2024-08-20T14:45", directFlight.getArrivalTime().toString());

            TicketEntity secondTicket = tickets.get(1);
            assertInstanceOf(DirectFlightEntity.class, secondTicket);
            assertEquals("Vladivostok", secondTicket.getOrigin());
            assertEquals("Tel Aviv", secondTicket.getDestination());
            assertEquals("A7", secondTicket.getCarrier());
            assertEquals(550, secondTicket.getPrice());

            directFlight = (DirectFlightEntity) secondTicket;
            assertEquals("2024-08-21T11:15", directFlight.getDepartureTime().toString());
            assertEquals("2024-08-21T15:45", directFlight.getArrivalTime().toString());

            TicketEntity thirdTicket = tickets.get(2);
            assertInstanceOf(ConnectingFlightEntity.class, thirdTicket);
            assertEquals("Vladivostok", thirdTicket.getOrigin());
            assertEquals("Tel Aviv", thirdTicket.getDestination());
            assertEquals("A7", thirdTicket.getCarrier());
            assertEquals(3600, thirdTicket.getPrice());

            ConnectingFlightEntity connectingFlight = (ConnectingFlightEntity) thirdTicket;
            List<FlightSegmentEntity> segments = connectingFlight.getSegments();
            assertNotNull(segments);
            assertEquals(2, segments.size());


            FlightSegmentEntity firstSegment = segments.get(0);
            assertEquals("Vladivostok", firstSegment.getOrigin());
            assertEquals("Bangkok", firstSegment.getDestination());
            assertEquals("2024-09-04T00:00", firstSegment.getDepartureTime().toString());
            assertEquals("2024-09-04T06:00", firstSegment.getArrivalTime().toString());

            FlightSegmentEntity secondSegment = segments.get(1);
            assertEquals("Bangkok", secondSegment.getOrigin());
            assertEquals("Tel Aviv", secondSegment.getDestination());
            assertEquals("2024-09-04T08:00", secondSegment.getDepartureTime().toString());
            assertEquals("2024-09-04T15:30", secondSegment.getArrivalTime().toString());

        } catch (IOException e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }
}