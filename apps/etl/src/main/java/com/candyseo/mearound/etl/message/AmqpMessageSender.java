package com.candyseo.mearound.etl.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AmqpMessageSender implements MessageSender {

    @Value("${rabbitmq.topic.sensorvalue:sensor-values}")
    private String topicName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private MessageBuffer messageBuffer;

    @Override
    public void setMessageBuffer(MessageBuffer messageBuffer) {
        this.messageBuffer = messageBuffer;
    }

    @Override
    public void send() {
        for (Message message: messageBuffer.getMessages()) {
            rabbitTemplate.convertAndSend(topicName, message);
        }
    }

}
