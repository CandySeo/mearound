package com.candyseo.mearound.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.entity.user.UserEntity;
import com.candyseo.mearound.model.mapper.UserMapper;
import com.candyseo.mearound.repository.user.UserRepository;
import com.candyseo.mearound.service.user.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Spy
    private UserMapper userMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        this.userMapper = new UserMapper();
        this.user = new User("USERID01", "PASSWORD01", "NICKNAME01");
    }

    @Test
    public void returnIdentifierWhenRegistUser() {

        UserEntity userEntity = UserEntity.builder()
                                          .identifier(UUID.randomUUID())
                                          .userId(user.getUserId())
                                          .password(user.getPassword())
                                          .nickname(user.getNickname())
                                          .build();        
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        String identifier = userService.regist(user); 

        assertNotNull(identifier);

    }
    
    @Test
    public void throwIllegalArgumentExceptionWhenAlreayRegistedUser() {

        UserEntity userEntity = UserEntity.builder()
                                          .identifier(UUID.randomUUID())
                                          .userId(user.getUserId())
                                          .password(user.getPassword())
                                          .nickname(user.getNickname())
                                          .build();        
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(userEntity));

        assertThrows(IllegalArgumentException.class, () -> {
            userService.regist(user);     
        });

    }
    
    @Test
    public void returnUserWhenGetRegistedUser() {

        UUID identifier = UUID.randomUUID();
        UserEntity userEntity = UserEntity.builder()
                                          .identifier(identifier)
                                          .userId(user.getUserId())
                                          .password(user.getPassword())
                                          .nickname(user.getNickname())
                                          .build();        
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(userEntity));

        User registed = userService.get(identifier.toString());

        assertNotNull(registed);

    }
    
    @Test
    public void throwDataNotFoundExceptionWhenGetUnregistedUser() {
        
        UUID identifier = UUID.randomUUID();      
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        
        assertThrows(DataNotFoundException.class, () -> {
            userService.get(identifier.toString());
        });

    }
}
