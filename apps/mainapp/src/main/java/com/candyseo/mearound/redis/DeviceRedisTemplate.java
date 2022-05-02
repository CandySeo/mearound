package com.candyseo.mearound.redis;

import com.candyseo.mearound.model.dto.device.Device;

import org.springframework.data.redis.core.RedisTemplate;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeviceRedisTemplate extends RedisTemplate<String, Device> {
    
}
