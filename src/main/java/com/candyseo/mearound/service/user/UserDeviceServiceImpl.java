package com.candyseo.mearound.service.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.dto.user.UserDevice;
import com.candyseo.mearound.model.entity.user.UserDeviceEntity;
import com.candyseo.mearound.repository.user.UserDeviceRepository;
import com.candyseo.mearound.service.device.DeviceService;
import com.candyseo.mearound.util.UuidUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    private UserService userService;

    private DeviceService deviceService;

    private UserDeviceRepository userDeviceRepository;

    public UserDeviceServiceImpl(UserService userService,
                                 DeviceService deviceService,
                                 UserDeviceRepository userDeviceRepository) {
        this.userService = userService;
        this.deviceService = deviceService;
        this.userDeviceRepository = userDeviceRepository;
    }

    @Override
    @Transactional
    public UserDevice setUserActiveDevice(String userIdentifier, String deviceIdentifier) {
        
        if (!UuidUtils.isUuidFormat(userIdentifier)) {
            throw new IllegalArgumentException("UserIdentifier `" + userIdentifier + "` not uuid format.");
        }

        if (!UuidUtils.isUuidFormat(deviceIdentifier)) {
            throw new IllegalArgumentException("DeviceIdentifier `" + deviceIdentifier + "` not uuid format.");
        }

        if (userDeviceRepository.findByDeviceIdAndIsActive(deviceIdentifier, true).isPresent()) {
            throw new IllegalArgumentException("Device `" + deviceIdentifier + "` is already use.");
        }

        UserDeviceEntity activeUserDeviceEntity = null;
        List<UserDeviceEntity> userDevices = userDeviceRepository.findByUserId(userIdentifier);
        for (UserDeviceEntity userDevice: userDevices) {

            if (userDevice.isActive()) {
                userDevice.setIsActive(false);
                userDeviceRepository.save(userDevice);
            }

            if (userDevice.getDeviceId().equals(deviceIdentifier)) {
                userDevice.setIsActive(true);
                userDeviceRepository.save(userDevice);
                activeUserDeviceEntity = userDevice;
            }
        }

        if (activeUserDeviceEntity == null) {
            activeUserDeviceEntity = userDeviceRepository.save(new UserDeviceEntity(userIdentifier, deviceIdentifier, true));
        }

        log.info("Registed user device: {}", activeUserDeviceEntity);

        Set<String> deviceIdentifiers = userDevices.stream().map(u -> u.getDeviceId()).collect(Collectors.toSet());
        deviceIdentifiers.add(deviceIdentifier);

        try {
            User user = userService.get(userIdentifier);
            Device activeDevice = deviceService.get(deviceIdentifier);
            List<Device> devices = deviceService.findAll(deviceIdentifiers);
            
            return new UserDevice(user, activeDevice, devices);
        } catch(Exception e) {
            throw new IllegalArgumentException(e);
        }

        // return new UserDevice(user, activeDevice, devices);
        // return makeUserDevice(userIdentifier, deviceIdentifier, userDevices.stream().map(u -> u.getDeviceId()).collect(Collectors.toSet()));
    }

    private UserDevice makeUserDevice(String userIdentifier, String activeDeviceIdentifier, Set<String> deviceIdentifiers) {

        User user = userService.get(userIdentifier);
        Device activeDevice = deviceService.get(activeDeviceIdentifier);
        List<Device> devices = deviceService.findAll(deviceIdentifiers);

        return new UserDevice(user, activeDevice, devices);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDevice getUserDevice(String userIdentifier) {

        if (!UuidUtils.isUuidFormat(userIdentifier)) {
            throw new IllegalArgumentException("UserIdentifier `" + userIdentifier + "` not uuid format.");
        }

        List<UserDeviceEntity> userDevices = userDeviceRepository.findByUserId(userIdentifier);

        if (userDevices.isEmpty()) {
            throw new DataNotFoundException("User `" + userIdentifier + "` has not devices.");
        }

        String activeDeviceIdentifier = null;
        for (UserDeviceEntity entity : userDevices) {
            if (entity.isActive()) {
                activeDeviceIdentifier = entity.getDeviceId();
            }
        }

        List<Device> devices = deviceService.findAll(userDevices.stream()
                                                                .map(e -> e.getDeviceId())
                                                                .collect(Collectors.toSet()));

        Device activeDevice = null;
        for (Device device: devices) {
            if (device.getIdentifier().equals(activeDeviceIdentifier)) {
                activeDevice = device;
            }
        }

        return new UserDevice(userService.get(userIdentifier), activeDevice, devices);
    }
    
}
