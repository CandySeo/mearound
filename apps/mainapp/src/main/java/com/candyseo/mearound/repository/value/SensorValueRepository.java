package com.candyseo.mearound.repository.value;

import java.util.List;

import com.candyseo.mearound.model.entity.device.SensorValueEntity;
import com.candyseo.mearound.model.entity.key.SensorValueEntityKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorValueRepository extends JpaRepository<SensorValueEntity, SensorValueEntityKey> {
    
    List<SensorValueEntity> findBySensorId(String sensorId);

}
