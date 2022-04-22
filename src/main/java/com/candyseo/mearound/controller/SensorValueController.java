package com.candyseo.mearound.controller;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.candyseo.mearound.model.dto.device.SensorValue;

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
        
    @PostMapping(value="/", 
                consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public long registSensorValue(@RequestBody SensorValue sensorValue) {

        log.info("Request to regist: {}", sensorValue);

        return sensorValue.getSequence();
    }

    @GetMapping(value="/{sensorValueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SensorValue getSensorValue(@PathVariable("sensorValueId") String id) {

        log.info("Request to get: id[{}]", id);
        
        return new SensorValue(id, 1, 21.9, LocalDateTime.now());
    }

    @GetMapping(value="/sensors/{sensorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SensorValue> getSensorValues(@PathVariable("sensorId") String id) {

        log.info("Request to get: sensorid[{}]", id);

        LocalDateTime now = LocalDateTime.now();
        List<SensorValue> values = new LinkedList<>();
        values.add(new SensorValue(id, 4, 21.9, now));
        values.add(new SensorValue(id, 3, 21.7, now.minusMinutes(2)));
        values.add(new SensorValue(id, 2, 21.8, now.minusMinutes(4)));
        values.add(new SensorValue(id, 1, 21.6, now.minusMinutes(8)));
        
        return values;
    }

}
