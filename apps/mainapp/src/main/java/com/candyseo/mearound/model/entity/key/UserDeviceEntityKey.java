package com.candyseo.mearound.model.entity.key;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDeviceEntityKey implements Serializable {
    
    private String userId;

    private String deviceId;
}
