package com.candyseo.mearound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import com.candyseo.mearound.config.WebConfiguration;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.dto.user.UserDevice;
import com.candyseo.mearound.model.mapper.DeviceMapper;
import com.candyseo.mearound.model.mapper.UserMapper;
import com.candyseo.mearound.repository.device.DeviceRepository;
import com.candyseo.mearound.repository.user.UserDeviceRepository;
import com.candyseo.mearound.repository.user.UserRepository;
import com.candyseo.mearound.service.device.DeviceService;
import com.candyseo.mearound.service.device.DeviceServiceImpl;
import com.candyseo.mearound.service.user.UserDeviceService;
import com.candyseo.mearound.service.user.UserService;
import com.candyseo.mearound.service.user.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Disabled("Instead use UserDeviceServiceTests2.class")
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
public class UserDeviceServiceTests {
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @SpyBean
    public UserService userService;

    @SpyBean
    public DeviceService deviceService;

    @Autowired
    public UserDeviceService userDeviceService;

    @BeforeEach
    public void setUp() {
        this.userService = new UserServiceImpl(userMapper, userRepository);
        this.deviceService = new DeviceServiceImpl(deviceRepository, deviceMapper);
    }

    @Test
    public void returnTrueWhenSetUserDevice() {

        User user = new User("USERID01", "PASSWORD01", "NICKNAME01");
        Device device = new Device("DEVICEID01", "DEVICENAME01");

        String userIdentifier = userService.regist(user);
        String deviceIdentifier = deviceService.regist(device);

        assertNotNull(userIdentifier);
        assertNotNull(deviceIdentifier);

        UserDevice result = userDeviceService.setUserActiveDevice(userIdentifier, deviceIdentifier);

        assertEquals(result.getUser().getIdentifier().toString(), userIdentifier);
    }

    @Test
    @Transactional
    public void returnUserDeviceWhenUserId() {
        User user = new User("USERID01", "PASSWORD01", "NICKNAME01");
        Device device = new Device("DEVICEID01", "DEVICENAME01");

        String userIdentifier = userService.regist(user);
        String deviceIdentifier = deviceService.regist(device);

        assertEquals(userIdentifier.length(), 36);
        assertNotNull(UUID.fromString(userIdentifier));
        assertNotNull(UUID.fromString(deviceIdentifier));

        UserDevice result = userDeviceService.setUserActiveDevice(userIdentifier, deviceIdentifier);

        assertEquals(result.getUser().getIdentifier().toString(), userIdentifier);

        UserDevice userDevice = userDeviceService.getUserDevice(userIdentifier);

        assertEquals(userDevice.getUser().getIdentifier().toString(), userIdentifier);
        assertEquals(userDevice.getDevices().size(), 1);
    }
}
