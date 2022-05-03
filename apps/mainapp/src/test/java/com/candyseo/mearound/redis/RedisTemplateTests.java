package com.candyseo.mearound.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.UUID;

import com.candyseo.mearound.config.RedisConfiguration;
import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import redis.embedded.RedisServer;

@ContextConfiguration(classes = RedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class RedisTemplateTests {
    
    private static final int redisPort = 6379;

    private static final String redisSetting = "maxmemory 128M";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, DeviceEntity> redisTemplate;

    private DeviceEntity device;

    private RedisServer redisServer;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void setUp() throws IOException {

        this.device = DeviceEntity.builder()
                                  .identifier(UUID.randomUUID())
                                  .deviceId("DEVICEID01")
                                  .name("DEVICENAME01")
                                  .build();

        this.redisServer = RedisServer.newRedisServer().port(redisPort).setting(redisSetting).build();
        this.redisServer.start();

    }

    @AfterEach
    public void shutDown() throws IOException {
        this.redisServer.stop();
    }

    @Test
    public void beans() {
        assertNotNull(context.getBean("redisConnectionFactory"));
        assertNotNull(context.getBean("redisTemplate"));
    }

    @Test
    public void returnTrueWhenHashMapSet() {

        final String deviceId = device.getDeviceId();
        this.redisTemplate.opsForHash().put(deviceId, "identifier", device.getIdentifier().toString());
        this.redisTemplate.opsForHash().put(deviceId, "entity", device);

        Long count = this.redisTemplate.opsForHash().size(deviceId);
        assertEquals(2L, count);
    }

    @Test
    public void returnDeviceWhenGet() {

        final String deviceId = device.getDeviceId();
        this.redisTemplate.opsForHash().put(deviceId, "identifier", device.getIdentifier().toString());
        this.redisTemplate.opsForHash().put(deviceId, "entity", device);

        Long count = this.redisTemplate.opsForHash().size(deviceId);
        assertEquals(2L, count);

        Object object = this.redisTemplate.opsForHash().get(deviceId, "identifier");
        String identifier = objectMapper.convertValue(object, String.class);    
    
        assertEquals(device.getIdentifier().toString(), identifier);
    }

    @Test
    public void returnNullWhenGetNotRegisted() {

        final String deviceId = device.getDeviceId();
        Long count = this.redisTemplate.opsForHash().size(deviceId);
        assertEquals(0L, count);

        Object object = this.redisTemplate.opsForHash().get(deviceId, "identifier");
        String identifier = objectMapper.convertValue(object, String.class);

        assertNull(object);
        assertNull(identifier);
    }

}
