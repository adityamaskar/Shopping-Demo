package com.aditya.inventoryservice.config;

import com.aditya.inventoryservice.dto.OrderDTO;
import com.aditya.inventoryservice.dto.PaymentHistory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfigPayment {

    @Bean
    public Map<String, Object> consumerConfigsPayment() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Key deserializer
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PaymentDTODeserializer.class); // Value deserializer for JSON
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public ConsumerFactory<String, PaymentHistory> consumerFactoryPayment() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigsPayment()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentHistory> kafkaListenerContainerFactoryPayment() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentHistory> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryPayment());
        return factory;
    }

}
