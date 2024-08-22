
import org.example.entity.TicketEntity;
import org.example.util.TicketDeserializerUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TicketDeserializerTest {


    @Test
    public void testDeserializeTickets() {
        TicketDeserializerUtil deserializer = new TicketDeserializerUtil();
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
