package org.example.entity;

import java.util.List;

public class TicketWrapper {
    private List<TicketEntity> tickets;

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }
}
