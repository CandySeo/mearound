package com.candyseo.mearound.model.mapper;

import java.util.UUID;

import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.entity.device.DeviceEntity;

public class DeviceMapper implements GenericMapper<Device, DeviceEntity> {

    @Override
    public Device toDto(DeviceEntity entity) {
        return new Device(entity.getIdentifier().toString(), entity.getDeviceId(), entity.getName());
    }

    @Override
    public DeviceEntity toEntity(Device dto) {
        return new DeviceEntity(UUID.fromString(dto.getIdentifier()), dto.getId(), dto.getName());
    }
    
}
