
import org.example.entity.TicketEntity;
import org.example.entity.TicketWrapper;
import org.example.util.TicketDeserializerUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TicketDeserializerTest {


    @Test
    public void testDeserializeTickets() {

        String jsonFile = "C:\\Users\\Umar\\IdeaProjects\\TestTicketsManager\\src\\main\\resources\\ticketsTest.json";
        try {

            TicketWrapper ticketWrapper = TicketDeserializerUtil.deserializeTickets(jsonFile);
            List<TicketEntity> tickets = ticketWrapper.getTickets();


            assertNotNull(tickets);
            assertEquals(2, tickets.size());

            TicketEntity firstTicket = tickets.get(0);
            assertEquals("VVO", firstTicket.getOrigin());
            assertEquals("Владивосток", firstTicket.getOriginName());
            assertEquals("TLV", firstTicket.getDestination());
            assertEquals("Тель-Авив", firstTicket.getDestinationName());
            assertEquals("TK", firstTicket.getCarrier());
            assertEquals(12400, firstTicket.getPrice());
            assertEquals(LocalDate.of(2018, 5, 12), firstTicket.getDepartureDate());
            assertEquals(LocalTime.of(16, 20), firstTicket.getDepartureTime());
            assertEquals(LocalDate.of(2018, 5, 12), firstTicket.getArrivalDate());
            assertEquals(LocalTime.of(22, 10), firstTicket.getArrivalTime());
            assertEquals(3, firstTicket.getStops());


            TicketEntity secondTicket = tickets.get(1);
            assertEquals("VVO", secondTicket.getOrigin());
            assertEquals("Владивосток", secondTicket.getOriginName());
            assertEquals("TLV", secondTicket.getDestination());
            assertEquals("Тель-Авив", secondTicket.getDestinationName());
            assertEquals("S7", secondTicket.getCarrier());
            assertEquals(13100, secondTicket.getPrice());
            assertEquals(LocalDate.of(2018, 5, 12), secondTicket.getDepartureDate());
            assertEquals(LocalTime.of(17, 20), secondTicket.getDepartureTime());
            assertEquals(LocalDate.of(2018, 5, 12), secondTicket.getArrivalDate());
            assertEquals(LocalTime.of(23, 50), secondTicket.getArrivalTime());
            assertEquals(1, secondTicket.getStops());


        } catch (IOException e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

}
