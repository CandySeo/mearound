package com.candyseo.mearound.config;

import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.redis.DeviceRedisTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean(value = "redisTemplate")
    public RedisTemplate<String, DeviceEntity> redisTemplate() {
        DeviceRedisTemplate redisTemplate = new DeviceRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(DeviceEntity.class));
        return redisTemplate;
    }
}
