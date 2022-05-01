package com.candyseo.mearound.etl.redis;

import com.candyseo.mearound.etl.message.Message;

import org.springframework.data.redis.core.RedisTemplate;

public class MessageRedisTemplate extends RedisTemplate<String, Message> {
    
}
