package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.entity.TicketWrapper;

import java.io.File;
import java.io.IOException;

public class TicketDeserializerUtil {
    public static TicketWrapper deserializeTickets(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(new File(filePath), TicketWrapper.class);
    }
}
