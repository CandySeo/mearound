package com.candyseo.mearound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.dto.user.UserDevice;
import com.candyseo.mearound.model.entity.user.UserDeviceEntity;
import com.candyseo.mearound.repository.user.UserDeviceRepository;
import com.candyseo.mearound.service.device.DeviceService;
import com.candyseo.mearound.service.user.UserDeviceServiceImpl;
import com.candyseo.mearound.service.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDeviceServiceTests2 {
    
    @Mock
    private UserDeviceRepository userDeviceRepository;

    @Mock
    public UserService userService;

    @Mock
    public DeviceService deviceService;

    @InjectMocks
    public UserDeviceServiceImpl userDeviceService;

    private User user;

    private String userIdentifier;

    private String deviceIdentifier;

    @BeforeEach
    public void setUp() {

        this.user = new User("USERID01", "PASSWORD01", "NICKNAME01");
        this.userIdentifier = UUID.randomUUID().toString();
        this.deviceIdentifier = UUID.randomUUID().toString();
    }


    @Test
    public void returnTrueWhenSetUserDevice() {

        UserDeviceEntity entity = new UserDeviceEntity(userIdentifier, deviceIdentifier, true);

        when(userDeviceRepository.findByDeviceIdAndIsActive(any(String.class), any(boolean.class))).thenReturn(Optional.empty());
        when(userDeviceRepository.findByUserId(any(String.class))).thenReturn(List.of());
        when(userDeviceRepository.save(any(UserDeviceEntity.class))).thenReturn(entity);
        
        boolean result = userDeviceService.setUserActiveDevice(userIdentifier, deviceIdentifier);

        assertTrue(result);
    }
    
    @Test
    public void throwIllegalArgumentExceptionWhenSetAlreadyUserDevice() {

        UserDeviceEntity entity = new UserDeviceEntity(userIdentifier, deviceIdentifier, true);
        
        when(userDeviceRepository.findByDeviceIdAndIsActive(any(String.class), any(boolean.class))).thenReturn(Optional.of(entity));
        
        assertThrows(IllegalArgumentException.class, () -> {
            userDeviceService.setUserActiveDevice(userIdentifier, deviceIdentifier);
        });
        
    }

    @Test
    public void returnUserDeviceWhenUserId() {

        UserDeviceEntity entity = new UserDeviceEntity(userIdentifier, deviceIdentifier, true);
        Device device = new Device(deviceIdentifier, "DEVICEID1", "DEVICENAME1");

        when(userDeviceRepository.findByUserId(any(String.class))).thenReturn(List.of(entity));
        when(deviceService.findAll(anySet())).thenReturn(List.of(device));
        when(userService.get(any(String.class))).thenReturn(user);

        UserDevice userDevice = userDeviceService.getUserDevice(userIdentifier);
        
        assertEquals(userDevice.getUser().getUserId(), user.getUserId());
        assertEquals(userDevice.getDeivces().size(), 1);
        assertEquals(userDevice.getActiveDevice().getId(), device.getId());
    }
        
    @Test
    public void throwDataNotFoundExceptionWhenUserIdHasNoDevice() {
        
        when(userDeviceRepository.findByUserId(any(String.class))).thenReturn(List.of());

        assertThrows(DataNotFoundException.class, () -> {
            userDeviceService.getUserDevice(userIdentifier);
        });        
    }
}
