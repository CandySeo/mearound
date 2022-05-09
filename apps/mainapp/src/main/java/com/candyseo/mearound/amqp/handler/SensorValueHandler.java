package com.candyseo.mearound.amqp.handler;

import java.time.LocalDateTime;

import com.candyseo.mearound.amqp.AmqpMessageHandler;
import com.candyseo.mearound.amqp.Message;
import com.candyseo.mearound.model.SensorType;
import com.candyseo.mearound.model.dto.device.DeviceSensor;
import com.candyseo.mearound.model.dto.device.Sensor;
import com.candyseo.mearound.model.dto.device.SensorValue;
import com.candyseo.mearound.service.device.DeviceSensorService;
import com.candyseo.mearound.service.value.SensorValueService;

public class SensorValueHandler implements AmqpMessageHandler {
    
    public DeviceSensorService deviceSensorService;

    public SensorValueService sensorValueService;

    public SensorValueHandler(
        DeviceSensorService deviceSensorService,
        SensorValueService sensorValueService) {
            this.deviceSensorService = deviceSensorService;
            this.sensorValueService = sensorValueService;
    }

    @Override
    public void handler(Message message) {
        String deviceId = message.getDeviceId();
        DeviceSensor deviceSensor = deviceSensorService.get(deviceId);

        for (Sensor sensor: deviceSensor.getSensors()) {
            if (sensor.getType().equals(SensorType.of(message.getType()))) {
                sensorValueService.append(
                    new SensorValue(sensor.getId(), Double.parseDouble(message.getValue()), LocalDateTime.parse(message.getRegistedDateTime()))
                );
            }
        }
    }
    
}
