package com.lab.plus.meeting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "delayed_meeting_exchange";
    public static final String QUEUE_NAME = "delayed_meeting_queue";

    @Bean
    public MessageConverter createMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    CustomExchange delayedExchange() {
        java.util.Map<String, Object> args = new java.util.HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    Queue delayedMeetingQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    Binding delayedMeetingBinding() {
        return BindingBuilder.bind(delayedMeetingQueue()).to(delayedExchange()).with(QUEUE_NAME).noargs();
    }


}
