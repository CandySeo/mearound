package com.candyseo.mearound.service.device;

import com.candyseo.mearound.model.dto.device.Device;

public interface DeviceService {
    
    public String regist(Device device);

    public Device get(Device id);

}
