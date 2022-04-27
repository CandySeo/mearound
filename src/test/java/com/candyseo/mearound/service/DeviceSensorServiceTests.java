package com.candyseo.mearound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.SensorType;
import com.candyseo.mearound.model.dto.device.DeviceSensor;
import com.candyseo.mearound.model.dto.device.Sensor;
import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.model.entity.device.SensorEntity;
import com.candyseo.mearound.model.mapper.DeviceMapper;
import com.candyseo.mearound.repository.device.DeviceRepository;
import com.candyseo.mearound.repository.device.SensorRepository;
import com.candyseo.mearound.service.device.DeviceSensorServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeviceSensorServiceTests {
    
    @Mock 
    private DeviceRepository deviceRepository;

    @Mock
    private SensorRepository sensorRepository;

    @Spy
    DeviceMapper deviceMapper;

    @InjectMocks
    private DeviceSensorServiceImpl deviceSensorService;

    private DeviceEntity device;

    private List<SensorEntity> sensors;

    @BeforeEach
    public void setUp() {

        this.deviceMapper = new DeviceMapper();
        this.device = DeviceEntity.builder()
                                  .identifier(UUID.randomUUID())
                                  .deviceId("DEVICEID1")
                                  .name("DEVICENAME1")
                                  .build();

                                  
        sensors = new LinkedList<>();
        sensors.add(new SensorEntity(UUID.randomUUID(), SensorType.TEMP.name(), false, device));
        sensors.add(new SensorEntity(UUID.randomUUID(), SensorType.PM10.name(), false, device));
        sensors.add(new SensorEntity(UUID.randomUUID(), SensorType.PM25.name(), false, device));
    }

    @Test
    public void returnDeviceSensorWhenRegist() {
        
        List<Sensor> registSensors = this.sensors.stream().map(e -> new Sensor(null, SensorType.of(e.getType()))).collect(Collectors.toList());
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.of(device));

        DeviceSensor deviceSensor = deviceSensorService.regist(device.getId().toString(), registSensors);
        
        assertEquals(device.getIdentifier().toString(), deviceSensor.getDevice().getIdentifier());

    }

    @Test
    public void throwDataNotFoundExceptionWhenSaveWithUnregistedDevice() {
        List<Sensor> registSensors = this.sensors.stream().map(e -> new Sensor(null, SensorType.of(e.getType()))).collect(Collectors.toList());
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            deviceSensorService.regist(device.getId().toString(), registSensors);
        });
    }

    @Test
    public void returnDeviceSensorWhenGet() {
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.of(device));
        when(sensorRepository.findByDevice(any(DeviceEntity.class))).thenReturn(sensors);

        DeviceSensor deviceSensor = deviceSensorService.get(device.getIdentifier().toString());

        assertEquals(device.getIdentifier().toString(), deviceSensor.getDevice().getIdentifier());
    }

    @Test
    public void throwDataNotFoundExceptionWhenGetWithUnregistedDevice() {
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            deviceSensorService.get(device.getIdentifier().toString());
        });
    }

}
