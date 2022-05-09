package com.candyseo.mearound.amqp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.candyseo.mearound.model.dto.device.SensorValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AmqpReceiverTests {

    private AmqpReceiver receiver;

    private ObjectMapper objectMapper;

    private List<SensorValue> values;

    @BeforeEach
    public void setUp() {

        this.values = new LinkedList<>();

        this.objectMapper = new ObjectMapper();
        this.receiver = new AmqpReceiver(objectMapper);
        this.receiver.addMessageHandler(new AmqpMessageHandler() {
            @Override
            public void handler(Message message) {
                System.out.println(message);
            }            
        });
        this.receiver.addMessageHandler(new AmqpMessageHandler() {
            @Override
            public void handler(Message message) {
                SensorValue value = new SensorValue(message.getDeviceId(), 1L, Double.valueOf(message.getValue()), LocalDateTime.parse(message.getRegistedDateTime()));
                values.add(value);
                System.out.println(value);
            }            
        });
    }

    @Test
    public void simpleTest() {

        sendMessage(5);
        
        assertEquals(5, values.size());
    }

    private void sendMessage(int length) {
        
        for (int i = 0; i < length; i++) {
            Map<String, Object> message = new HashMap<>();
            message.put("deviceId", "DEVICEID01");
            message.put("type", "TEMP");
            message.put("value", "15.3"); 
            message.put("registedDateTime", LocalDateTime.now().toString());
            receiver.receiveMessage(message);
        }
    }

}
