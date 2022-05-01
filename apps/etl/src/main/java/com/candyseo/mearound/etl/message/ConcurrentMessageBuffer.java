package com.candyseo.mearound.etl.message;

import java.util.LinkedList;
import java.util.List;

public class ConcurrentMessageBuffer implements MessageBuffer {
    
    private static class LazyHolder {
        public static final java.util.Queue<Message> QUEUE = new java.util.concurrent.ConcurrentLinkedQueue<>();
    }

    @Override
    public void appendMessage(Message message) {
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
