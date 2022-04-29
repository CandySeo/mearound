package com.candyseo.mearound.etl.message.amqp;

import java.util.LinkedList;
import java.util.List;

import com.candyseo.mearound.etl.message.Message;
import com.candyseo.mearound.etl.message.MessageBuffer;

import org.springframework.amqp.core.Queue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AmqpMessageQueue extends Queue implements MessageBuffer {

    public AmqpMessageQueue(String name) {
        super(name);
    }

    private static class LazyHolder {
        public static final java.util.Queue<Message> QUEUE = new java.util.concurrent.ConcurrentLinkedQueue<>();
    }

    @Override
    public void appendMessage(Message message) {
        log.info("Append {}", message);
        LazyHolder.QUEUE.add(message);
    }

    @Override
    public void appendMessages(List<Message> messages) {
        LazyHolder.QUEUE.addAll(messages);
    }

    @Override
    public synchronized List<Message> getMessages() {
        List<Message> returnMessages = new LinkedList<>(LazyHolder.QUEUE);
        LazyHolder.QUEUE.clear();
        return returnMessages;
    }
    
}
