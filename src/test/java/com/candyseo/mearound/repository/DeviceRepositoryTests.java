package com.candyseo.mearound.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.repository.device.DeviceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DeviceRepositoryTests {
    
    @Autowired
    private DeviceRepository deviceRepository;

    private DeviceEntity device;

    @BeforeEach
    public void setUp() {
        this.device = DeviceEntity.builder()
                                  .deviceId("DEVICEID1")
                                  .name("DEVICENAME1")
                                  .build();

    }

    @Test
    public void returnDeviceEntityWhenSaveNewDevice() {

        DeviceEntity registed = deviceRepository.save(device);

        assertNotNull(registed.getIdentifier());
        assertEquals(registed.getDeviceId(), device.getDeviceId());
        assertEquals(registed.getName(), device.getName());

    }

    @Test
    public void returnDeviceEntityWhenGetByIdRegistedDevice() {
        
        DeviceEntity registed = deviceRepository.save(device);

        assertNotNull(registed.getIdentifier());
        assertEquals(registed.getDeviceId(), device.getDeviceId());
        assertEquals(registed.getName(), device.getName());

        DeviceEntity search = deviceRepository.getById(registed.getIdentifier());

        assertEquals(search.getDeviceId(), device.getDeviceId());
        assertEquals(search.getName(), device.getName());
    }

    @Test
    public void throwEntityNotFoundExceptionWhenGetByIdUnregistedDevice() {
        
        DeviceEntity search = deviceRepository.getById(UUID.randomUUID());

        assertThrows(EntityNotFoundException.class, () -> {
            assertEquals(search.getDeviceId(), device.getDeviceId());
            assertEquals(search.getName(), device.getName());
        });
        
    }

    @Test
    public void returnDeviceEntityListWhenFindAllByIds() {

        DeviceEntity device1 = DeviceEntity.builder().deviceId("DEVICEID1").name("DEVICENAME1").build();
        DeviceEntity device2 = DeviceEntity.builder().deviceId("DEVICEID2").name("DEVICENAME2").build();
        DeviceEntity device3 = DeviceEntity.builder().deviceId("DEVICEID3").name("DEVICENAME3").build();

        DeviceEntity registed1 = deviceRepository.save(device1);
        DeviceEntity registed2 = deviceRepository.save(device2);
        DeviceEntity registed3 = deviceRepository.save(device3);

        Set<UUID> ids = new HashSet<>();
        ids.add(registed1.getIdentifier());
        ids.add(registed2.getIdentifier());
        ids.add(registed3.getIdentifier());

        List<DeviceEntity> deviceEntities = deviceRepository.findAllById(ids);

        assertEquals(deviceEntities.size(), 3);
    }

    @Test
    public void retuenTheLessDeviceEntityListWhenFindAllByIds() {

        DeviceEntity device1 = DeviceEntity.builder().deviceId("DEVICEID1").name("DEVICENAME1").build();
        DeviceEntity device2 = DeviceEntity.builder().deviceId("DEVICEID2").name("DEVICENAME2").build();
        DeviceEntity device3 = DeviceEntity.builder().deviceId("DEVICEID3").name("DEVICENAME3").build();

        DeviceEntity registed1 = deviceRepository.save(device1);
        DeviceEntity registed2 = deviceRepository.save(device2);
        DeviceEntity registed3 = deviceRepository.save(device3);

        Set<UUID> ids = new HashSet<>();
        ids.add(registed1.getIdentifier());
        // ids.add(registed2.getIdentifier());
        ids.add(registed3.getIdentifier());
        ids.add(UUID.randomUUID());

        List<DeviceEntity> deviceEntities = deviceRepository.findAllById(ids);

        assertEquals(deviceEntities.size(), 2);
    }

}
