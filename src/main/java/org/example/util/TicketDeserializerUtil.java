package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.entity.TicketEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TicketDeserializerUtil {
    public static List<TicketEntity> deserializeTickets(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);

        return mapper.readValue(new File(filePath), new TypeReference<>() {});
    }
}
