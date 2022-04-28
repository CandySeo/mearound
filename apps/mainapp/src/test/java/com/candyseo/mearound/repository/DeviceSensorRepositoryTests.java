package com.candyseo.mearound.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.model.entity.device.DeviceSensorEntity;
import com.candyseo.mearound.repository.device.DeviceSensorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DeviceSensorRepositoryTests {
    
    @Autowired
    private DeviceSensorRepository deviceSensorRepository;

    private String deviceId;

    private List<DeviceSensorEntity> sensorEntities;

    @BeforeEach
    public void setUp() {
        this.deviceId = UUID.randomUUID().toString();
        this.sensorEntities = new LinkedList<>();
        this.sensorEntities.add(new DeviceSensorEntity(deviceId, UUID.randomUUID().toString()));
        this.sensorEntities.add(new DeviceSensorEntity(deviceId, UUID.randomUUID().toString()));
        this.sensorEntities.add(new DeviceSensorEntity(deviceId, UUID.randomUUID().toString()));
    }

    @Test
    public void returnDeviceSensorEntityWhenSave() {
        
        DeviceSensorEntity registed = deviceSensorRepository.save(sensorEntities.get(0));

        assertEquals(registed.getDeviceId(), sensorEntities.get(0).getDeviceId());
        assertEquals(registed.getSensorId(), sensorEntities.get(0).getSensorId());
    }

    @Test
    public void returnDeviceSensorListEntityWhenSaveAll() {
        
        List<DeviceSensorEntity> registed = deviceSensorRepository.saveAll(sensorEntities);

        assertEquals(registed.size(), sensorEntities.size());
    }

    @Test
    public void returnCountWhenCountAfterSaveAll() { 
        
        List<DeviceSensorEntity> registed = deviceSensorRepository.saveAll(sensorEntities);

        assertEquals(deviceSensorRepository.count(), 3L);

    }
}
