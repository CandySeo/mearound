package com.candyseo.mearound.service.value;

import java.util.List;

import com.candyseo.mearound.model.dto.device.SensorValue;

public interface SensorValueService {
    
    int appendAll(List<SensorValue> values);

    List<SensorValue> get(String sensorId);
}
