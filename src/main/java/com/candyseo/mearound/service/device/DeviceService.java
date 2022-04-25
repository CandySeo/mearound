package com.candyseo.mearound.service.device;

import java.util.List;
import java.util.Set;

import com.candyseo.mearound.model.dto.device.Device;

public interface DeviceService {
    
    public String regist(Device device);

    public Device get(String identifier);

    public List<Device> findAll(Set<String> identifier);

}
