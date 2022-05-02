package com.candyseo.mearound.etl.message.amqp;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class AmqpMessageConverter implements MessageConverter {

    private ObjectMapper objectMapper;

    private MessageConverter realConverter;

    public AmqpMessageConverter (MessageConverter converter,
                                 ObjectMapper objectMapper) {
        this.realConverter = converter;
        this.objectMapper = objectMapper;
    }

    @Override
    public org.springframework.amqp.core.Message toMessage(Object object, MessageProperties messageProperties)
            throws MessageConversionException {
                Map<String, Object> map = objectMapper.convertValue(object, new TypeReference<Map<String, Object>>(){});
        messageProperties.setType("java.util.Map");
        return realConverter.toMessage(map, messageProperties);
    }


    @Override
    public Object fromMessage(org.springframework.amqp.core.Message message) throws MessageConversionException {
        Object object = realConverter.fromMessage(message);
        com.candyseo.mearound.etl.message.Message converted = objectMapper.convertValue(object, com.candyseo.mearound.etl.message.Message.class);
        return converted;
    }
    
}
