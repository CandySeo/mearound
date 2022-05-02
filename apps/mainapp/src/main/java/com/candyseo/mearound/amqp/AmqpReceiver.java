package com.candyseo.mearound.amqp;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AmqpReceiver {

    @Value("${rabbitmq.topic.sensorvalue:sensor-values}")
    private String topicName;

    @Autowired
    private ObjectMapper objectMapper;

    public void receiveMessage(Map<String, Object> map) {
        Message message = objectMapper.convertValue(map, Message.class);
        log.info("Received message: {}", message);
    }

}
