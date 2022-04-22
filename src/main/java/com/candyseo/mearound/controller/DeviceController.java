package com.candyseo.mearound.controller;

import java.util.UUID;

import com.candyseo.mearound.model.dto.device.Device;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mearound/devices")
public class DeviceController {
    
    @PostMapping(value="/", 
                consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registDevice(@RequestBody Device device) {

        log.info("Request to regist: {}", device);

        return UUID.randomUUID().toString();
    }

    @GetMapping(value="/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Device getDevice(@PathVariable("deviceId") String id) {

        log.info("Request to get: id[{}]", id);
        
        return new Device(UUID.randomUUID().toString(), "DEVICEID1", "DEVICENAME1");
    }

}
