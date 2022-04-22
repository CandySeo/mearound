package com.candyseo.mearound.model.entity.device;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSensorEntity {
    
    private DeviceEntity device;

    private List<SensorEntity> sensors;

}
