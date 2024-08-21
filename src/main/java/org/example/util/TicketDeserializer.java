package org.example.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.entity.ConnectingFlightEntity;
import org.example.entity.DirectFlightEntity;
import org.example.entity.TicketEntity;

import java.io.IOException;

public class TicketDeserializer extends JsonDeserializer<TicketEntity> {

    @Override
    public TicketEntity deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("segments")) {
            return jp.getCodec().treeToValue(node, ConnectingFlightEntity.class);
        } else {
            return jp.getCodec().treeToValue(node, DirectFlightEntity.class);
        }
    }
}