package com.candyseo.mearound.etl.message;

public interface MessageReader {
    
    void setMessageBuffer(MessageBuffer messageBuffer);

    void read();
}
