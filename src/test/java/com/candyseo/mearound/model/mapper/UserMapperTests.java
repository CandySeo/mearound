package com.candyseo.mearound.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.entity.user.UserEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserMapperTests {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        this.userMapper = new UserMapper();
    }
    
    @Test
    public void returnUserEntityWhenToEntityGivenUserDtoTest() {

        User user = new User(null, "USERID01", "PASSWORD01", "NICKNAME01");
        UserEntity userEntity = userMapper.toEntity(user);

        assertNotNull(userEntity);
        assertEquals(userEntity.getIdentifier(), user.getIdentifier());
        assertEquals(userEntity.getNickname(), user.getNickname());
    }

    @Test
    public void throwIllegalArgumentExceptionWhenToEntityGivenNullValue() {
        
        User user1 = new User(null, null, "PASSWORD01", "NICKNAME01");
        User user2 = new User(null, "USERID02", null, "NICKNAME02");
        User user3 = new User(null, "USERID03", "PASSWORD03", null);

        
        assertThrows(IllegalArgumentException.class, () -> {
            userMapper.toEntity(user1);
        });     

        assertThrows(IllegalArgumentException.class, () -> {
            userMapper.toEntity(user2);
        });        

        assertThrows(IllegalArgumentException.class, () -> {
            userMapper.toEntity(user3);
        });
    }

    @Test
    public void retuenUserDtoWhenToDtoGivenUserEntityTest() {

        UserEntity userEntity = new UserEntity(UUID.randomUUID(), "USERID01", "PASSWORD01", "NICKNAME01");
        User user = userMapper.toDto(userEntity);

        assertNotNull(user);
        assertEquals(user.getIdentifier(), userEntity.getIdentifier());
        assertEquals(user.getNickname(), userEntity.getNickname());
    }

    @Test
    public void throwIllegalArgumentExceptionWhenToDtoGivenNullIdentifier() {

        UserEntity userEntity = new UserEntity(null, "USERID01", "PASSWORD01", "NICKNAME01");

        assertThrows(IllegalArgumentException.class, () -> {
            userMapper.toDto(userEntity);
        });        
    }

}
