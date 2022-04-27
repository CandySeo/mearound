package com.candyseo.mearound.service.value;

import java.util.List;
import java.util.stream.Collectors;

import com.candyseo.mearound.model.dto.device.SensorValue;
import com.candyseo.mearound.model.entity.device.SensorValueEntity;
import com.candyseo.mearound.repository.value.SensorValueRepository;

import org.springframework.stereotype.Service;

@Service
public class SensorValueServiceImpl implements SensorValueService {

    private SensorValueRepository sensorValueRepository;

    public SensorValueServiceImpl(SensorValueRepository sensorValueRepository) {
        this.sensorValueRepository = sensorValueRepository;
    }

    @Override
    public int appendAll(List<SensorValue> values) {
        return sensorValueRepository.saveAll(values.stream()
                                                   .map(v -> new SensorValueEntity(v.getValue(), v.getRegistedDateTime()))
                                                   .collect(Collectors.toList()))
                                    .size();
    }

    @Override
    public List<SensorValue> get(String sensorId) {
        
        return sensorValueRepository.findBySensorId(sensorId).stream()
                                                             .map(s -> new SensorValue(s.getSensorId(), s.getSequence(), s.getValue(), s.getRegistedDateTime()))
                                                             .collect(Collectors.toList());
    }
}
