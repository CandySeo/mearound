package com.candyseo.mearound.service.device;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.model.mapper.DeviceMapper;
import com.candyseo.mearound.repository.device.DeviceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        
        DeviceEntity registed = deviceRepository.findById(UUID.fromString(identifier)).orElseThrow(
            () -> new DataNotFoundException("Not found device by identifier`" + identifier + "`")
        );

        return deviceMapper.toDto(registed);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Device> findAll(Set<String> identifiers) {

        List<Device> devices = new LinkedList<>();
        for (String id : identifiers) {
            deviceRepository.findById(UUID.fromString(id)).ifPresent(entity -> {
                devices.add(deviceMapper.toDto(entity));
            });
        }
        
        return devices;
    }
    
}
