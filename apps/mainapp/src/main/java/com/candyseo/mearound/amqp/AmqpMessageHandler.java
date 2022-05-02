package com.candyseo.mearound.amqp;

public interface AmqpMessageHandler {
    
    public void handler(Message message);

}
