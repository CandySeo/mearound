package com.candyseo.mearound.etl.message;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileMessageReader implements MessageReader {

    private MessageBuffer messageBuffer;

    private DataParsorPolicy<Message, String> dataParsorPolicy;

    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Autowired
    public void setDataParsorPolicy(DataParsorPolicy<Message, String> dataParsorPolicy) {
        this.dataParsorPolicy = dataParsorPolicy;
    }

    @Override
    public void setMessageBuffer(MessageBuffer messageBuffer) {
        this.messageBuffer = messageBuffer;
    }
    
    @Override
    public void read() {
        
        if (filePath == null) {
            throw new RuntimeException("`filePath` must be not null. Set the location of the file to be read using the method `setFilePath(String filePath)`.");
        }

        try {
            this.readFile(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed read file `" + filePath + "`.", e);
        }
    }

    private void readFile(String filePath) throws FileNotFoundException, IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                messageBuffer.appendMessage(dataParsorPolicy.parseString(line));
            }
        }
    }
}
