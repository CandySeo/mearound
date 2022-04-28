package com.candyseo.mearound.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.config.GlobalExceptionHandler;
import com.candyseo.mearound.config.WebConfiguration;
import com.candyseo.mearound.model.dto.device.SensorValue;
import com.candyseo.mearound.service.value.SensorValueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
public class SensorValueControllerTests {
    
    @Mock
    private SensorValueService sensorValueService;

    @InjectMocks
    private SensorValueController sensorValueController;

    private MockMvc mockMvc;

    private List<SensorValue> values;

    private String sensorId;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(sensorValueController)
                                      .setControllerAdvice(GlobalExceptionHandler.class)
                                      .build();
        this.objectMapper = new ObjectMapper();
        
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        LocalDateTime now = LocalDateTime.now();
        this.sensorId = UUID.randomUUID().toString();
        values = new LinkedList<>();
        values.add(new SensorValue(sensorId, 0L, 15.0, now));
        values.add(new SensorValue(sensorId, 0L, 15.1, now.minusMinutes(2L)));
        values.add(new SensorValue(sensorId, 0L, 15.2, now.minusMinutes(4L)));
        values.add(new SensorValue(sensorId, 0L, 15.3, now.minusMinutes(6L)));

    }

    @Test
    public void returnCountWhenRegist() throws JsonProcessingException, Exception {

        when(sensorValueService.appendAll(anyList())).thenReturn(values.size());
        
        MvcResult result = mockMvc.perform(post("/mearound/sensorvalues/")
                                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                                            .content(objectMapper.writeValueAsString(values)))
                                    .andExpect(status().isOk())
                                    .andDo(print())
                                    .andReturn();

        String resultBody = result.getResponse().getContentAsString();

        assertEquals(values.size(), Integer.valueOf(resultBody));

    }

    @Test
    public void returnListWhenGetBySensorId() throws Exception {

        when(sensorValueService.get(any(String.class))).thenReturn(values);

        
        MvcResult result = mockMvc.perform(get("/mearound/sensorvalues/sensors/" + sensorId)
                                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                                    .andExpect(status().isOk())
                                    .andDo(print())
                                    .andReturn();

        String resultBody = result.getResponse().getContentAsString();
        List<SensorValue> resultList = objectMapper.readValue(resultBody, new TypeReference<List<SensorValue>>(){});

        assertEquals(values.size(), resultList.size());
    }

}
