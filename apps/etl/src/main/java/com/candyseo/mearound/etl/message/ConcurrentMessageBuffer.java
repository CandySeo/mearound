package com.candyseo.mearound.etl.message;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentMessageBuffer implements MessageBuffer {
    
    private Queue<Message> messages;

    public ConcurrentMessageBuffer() {
        this.messages = new ConcurrentLinkedQueue<>();
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
