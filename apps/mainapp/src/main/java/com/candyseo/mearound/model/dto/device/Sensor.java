package com.candyseo.mearound.model.dto.device;

import com.candyseo.mearound.model.SensorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    
    private String id;

    private SensorType type;
    
}
