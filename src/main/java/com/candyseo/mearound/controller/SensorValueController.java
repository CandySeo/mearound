package com.candyseo.mearound.controller;

import java.util.List;

import com.candyseo.mearound.model.dto.device.SensorValue;
import com.candyseo.mearound.service.value.SensorValueService;

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
@RequestMapping("/mearound/sensorvalues")
public class SensorValueController {
    
    private SensorValueService sensorValueService;

    public SensorValueController(SensorValueService sensorValueService) {
        this.sensorValueService = sensorValueService;
    }

    @PostMapping(value="/", 
                consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int registSensorValue(@RequestBody List<SensorValue> sensorValues) {

        log.info("Request to regist: {}", sensorValues);

        return sensorValueService.appendAll(sensorValues);
    }

    @GetMapping(value="/sensors/{sensorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SensorValue> getSensorValues(@PathVariable("sensorId") String sensorId) {

        log.info("Request to get: sensorid[{}]", sensorId);
        
        return sensorValueService.get(sensorId);
    }

}
