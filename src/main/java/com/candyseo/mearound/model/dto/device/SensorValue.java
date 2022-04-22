package com.candyseo.mearound.model.dto.device;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SensorValue {
    
    private String sensorId;

    private long sequence;

    private double value;

    private LocalDateTime registedDateTime;

}
