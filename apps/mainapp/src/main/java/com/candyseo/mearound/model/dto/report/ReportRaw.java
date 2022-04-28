package com.candyseo.mearound.model.dto.report;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportRaw {
    
    // SensorValue.sensorId
    private String sensorId;

    // SensorValue.sequence
    private long sequence;

    // Sensor.type
    private String type;

    // SensorValue.value
    private double value;

    // SensorValue.registedDateTime
    private LocalDateTime registedDateTime;

}
