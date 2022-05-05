package com.candyseo.mearound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.candyseo.mearound.annotation.EnableRedis;
import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.model.mapper.DeviceMapper;
import com.candyseo.mearound.repository.device.DeviceRepository;
import com.candyseo.mearound.service.device.DeviceServiceImpl;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@EnableRedis
@ExtendWith(MockitoExtension.class)
public class DeviceServiceTests {
    
    @Mock
    private DeviceRepository deviceRepository;

    @Spy
    private DeviceMapper deviceMapper;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    private DeviceEntity deviceEntity;

    private Device device;

    @BeforeEach
    public void setUp() {
        this.deviceMapper = new DeviceMapper();
        this.deviceEntity = DeviceEntity.builder()
                                        .identifier(UUID.randomUUID())
                                        .deviceId("DEVICEID01")
                                        .name("DEVICENAME01")
                                        .build();
        this.device = deviceMapper.toDto(deviceEntity);
    }

    @Test
    public void beans() {
        assertNotNull(deviceService);
    }

    @Test
    public void returnRegistedUuidWhenRegist() {
        
        when(deviceRepository.save(any(DeviceEntity.class))).thenReturn(deviceEntity);
        
        String uuid = deviceService.regist(device);

        assertNotNull(uuid);
    }

    @Test
    public void throwIllegalArgumentExceptionWhenRegistAlready() {

        when(deviceRepository.findByDeviceId(anyString())).thenReturn(Optional.of(deviceEntity));

        assertThrows(IllegalArgumentException.class, () -> {
            deviceService.regist(device);
        });

    }

    @Test
    public void returnDeviceWhenGet() {

        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.of(deviceEntity));

        Device searched = deviceService.get(deviceEntity.getIdentifier().toString());

        assertEquals(deviceEntity.getDeviceId(), searched.getId());
        assertEquals(deviceEntity.getName(), searched.getName());
    }

    @Test
    public void throwDataNotFoundExceptionWhenGetUnRegisted() {

        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            deviceService.get(deviceEntity.getIdentifier().toString());
        });
    }

    @Test
    public void returnDeviceListWhenGetAll() {

        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.of(deviceEntity));

        Set<String> set = new HashSet<>(List.of(
            deviceEntity.getIdentifier().toString(), 
            UUID.randomUUID().toString()
        ));
        List<Device> devices = deviceService.findAll(set);

        assertEquals(2, devices.size());
    }

    @Test
    public void emptyDeviceListWhenGetAll() {
        when(deviceRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Set<String> set = new HashSet<>(List.of(
            deviceEntity.getIdentifier().toString(), 
            UUID.randomUUID().toString()
        ));
        List<Device> devices = deviceService.findAll(set);

        assertEquals(0, devices.size());
    }

}
