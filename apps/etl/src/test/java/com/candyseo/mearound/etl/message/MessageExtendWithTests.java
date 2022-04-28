package com.candyseo.mearound.etl.message;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.candyseo.mearound.etl.EtlApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EtlApplication.class)
public class MessageExtendWithTests {
    
    @Autowired
    private ApplicationContext context;

    @Autowired
    private MessageBuffer messageBuffer;

    @Autowired
    private FileMessageReader messageReader;

    @Autowired
    private MessageSender messageSender;

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

        // for (String name : context.getBeanDefinitionNames()) {
        //     System.out.println(">>>>> " + name);
        // }
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
    public void throwRuntimeExceptionWhenMessageReadTests() {
        String filePath = getClass().getResource("messages_failed.txt").getPath();
        messageReader.setFilePath(filePath);

        assertThrows(RuntimeException.class, () -> {
            messageReader.read();
            messageSender.send();
        });
    }
    
    @Test
    public void messageReadTests() {
        String filePath = getClass().getResource("messages.txt").getPath();
        messageReader.setFilePath(filePath);

        messageReader.read();
        messageSender.send();
    }

}
