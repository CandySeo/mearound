package com.candyseo.mearound.repository.device;

import java.util.Optional;
import java.util.UUID;

import com.candyseo.mearound.model.entity.device.DeviceEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {
    
    Optional<DeviceEntity> findByDeviceId(String id);
}
