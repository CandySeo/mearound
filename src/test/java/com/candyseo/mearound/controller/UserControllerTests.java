package com.candyseo.mearound.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import com.candyseo.mearound.config.GlobalExceptionHandler;
import com.candyseo.mearound.config.WebConfiguration;
import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.service.user.UserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = WebConfiguration.class)
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private User user;
    
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setMapper() {
        mapper = new ObjectMapper();
    }

    @BeforeEach
    public void setup() {
        assertNotNull(userService);
        assertNotNull(userController);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                                 .setControllerAdvice(GlobalExceptionHandler.class)
                                 .build();
        
        user = new User(null, "userid1", "password1", "nickname1");
    }

    @Test
    public void returnUuidWhenRegistNewUser() throws JsonProcessingException, Exception {

        when(userService.regist(any(User.class))).thenReturn(UUID.randomUUID().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/mearound/users/")
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .characterEncoding("utf-8")
                                              .content(mapper.writeValueAsString(user)))
               .andExpect(status().isOk())
               .andDo(print());
        
    }

    @Test
    public void ThrowIllegalArgumentExceptionWhenAlreadyRegistNewUser() throws JsonProcessingException, Exception {

        when(userService.regist(any(User.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/mearound/users/")
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .characterEncoding("utf-8")
                                              .content(mapper.writeValueAsString(user)))
               .andExpect(status().isBadRequest())
               .andDo(print());
        
    }

    @Test
    public void returnUserWhenGetRegistedUser() throws Exception {

        when(userService.get(any(String.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/mearound/users/" + user.getUserId())
                                              .accept(MediaType.APPLICATION_JSON_VALUE)
                                              .characterEncoding("utf-8"))
               .andExpect(status().isOk())
               .andDo(print());
        
    }

    @Test
    public void ThrowDataNotFoundExceptionWhenGetUnregistedUser() throws Exception {

        when(userService.get(any(String.class))).thenThrow(DataNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/mearound/users/" + user.getUserId())
                                              .accept(MediaType.APPLICATION_JSON_VALUE)
                                              .characterEncoding("utf-8"))
               .andExpect(status().isNoContent())
               .andDo(print());
        
    }
}
