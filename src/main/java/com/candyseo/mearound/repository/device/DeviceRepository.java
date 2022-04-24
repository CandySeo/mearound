package com.candyseo.mearound.repository.device;

import com.candyseo.mearound.model.entity.device.DeviceEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {
    
}
