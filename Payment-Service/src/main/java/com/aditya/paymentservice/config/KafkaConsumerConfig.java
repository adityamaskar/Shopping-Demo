package com.aditya.paymentservice.config;


import com.aditya.paymentservice.dto.OrderDTO;
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
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Key deserializer
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDTODeserializer.class); // Value deserializer for JSON
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.aditya.inventoryservice.dto.Order,com.aditya.orderservice.entity.Order,com.aditya"); // using as a wildcard to trust all packages
        return props;
    }

    @Bean
    public ConsumerFactory<String, OrderDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs()
//                new StringDeserializer(),
//                new JsonDeserializer<>(OrderDTO.class) // Deserializing into Order class
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderDTO> kafkaListenerContainerFactoryPayment() {
        ConcurrentKafkaListenerContainerFactory<String, OrderDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
