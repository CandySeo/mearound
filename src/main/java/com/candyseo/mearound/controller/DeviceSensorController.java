package com.candyseo.mearound.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.model.SensorType;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.dto.device.DeviceSensor;
import com.candyseo.mearound.model.dto.device.Sensor;
import com.candyseo.mearound.service.device.DeviceSensorService;

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
@RequestMapping("/mearound/devices/{deviceId}/sensors")
public class DeviceSensorController {

    private static final String SENSORID_DELEMETER = ",";

    private DeviceSensorService deviceSensorService;

    public DeviceSensorController(DeviceSensorService deviceSensorService) {
        this.deviceSensorService = deviceSensorService;
    }
    
    @PostMapping(value="/", 
                consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceSensor registDeviceSensor( @PathVariable("deviceId") String deviceId,
                                            @RequestBody DeviceSensor deviceSensor) {

        log.info("Request to regist: {}", deviceSensor);

        return deviceSensorService.regist(deviceId, deviceSensor.getSensors());
    }
    
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceSensor getDeviceSensor(@PathVariable("deviceId") String deviceId) {

        log.info("Request to get: deviceid[{}]", deviceId);
                
        return deviceSensorService.get(deviceId);
    }

}
