import org.example.entity.RouteNode;
import org.example.entity.TicketEntity;
import org.example.service.FlightDurationCalculatorService;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightDurationCalculatorServiceTest {

    @Test
    public void CalculateMinFlightTime() {

        TicketEntity ticket1 = new TicketEntity();
        ticket1.setOrigin("Vladivostok");
        ticket1.setDestination("Moscow");
        ticket1.setCarrier("Aeroflot");
        ticket1.setDepartureTime(LocalDateTime.of(2024, 8, 20, 8, 0));
        ticket1.setArrivalTime(LocalDateTime.of(2024, 8, 20, 19, 0));
        ticket1.setPrice(2000);


        TicketEntity ticket2 = new TicketEntity();
        ticket2.setOrigin("Moscow");
        ticket2.setDestination("Tel Aviv");
        ticket2.setCarrier("Aeroflot");
        ticket2.setDepartureTime(LocalDateTime.of(2024, 8, 20, 21, 0));
        ticket2.setArrivalTime(LocalDateTime.of(2024, 8, 21, 1, 0));
        ticket2.setPrice(3000);


        TicketEntity ticket3 = new TicketEntity();
        ticket3.setOrigin("Vladivostok");
        ticket3.setDestination("Tel Aviv");
        ticket3.setCarrier("El Al");
        ticket3.setDepartureTime(LocalDateTime.of(2024, 8, 20, 7, 0));
        ticket3.setArrivalTime(LocalDateTime.of(2024, 8, 20, 22, 0));
        ticket3.setPrice(4000);


        RouteNode aeroflotRoute = new RouteNode(
                "Tel Aviv",
                ticket1.getFlightDuration().plus(ticket2.getFlightDuration()).plus(Duration.ofHours(2)),
                Arrays.asList(ticket1, ticket2),
                ticket2.getArrivalTime()
        );


        RouteNode elAlRoute = new RouteNode(
                "Tel Aviv",
                ticket3.getFlightDuration(),
                List.of(ticket3),
                ticket3.getArrivalTime()
        );


        List<RouteNode> routes = Arrays.asList(aeroflotRoute, elAlRoute);

        Map<String, List<RouteNode>> routesMap = routes.stream()
                .collect(Collectors.groupingBy(
                        route -> route.getRoute().get(0).getCarrier()
                ));

        FlightDurationCalculatorService durationCalculator = new FlightDurationCalculatorService();


        Map<String, Duration> result = durationCalculator.calculateMinFlightTime(routesMap);


        assertEquals(Duration.ofHours(17), result.get("Aeroflot")); // Время полета с пересадкой
        assertEquals(Duration.ofHours(15), result.get("El Al"));    // Прямой рейс
    }
}