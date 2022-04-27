package com.candyseo.mearound.service.device;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.SensorType;
import com.candyseo.mearound.model.dto.device.DeviceSensor;
import com.candyseo.mearound.model.dto.device.Sensor;
import com.candyseo.mearound.model.entity.device.DeviceEntity;
import com.candyseo.mearound.model.entity.device.SensorEntity;
import com.candyseo.mearound.model.mapper.DeviceMapper;
import com.candyseo.mearound.repository.device.DeviceRepository;
import com.candyseo.mearound.repository.device.SensorRepository;
import com.candyseo.mearound.util.UuidUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceSensorServiceImpl implements DeviceSensorService {
    
    private DeviceRepository deviceRepository;

    private SensorRepository sensorRepository;

    private DeviceMapper deviceMapper;

    public DeviceSensorServiceImpl(SensorRepository sensorRepository,
                                   DeviceRepository deviceRepository,
                                   DeviceMapper deviceMapper) {
        this.sensorRepository = sensorRepository;
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    private DeviceEntity getValidDevice(String deviceId) {

        if (!UuidUtils.isUuidFormat(deviceId)) {
            throw new IllegalArgumentException("DeviceId `" + deviceId + "` is not uuid format.");
        }
        
        DeviceEntity device = deviceRepository.findById(UUID.fromString(deviceId)).orElseThrow(() -> 
            new DataNotFoundException("Device `" + deviceId + "` is unregisted.")
        );

        return device;
    }

    @Override
    @Transactional
    public DeviceSensor regist(String deviceId, List<Sensor> sensors) {

        DeviceEntity device = getValidDevice(deviceId);

        List<SensorEntity> registed = sensorRepository.saveAll(sensors.stream()
                                                                      .map(s -> new SensorEntity(null, s.getType().name(), true, device))
                                                                      .collect(Collectors.toSet()));

        return new DeviceSensor(deviceMapper.toDto(device), registed.stream()
                                                                    .map(s -> new Sensor(s.getIdentifier().toString(), SensorType.valueOf(s.getType())))
                                                                    .collect(Collectors.toList()));
    }

    @Override
    public DeviceSensor delete(String deviceId, List<Sensor> sensors) {
        DeviceEntity device = getValidDevice(deviceId);
        List<SensorEntity> entities = sensorRepository.findAllById(sensors.stream()
                                                                          .map(s -> UUID.fromString(s.getId()))
                                                                          .collect(Collectors.toSet()));

        sensorRepository.deleteAllById(entities.stream()
                                               .filter(e -> e.getDevice().getIdentifier().equals(device.getIdentifier()))
                                               .map(e -> e.getId())
                                               .collect(Collectors.toSet()));

        List<SensorEntity> registed = sensorRepository.findByDevice(device);

        return new DeviceSensor(deviceMapper.toDto(device), registed.stream()
                                                                    .map(s -> new Sensor(s.getIdentifier().toString(), SensorType.valueOf(s.getType())))
                                                                    .collect(Collectors.toList()));
    }

    @Override
    public void deleteAllByDevice(String deviceId) {
        DeviceEntity device = getValidDevice(deviceId);

        sensorRepository.deleteAllByDevice(device);
    }

    @Override
    public boolean isActive(String sensorId) {
        return sensorRepository.findById(UUID.fromString(sensorId)).isPresent();
    }

}
