package com.candyseo.mearound.repository.device;

import java.util.List;

import com.candyseo.mearound.model.entity.device.DeviceSensorEntity;
import com.candyseo.mearound.model.entity.key.DeviceSensorEntityKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceSensorRepository extends JpaRepository<DeviceSensorEntity, DeviceSensorEntityKey> {

    List<DeviceSensorEntity> findByDeviceId(String deviceId);

}
