package com.candyseo.mearound.service.device;

import java.util.List;

import com.candyseo.mearound.model.dto.device.DeviceSensor;
import com.candyseo.mearound.model.dto.device.Sensor;

public interface DeviceSensorService {
    
    public DeviceSensor regist(String deviceId, List<Sensor> sensors);

    public DeviceSensor delete(String deviceId, List<Sensor> sensors);

    public void deleteAllByDevice(String deviceId);

    public boolean isActive(String sensorId);

}
