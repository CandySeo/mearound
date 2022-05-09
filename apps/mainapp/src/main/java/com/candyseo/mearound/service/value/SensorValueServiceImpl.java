package com.candyseo.mearound.service.value;

import java.util.Collections;
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

        Collections.sort(values);
        return sensorValueRepository.saveAll(values.stream()
                                                   .map(v -> new SensorValueEntity(v.getSensorId(), v.getValue(), v.getRegistedDateTime()))
                                                   .collect(Collectors.toList()))
                                    .size();
    }


    public int append(SensorValue values) {
        
        SensorValueEntity registed = sensorValueRepository.save(
            new SensorValueEntity(values.getSensorId(), values.getValue(), values.getRegistedDateTime())
        );

        return registed.getSequence() > 0L ? 1 : 0;
    }

    @Override
    public List<SensorValue> get(String sensorId) {
        
        return sensorValueRepository.findBySensorId(sensorId).stream()
                                                             .map(s -> new SensorValue(s.getSensorId(), s.getSequence(), s.getValue(), s.getRegistedDateTime()))
                                                             .collect(Collectors.toList());
    }
}
