package com.candyseo.mearound.etl.message;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.candyseo.mearound.etl.config.ApplicationConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class MessageExtendWithTests {
    
    @Autowired
    private ApplicationContext context;

    @Autowired
    private MessageBuffer messageBuffer;

    @Autowired
    private FileMessageReader messageReader;

    @Autowired
    private MessageSender messageSender;

    @Value("${message.reader.template-path:data/template.txt}")
    private String templatePath;

    @BeforeEach
    public void setUp() {
        this.messageSender.setMessageBuffer(this.messageBuffer);
        this.messageReader.setMessageBuffer(this.messageBuffer);
    }

    @Test
    public void beanTests() {
        assertNotNull(context);
        assertNotNull(context.getBean(MessageBuffer.class));
        assertNotNull(context.getBean(MessageSender.class));
    }

    @Test
    public void messageSendTests() {

        Message message1 = new Message("MESSAGE1");
        Message message2 = new Message("MESSAGE2");
        Message message3 = new Message("MESSAGE3");
        Message message4 = new Message("MESSAGE4");
        Message message5 = new Message("MESSAGE5");
        messageBuffer.appendMessages(List.of(message1, message2, message3, message4, message5));

        messageSender.send();
    }

    @Test
    public void throwRuntimeExceptionWhenMessageReadTests() throws IOException {

        assertThrows(FileNotFoundException.class, () -> {
            File file = new ClassPathResource("messages_failed.txt").getFile();
            messageReader.setFile(file);
            messageReader.read();
            messageSender.send();
        });
    }
    
    @Test
    public void templateReadTests() throws IOException {
        File file = new ClassPathResource(templatePath).getFile();
        
        messageReader.setFile(file);

        messageReader.read();
        messageSender.send();
    }
}
