package com.candyseo.mearound.model.entity.user;

import com.candyseo.mearound.model.entity.device.DeviceEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDeviceEntity {
    
    private UserEntity user;

    private DeviceEntity deivces;

}
