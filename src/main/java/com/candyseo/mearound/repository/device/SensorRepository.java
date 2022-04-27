package com.candyseo.mearound.repository.device;

import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.model.entity.device.SensorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<SensorEntity, UUID> {
    
    List<SensorEntity> findByDevice(DeviceEntity device);

    void deleteAllByDevice(DeviceEntity device);
}
