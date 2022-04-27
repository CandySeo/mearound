package com.candyseo.mearound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.candyseo.mearound.model.dto.device.SensorValue;
import com.candyseo.mearound.model.entity.device.SensorValueEntity;
import com.candyseo.mearound.repository.value.SensorValueRepository;
import com.candyseo.mearound.service.value.SensorValueServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SensorValueServiceTests {
    
    @Mock
    private SensorValueRepository sensorValueRepository;

    @InjectMocks
    private SensorValueServiceImpl sensorValueService;

    private List<SensorValue> values;

    private String sensorId;

    @BeforeEach
    private void setUp() {

        LocalDateTime now = LocalDateTime.now();
        this.sensorId = UUID.randomUUID().toString();
        values = new LinkedList<>();
        values.add(new SensorValue(sensorId, 0L, 15.0, now));
        values.add(new SensorValue(sensorId, 0L, 15.1, now.minusMinutes(2L)));
        values.add(new SensorValue(sensorId, 0L, 15.2, now.minusMinutes(4L)));
        values.add(new SensorValue(sensorId, 0L, 15.3, now.minusMinutes(6L)));
    }

    @Test
    public void returnRegistedCountWhenAppendAll() {

        List<SensorValueEntity> registed = this.values.stream()
                                                      .map(v -> new SensorValueEntity(sensorId, 0L, v.getValue(), v.getRegistedDateTime()))
                                                      .collect(Collectors.toList());
        when(sensorValueRepository.saveAll(anyList())).thenReturn(registed);

        int registedCount = sensorValueService.appendAll(values);

        assertEquals(values.size(), registedCount);
    }

    @Test
    public void returnSensorValueListWhenFindBySensorId() {
        List<SensorValueEntity> registed = this.values.stream()
                                                      .map(v -> new SensorValueEntity(sensorId, 0L, v.getValue(), v.getRegistedDateTime()))
                                                      .collect(Collectors.toList());
        when(sensorValueRepository.findBySensorId(any(String.class))).thenReturn(registed);

        List<SensorValue> find = sensorValueService.get(this.sensorId);

        assertEquals(registed.size(), find.size());
    }

    @Test
    public void returnEmptyListWhenFindBySensorIdNotRegisted() {
        when(sensorValueRepository.findBySensorId(any(String.class))).thenReturn(List.of());

        List<SensorValue> find = sensorValueService.get(this.sensorId);

        assertEquals(0, find.size());
    }
}
