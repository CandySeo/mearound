package com.candyseo.mearound.etl.message;

public class ConsoleMessageSender implements MessageSender {

    private MessageBuffer messageBuffer;

    @Override
    public void setMessageBuffer(MessageBuffer messageBuffer) {
        this.messageBuffer = messageBuffer;
    }

    @Override
    public void send() {        
        for (Message m : messageBuffer.getMessages()) {
            System.out.println(m);
        }

    }
    
}
