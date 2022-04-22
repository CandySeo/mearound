package com.candyseo.mearound.model.dto.user;

import java.util.List;

import com.candyseo.mearound.model.dto.device.Device;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDevice {
    
    private User user;

    private Device activeDevice;

    private List<Device> deivces;

}
