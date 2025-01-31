package com.aditya.orderservice.config;

import com.aditya.orderservice.dto.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.hibernate.type.SerializationException;

public class OrderDTODeserializer implements Deserializer<OrderDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public OrderDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            // Deserialize the byte array into the OrderDTO object
            return objectMapper.readValue(data, OrderDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing message for topic: " + topic, e);
        }
    }
}
