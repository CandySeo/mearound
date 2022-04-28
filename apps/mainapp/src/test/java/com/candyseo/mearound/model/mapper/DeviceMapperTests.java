package com.candyseo.mearound.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.entity.device.DeviceEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeviceMapperTests {
    
    private DeviceMapper deviceMapper;

    @BeforeEach
    public void setUp() {
        this.deviceMapper = new DeviceMapper();
    }

    @Test
    public void returnDeviceDtoWhenToDtoGivenDeviceEntity() {
        DeviceEntity entity = DeviceEntity.builder()
                                          .identifier(UUID.randomUUID())
                                          .deviceId("DEVICEID1")
                                          .name("DEVICENAME1")
                                          .build();
        
        Device dto = deviceMapper.toDto(entity);

        assertEquals(dto.getIdentifier(), entity.getIdentifier().toString());
        assertEquals(dto.getId(), entity.getDeviceId());
        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    public void returnDeviceEntityWhenToEntityGivenDeviceDto() {
        Device dto = new Device(UUID.randomUUID().toString(), "DEVICEID1", "DEVICENAME1");

        DeviceEntity entity = deviceMapper.toEntity(dto);
        
        // assertEquals(entity.getIdentifier().toString(), dto.getIdentifier());
        assertEquals(entity.getDeviceId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

}
