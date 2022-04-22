package com.candyseo.mearound.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.model.SensorType;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.dto.device.DeviceSensor;
import com.candyseo.mearound.model.dto.device.Sensor;

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
    
    @PostMapping(value="/", 
                consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceSensor registDeviceSensor(@RequestBody DeviceSensor deviceSensor) {

        log.info("Request to regist: {}", deviceSensor);

        return new DeviceSensor(new Device(deviceSensor.getDevice().getId(), 
                                                        UUID.randomUUID().toString(), 
                                                        "devicename1"), 
                                deviceSensor.getSensors());
    }
    
    @GetMapping(value="/{sensorIds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceSensor getDeviceSensor(@PathVariable("deviceId") String deviceId,
                                        @PathVariable("sensorIds") String sensorIds) {

        log.info("Request to get: deviceid[{}] sensorId[{}]", deviceId, sensorIds);
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor("SENSORID1", SensorType.TEMP));
        
        return new DeviceSensor(new Device("DEVICEID1", UUID.randomUUID().toString(), "DEVICENAME1"), sensors);
    }

}
