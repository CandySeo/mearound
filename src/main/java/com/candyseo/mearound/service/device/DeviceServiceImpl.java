package com.candyseo.mearound.service.device;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.model.mapper.DeviceMapper;
import com.candyseo.mearound.repository.device.DeviceRepository;

import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;

    private DeviceMapper deviceMapper;

    public DeviceServiceImpl(DeviceRepository deviceRepository,
                             DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public String regist(Device device) {
        
        if (deviceRepository.findByDeviceId(device.getId()).isPresent()) {
            throw new IllegalArgumentException("Already registed device id `" + device.getId() + "`");
        }

        DeviceEntity registed = deviceRepository.save(deviceMapper.toEntity(device));
        return registed.getIdentifier().toString();
    }

    @Override
    public Device get(String identifier) {
        
        DeviceEntity registed = deviceRepository.findById(identifier).orElseThrow(
            () -> new DataNotFoundException("Not found device by identifier`" + identifier + "`")
        );

        return deviceMapper.toDto(registed);
    }
    
}
