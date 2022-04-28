package com.candyseo.mearound.model.mapper;

import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.entity.device.DeviceEntity;

import org.springframework.stereotype.Component;

@Component
public class DeviceMapper implements GenericMapper<Device, DeviceEntity> {

    @Override
    public Device toDto(DeviceEntity entity) {
        return new Device(entity.getIdentifier().toString(), entity.getDeviceId(), entity.getName());
    }

    @Override
    public DeviceEntity toEntity(Device dto) {

        return DeviceEntity.builder()
                           .deviceId(dto.getId())
                           .name(dto.getName())
                           .build();
    }
    
}
