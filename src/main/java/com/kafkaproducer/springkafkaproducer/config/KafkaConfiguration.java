package com.kafkaproducer.springkafkaproducer.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.kafkaproducer.springkafkaproducer.model.Books;


@Configuration
public class KafkaConfiguration {

	@Value("${kafka.bootstrap.servers}")
	private String bootstrapServer;
	
    @Bean
    public ProducerFactory<String, List<Books>> producerFactory(){
        Map<String,Object> config = new HashMap<>();

        //config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.13:9092");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, List<Books>> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}