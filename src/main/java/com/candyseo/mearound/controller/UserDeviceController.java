package com.candyseo.mearound.controller;

import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.dto.user.DeviceId;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.dto.user.UserDevice;
import com.candyseo.mearound.service.user.UserDeviceService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mearound/users/{userId}/devices")
public class UserDeviceController {
    
    private static final String SENSORID_DELEMETER = ",";

    private UserDeviceService userDeviceService;

    public UserDeviceController(UserDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }
    
    @PostMapping(value="/", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDevice registUserDevice( @PathVariable("userId") String userId,
                                                        @RequestBody DeviceId deviceId) {

        log.info("Request to regist: userId[{}], deviceId[{}]", userId, deviceId.getDeviceId());

        return userDeviceService.setUserActiveDevice(userId, deviceId.getDeviceId());
    }
    
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDevice getUserDevice(@PathVariable("userId") String userId) {

        log.info("Request to get: userId[{}]", userId);

        return userDeviceService.getUserDevice(userId);
    }

}
