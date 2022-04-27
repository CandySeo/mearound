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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor_values")
@IdClass(value = SensorValueEntityKey.class)
public class SensorValueEntity {
    
    @Id
    @Column(name = "sensor_id", nullable = false, length = 36)
    private String sensorId;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long sequence;

    @NonNull
    private double value;

    @NonNull
    private LocalDateTime registedDateTime;

}
