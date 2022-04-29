package com.candyseo.mearound.etl.message;

import java.util.List;

public interface MessageBuffer {
    
    void appendMessage(Message message);

    void appendMessages(List<Message> messages);

    List<Message> getMessages();
    
}
