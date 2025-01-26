package com.aditya.inventoryservice.config;

import com.aditya.inventoryservice.dto.PaymentHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.hibernate.type.SerializationException;

public class PaymentDTODeserializer implements Deserializer<PaymentHistory> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public PaymentHistory deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            // Deserialize the byte array into the OrderDTO object
            return objectMapper.readValue(data, PaymentHistory.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing message for topic: " + topic, e);
        }
    }
}
