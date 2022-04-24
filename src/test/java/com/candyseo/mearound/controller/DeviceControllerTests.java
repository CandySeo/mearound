package com.candyseo.mearound.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import com.candyseo.mearound.config.GlobalExceptionHandler;
import com.candyseo.mearound.config.WebConfiguration;
import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.device.Device;
import com.candyseo.mearound.service.device.DeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
public class DeviceControllerTests {
    
    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    Device device;

    @BeforeAll
    public static void setObjectMapper() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    public void setUp() {
        
        mockMvc = MockMvcBuilders.standaloneSetup(deviceController)
                                 .setControllerAdvice(GlobalExceptionHandler.class)
                                 .build();

        device = new Device(null, "DEVICEID1", "DEVICENAME1");
    }

    @Test
    public void returnUuidWhenRegistNewDevice() throws JsonProcessingException, Exception {

        when(deviceService.regist(any(Device.class))).thenReturn(UUID.randomUUID().toString());

        mockMvc.perform(post("/mearound/devices/")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(device)))
               .andExpect(status().isOk())
               .andDo(print());
    }

    @Test
    public void ThrowIllegalArgumentExceptionWhenAlreadyRegistDeviceId() throws JsonProcessingException, Exception {

        when(deviceService.regist(any(Device.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/mearound/devices/")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(device)))
               .andExpect(status().isBadRequest())
               .andDo(print());
    }

    @Test
    public void returnDeviceWhenGetRegistedDeviceId() throws JsonProcessingException, Exception {

        String identifier = "identifier";
        when(deviceService.get(any(String.class))).thenReturn(device);

        mockMvc.perform(get("/mearound/devices/" + identifier)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(device)))
               .andExpect(status().isOk())
               .andDo(print());
    }

    @Test
    public void ThrowDataNotFoundExceptionWhenGetUnregistedDevice() throws JsonProcessingException, Exception {

        String identifier = "identifier";
        when(deviceService.get(any(String.class))).thenThrow(DataNotFoundException.class);

        mockMvc.perform(get("/mearound/devices/" + identifier)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(device)))
               .andExpect(status().isNoContent())
               .andDo(print());
    }
}
