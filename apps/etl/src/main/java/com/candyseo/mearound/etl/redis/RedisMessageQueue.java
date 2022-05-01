package com.candyseo.mearound.etl.redis;

import java.util.List;

import com.candyseo.mearound.etl.message.Message;
import com.candyseo.mearound.etl.message.MessageBuffer;

import org.springframework.amqp.core.Queue;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisMessageQueue extends Queue implements RedisService<String, Message>, MessageBuffer {

    private RedisTemplate<String, Message> redisTemplate;

    public RedisMessageQueue(String name, RedisTemplate<String, Message> redisTemplate) {
        super(name);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String key, Message object) {
        redisTemplate.opsForValue().set(key, object);
    }

    @Override
    public Message get(String key) {
        Message message = redisTemplate.opsForValue().get(key);
        return message;
    }

    @Override
    public void appendMessage(Message message) {
        this.save(message.getDeviceId(), message);
    }

    @Override
    public void appendMessages(List<Message> messages) {
        for (Message message: messages) {
            this.save(message.getDeviceId(), message);
        }
    }

    @Override
    public List<Message> getMessages() {
        throw new RuntimeException("This method is not implements.");
    }
    
}
