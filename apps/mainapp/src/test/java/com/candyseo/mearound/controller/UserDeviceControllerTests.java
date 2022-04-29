package com.candyseo.mearound.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import com.candyseo.mearound.config.WebConfiguration;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.service.user.UserDeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
public class UserDeviceControllerTests {
    
    @Mock
    private UserDeviceService userDeviceService;

    @InjectMocks
    private UserDeviceController userDeviceController;

    private MockMvc mockMvc;

    private User user;
    
    private static ObjectMapper mapper;

    @Test
    public void uuidConvertTest() {

        String uuid = UUID.randomUUID().toString();
        String notUuid = "not uuid";
        String plainUuid = "fdf7666a-b4aa-4c47-84ec-7b84e4556326";

        UUID uuidConverted = UUID.fromString(uuid);
        UUID uuidConverted2 = UUID.fromString(plainUuid);
        
        assertThrows(IllegalArgumentException.class, () -> {
            UUID failConverted = UUID.fromString(notUuid);
        });

    }

}
