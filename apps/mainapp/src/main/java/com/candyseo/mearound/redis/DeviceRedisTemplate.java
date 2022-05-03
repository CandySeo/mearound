package com.candyseo.mearound.redis;

import com.candyseo.mearound.model.entity.device.DeviceEntity;

import org.springframework.data.redis.core.RedisTemplate;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeviceRedisTemplate extends RedisTemplate<String, DeviceEntity> {
    
}
