package com.candyseo.mearound.etl.message.amqp;

import javax.annotation.PreDestroy;

import com.candyseo.mearound.etl.message.Message;
import com.candyseo.mearound.etl.message.MessageBuffer;
import com.candyseo.mearound.etl.message.MessageSender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @PreDestroy
    public void shutdown() {
        log.info("shutdown bean: try buffer empty.");
        this.send();
    }

}
