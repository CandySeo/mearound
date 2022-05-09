package com.candyseo.mearound.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.model.entity.device.SensorValueEntity;
import com.candyseo.mearound.repository.value.SensorValueRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SensorValueRepositoryTests {
    
    @Autowired
    private SensorValueRepository sensorValueRepository;

    private List<SensorValueEntity> values;

    private String sensorId;

    @BeforeEach
    public void setUp() {
        this.sensorId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        values = new LinkedList<>();
        values.add(new SensorValueEntity(sensorId, 0, 21.9, now));
        values.add(new SensorValueEntity(sensorId, 0, 21.7, now.minusMinutes(2)));
        values.add(new SensorValueEntity(sensorId, 0, 21.8, now.minusMinutes(4)));
        values.add(new SensorValueEntity(sensorId, 0, 21.6, now.minusMinutes(8)));
        
    }

    @Test
    public void returnSensorValueListWhenSaveAll() {

        List<SensorValueEntity> registed = sensorValueRepository.saveAll(values);

        assertEquals(values.size(), registed.size());
    }

    @Test
    public void returnSensorValueWhenSave() {
        SensorValueEntity registed = sensorValueRepository.save(values.get(0));

        assertEquals(values.get(0).getValue(), registed.getValue());
    }

    @Test
    public void returnEmptyListWhenFindBySensorId() {

        List<SensorValueEntity> registed = sensorValueRepository.findBySensorId(sensorId);
        assertEquals(0, registed.size());
    }
    
    @Test
    public void returnSensorValueListWhenFindBySensorId() {

        List<SensorValueEntity> registed = sensorValueRepository.saveAll(values);
        assertEquals(values.size(), registed.size());

        List<SensorValueEntity> search = sensorValueRepository.findBySensorId(sensorId);

        assertEquals(values.size(), search.size());
    }
    
}
