package com.candyseo.mearound.model.entity.device;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.candyseo.mearound.model.entity.key.SensorValueEntityKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor_values")
@IdClass(value = SensorValueEntityKey.class)
public class SensorValueEntity implements Comparable<SensorValueEntity> {
    
    @Id
    @Column(name = "sensor_id", nullable = false, length = 36)
    private String sensorId;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long sequence;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private LocalDateTime registedDateTime;

    public SensorValueEntity(String sensorId, double value, LocalDateTime registedDateTime) {
        this.sensorId = sensorId;
        this.value = value;
        this.registedDateTime = registedDateTime;
    }

    @Override
    public int compareTo(SensorValueEntity o) {
        return this.registedDateTime.isAfter(o.getRegistedDateTime()) ? 1 : -1;
    }

}
