
import org.example.entity.TicketEntity;
import org.example.service.FlightDurationCalculatorService;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightDurationCalculatorServiceTest {

    @Test
    public void CalculateMinFlightTime() {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        TicketEntity ticket1 = new TicketEntity();
        ticket1.setOrigin("VVO");
        ticket1.setDestination("TLV");
        ticket1.setCarrier("TK");
        ticket1.setDepartureDate(LocalDate.parse("12.05.18", dateFormatter));
        ticket1.setDepartureTime(LocalTime.parse("16:20", timeFormatter));
        ticket1.setArrivalDate(LocalDate.parse("12.05.18", dateFormatter));
        ticket1.setArrivalTime(LocalTime.parse("22:10", timeFormatter));
        ticket1.setPrice(12400);

        TicketEntity ticket2 = new TicketEntity();
        ticket2.setOrigin("VVO");
        ticket2.setDestination("TLV");
        ticket2.setCarrier("S7");
        ticket2.setDepartureDate(LocalDate.parse("12.05.18", dateFormatter));
        ticket2.setDepartureTime(LocalTime.parse("17:20", timeFormatter));
        ticket2.setArrivalDate(LocalDate.parse("12.05.18", dateFormatter));
        ticket2.setArrivalTime(LocalTime.parse("23:50", timeFormatter));
        ticket2.setPrice(13100);

        TicketEntity ticket3 = new TicketEntity();
        ticket3.setOrigin("VVO");
        ticket3.setDestination("TLV");
        ticket3.setCarrier("SU");
        ticket3.setDepartureDate(LocalDate.parse("12.05.18", dateFormatter));
        ticket3.setDepartureTime(LocalTime.parse("12:10", timeFormatter));
        ticket3.setArrivalDate(LocalDate.parse("12.05.18", dateFormatter));
        ticket3.setArrivalTime(LocalTime.parse("18:10", timeFormatter));
        ticket3.setPrice(15300);

        TicketEntity ticket4 = new TicketEntity();
        ticket4.setOrigin("VVO");
        ticket4.setDestination("TLV");
        ticket4.setCarrier("TK");
        ticket4.setDepartureDate(LocalDate.parse("12.05.18", dateFormatter));
        ticket4.setDepartureTime(LocalTime.parse("17:00", timeFormatter));
        ticket4.setArrivalDate(LocalDate.parse("12.05.18", dateFormatter));
        ticket4.setArrivalTime(LocalTime.parse("23:30", timeFormatter));
        ticket4.setPrice(11000);

        List<TicketEntity> tickets = Arrays.asList(ticket1, ticket2, ticket3, ticket4);

        Map<String, List<TicketEntity>> routesMap = tickets.stream()
                .collect(Collectors.groupingBy(TicketEntity::getCarrier));

        List<TicketEntity> allTickets = routesMap.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        FlightDurationCalculatorService durationCalculator = new FlightDurationCalculatorService();

        Map<String, Duration> result = durationCalculator.calculateMinFlightTime(allTickets);

        assertEquals(Duration.ofHours(5).plusMinutes(50), result.get("TK"));
        assertEquals(Duration.ofHours(6).plusMinutes(30), result.get("S7"));
        assertEquals(Duration.ofHours(6), result.get("SU"));
    }
}