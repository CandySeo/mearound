package com.candyseo.mearound.model.entity.key;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SensorValueEntityKey implements Serializable {
    
    private String sensorId;

    private long sequence;

}
