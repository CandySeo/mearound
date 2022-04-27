package com.candyseo.mearound.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.candyseo.mearound.model.SensorType;
import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.model.entity.device.SensorEntity;
import com.candyseo.mearound.repository.device.DeviceRepository;
import com.candyseo.mearound.repository.device.SensorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class SensorRepositoryTests {
    
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private SensorEntity sensor;

    private List<SensorEntity> sensors;

    private DeviceEntity device;

    @BeforeEach
    public void setUp() {
        sensors = new LinkedList<>();
        sensors.add(new SensorEntity(null, SensorType.TEMP.name(), true, null));
        sensors.add(new SensorEntity(null, SensorType.PM10.name(), true, null));
        sensors.add(new SensorEntity(null, SensorType.PM25.name(), true, null));

        device = DeviceEntity.builder()
                             .deviceId("DEVICEID1")
                             .name("DEVICENAME1")
                             .build();
                             
        sensor = new SensorEntity(null, SensorType.TEMP.name(), true, null);
    }

    @Test
    public void returnSensorEntityWhenSave() {

        SensorEntity registed = sensorRepository.save(sensor);

        assertNotNull(registed.getId());
        assertEquals(registed.getType(), sensor.getType());
    }
    
    @Test
    public void returnSensorEntityWhenSearchAfterSave() {

        SensorEntity registed = sensorRepository.save(sensor);

        assertNotNull(registed.getId());

        Optional<SensorEntity> searchRegisted = sensorRepository.findById(registed.getId());
        Optional<SensorEntity> searchUnregisted = sensorRepository.findById(UUID.randomUUID());

        assertTrue(searchRegisted.isPresent());
        assertTrue(searchUnregisted.isEmpty());
    }

    @Test
    public void retuenSensorEntityListWhenSaveAll() {
        
        List<SensorEntity> registed = sensorRepository.saveAll(sensors);

        assertEquals(registed.size(), sensors.size());
    }

    @Test
    public void returnSensorEntityWhenSaveWithDeviceEntity() {

        sensor.setDevice(device);

        SensorEntity registed = sensorRepository.save(sensor);

        assertEquals(registed.getType(), sensor.getType());
        assertEquals(registed.getDevice().getIdentifier(), device.getIdentifier());
    }

    @Test
    public void returnSensorEntityListWhenFindByDevice() {

        DeviceEntity registedDevice = deviceRepository.save(device);
        assertNotNull(registedDevice.getIdentifier());
        sensor.setDevice(registedDevice);

        SensorEntity registed = sensorRepository.save(sensor);

        assertEquals(registed.getType(), sensor.getType());
        assertEquals(registed.getDevice().getIdentifier(), registedDevice.getIdentifier());

        List<SensorEntity> search = sensorRepository.findByDevice(registedDevice);

        assertEquals(search.size(), 1);
    }

    @Test
    public void retuenSensorEntityListWhenFindAllById() {

        List<SensorEntity> registed = sensorRepository.saveAll(sensors);

        assertEquals(registed.size(), 3);

        List<SensorEntity> entities = sensorRepository.findAllById(registed.stream()
                                                                           .map(s -> s.getId())
                                                                           .collect(Collectors.toSet()));

        assertEquals(entities.size(), registed.size());
    }

}
  