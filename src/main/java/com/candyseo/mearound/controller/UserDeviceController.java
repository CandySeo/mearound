package com.candyseo.mearound.controller;

import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.dto.user.UserDevice;
import com.candyseo.mearound.service.user.UserDeviceService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mearound/users/{userId}/devices/")
public class UserDeviceController {
    
    private static final String SENSORID_DELEMETER = ",";

    private UserDeviceService userDeviceService;

    public UserDeviceController(UserDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }
    
    @PostMapping(value="/{deviceId}", 
                consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDevice registUserDevice( @PathVariable("userId") String userId,
                                        @PathVariable("deviceId") String deviceId) {

        log.info("Request to regist: userId[{}], deviceId[{}]", userId, deviceId);

        return userDeviceService.setUserActiveDevice(userId, deviceId);
    }
    
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDevice getUserDevice(@PathVariable("userId") UUID userId) {

        log.info("Request to get: userId[{}]", userId);

        Device userDevice = new Device("DEVICEID1", UUID.randomUUID().toString(), "DEVICENAME1");
        Device userDevice2 = new Device("DEVICEID2", UUID.randomUUID().toString(), "DEVICENAME2");
        
        return new UserDevice(new User(userId, "USERID1", "PASSWORD1", "USERNAME1"), userDevice, List.of(userDevice, userDevice2));
    }

}
