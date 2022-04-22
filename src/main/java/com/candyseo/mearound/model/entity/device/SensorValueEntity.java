package com.candyseo.mearound.model.entity.device;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SensorValueEntity {
    
    private String sensorId;

    private long sequence;

    private double value;

    private LocalDateTime registedDateTime;

}
