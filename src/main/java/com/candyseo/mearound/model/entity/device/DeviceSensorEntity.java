package com.candyseo.mearound.model.entity.device;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.candyseo.mearound.model.entity.key.DeviceSensorEntityKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device_sensors")
@IdClass(DeviceSensorEntityKey.class)
public class DeviceSensorEntity implements Serializable {

    @Id
    @Column(name = "device_id", nullable = false, updatable = false, length = 36)
    private String deviceId;

    @Id
    @Column(name = "sensor_id", nullable = false, updatable = false, length = 36)
    private String sensorId;

}
