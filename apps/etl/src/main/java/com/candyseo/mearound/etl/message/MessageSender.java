package com.candyseo.mearound.etl.message;

public interface MessageSender {
    
    void setMessageBuffer(MessageBuffer messageBuffer);

    void send();
}
