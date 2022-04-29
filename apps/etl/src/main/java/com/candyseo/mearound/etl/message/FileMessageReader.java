package com.candyseo.mearound.etl.message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor
public class FileMessageReader implements MessageReader {

    private MessageBuffer messageBuffer;

    private DataParsorPolicy<Message, String> dataParsorPolicy;

    private File file;

    @Autowired
    public FileMessageReader(MessageBuffer messageBuffer, DataParsorPolicy<Message, String> dataParsorPolicy) {
        this.messageBuffer = messageBuffer;
        this.dataParsorPolicy = dataParsorPolicy;
    }

    public void setFile(File file) {
        this.file = file;
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
        
        if (file == null) {
            throw new RuntimeException("No file information to read. " + 
                                        "Set the location of the file to be read using the method `setFile(File file)`.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    log.info("ReadLine: {}", line);
                    messageBuffer.appendMessage(dataParsorPolicy.parseString(line));
                }
            }
        } catch(Exception e) {
            throw new RuntimeException("Cannot read file. " + 
                                        "Set the location of the file to be read using the method `setFile(File file)`.");
        }
    }
}
