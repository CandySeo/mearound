package com.candyseo.mearound.redis;

import com.candyseo.mearound.model.dto.device.Device;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RedisTemplateTests {
    
    @Autowired
    private RedisTemplate<String, Device> redisTemplate;

    private Device device;

    @BeforeEach
    public void setUp() {

    }

    public void returnTrueWhenHashMapSet() {

    }

    public void returnDeviceWhenGet() {

    }

    public void returnNilWhenGetNotRegisted() {
        
    }

}
