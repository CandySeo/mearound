package com.candyseo.mearound.etl.message;

import java.util.LinkedList;
import java.util.List;

import org.springframework.amqp.core.Queue;

public class AmqpMessageQueue extends Queue implements MessageBuffer {

    private java.util.Queue<Message> messages;

    public AmqpMessageQueue(String name) {
        super(name);
        messages = new java.util.concurrent.ConcurrentLinkedQueue<>();
    }

    @Override
    public void appendMessage(Message message) {
        this.messages.add(message);        
    }

    @Override
    public void appendMessages(List<Message> messages) {
        this.messages.addAll(messages);
    }

    @Override
    public synchronized List<Message> getMessages() {
        List<Message> returnMessages = new LinkedList<>(messages);
        messages.clear();
        return returnMessages;
    }
    
}
