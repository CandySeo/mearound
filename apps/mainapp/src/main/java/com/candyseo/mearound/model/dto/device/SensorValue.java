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
public class SensorValue implements Comparable<SensorValue> {
    
    private String sensorId;

    private long sequence;

    private double value;

    private LocalDateTime registedDateTime;

    public SensorValue(String sensorId, double value, LocalDateTime registedDateTime) {
        this.sensorId = sensorId;
        this.value = value;
        this.registedDateTime = registedDateTime;
    }

    @Override
    public int compareTo(SensorValue o) {
        return this.registedDateTime.isAfter(o.getRegistedDateTime()) ? 1 : -1;
    }

}
