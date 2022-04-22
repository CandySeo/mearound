package com.candyseo.mearound.model.entity.device;

import com.candyseo.mearound.model.SensorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SensorEntity {
    
    private String id;

    private SensorType type;
    
}
