package com.candyseo.mearound.amqp;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AmqpReceiver {

    @Value("${rabbitmq.topic.sensorvalue:sensor-values}")
    private String topicName;

    @Autowired
    private ObjectMapper objectMapper;

    private List<AmqpMessageHandler> handlers = new LinkedList<>();

    public AmqpReceiver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void addMessageHandler(AmqpMessageHandler handler) {
        this.handlers.add(handler);
    }

    public void receiveMessage(Map<String, Object> map) {
        Message message = objectMapper.convertValue(map, Message.class);

        for (AmqpMessageHandler handler: handlers) {
            handler.handler(message);
        }
    }

}
