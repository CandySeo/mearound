package com.candyseo.mearound.etl.message;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageTests {
    
    private MessageBuffer buffer;

    private MessageReader reader;

    private MessageSender sender;

    

    @BeforeEach
    public void setUp() {
        this.buffer = new ConcurrentMessageBuffer();
        this.sender = new ConsoleMessageSender();
        this.sender.setMessageBuffer(buffer);
    }

    @Test
    public void messageSendTests() {

        Message message1 = new Message("MESSAGE1");
        Message message2 = new Message("MESSAGE2");
        Message message3 = new Message("MESSAGE3");
        Message message4 = new Message("MESSAGE4");
        Message message5 = new Message("MESSAGE5");
        buffer.appendMessages(List.of(message1, message2, message3, message4, message5));

        sender.send();
    }

}
