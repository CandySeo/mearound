package com.candyseo.mearound.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.entity.user.UserEntity;
import com.candyseo.mearound.model.mapper.UserMapper;
import com.candyseo.mearound.repository.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTests {
    
    @Autowired
    private UserRepository userRepository;

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        this.userMapper = new UserMapper();
    }

    @Test
    public void returnEmptyOptionalWhenGivenUserId() {

        User user = new User("USERID1", "PASSWORD1", "NICKNAME1");

        Optional<UserEntity> entity = userRepository.findByUserId(user.getUserId());

        assertTrue(entity.isEmpty());

    }
    
    @Test
    public void returnUserentityWhenGivenUserId() {

        User user = new User("USERID1", "PASSWORD1", "NICKNAME1");
        registedUser(user);

        Optional<UserEntity> entity = userRepository.findByUserId(user.getUserId());

        assertTrue(entity.isPresent());
        equalsEntityAndDto(entity.get(), user);
    }

    private void registedUser(User user) {

        UserEntity registed = userRepository.save(userMapper.toEntity(user));
        
        assertNotNull(registed.getIdentifier());
        equalsEntityAndDto(registed, user);
    }

    private void equalsEntityAndDto(UserEntity entity, User user) {
        assertEquals(entity.getUserId(), user.getUserId());
        assertEquals(entity.getPassword(), user.getPassword());
        assertEquals(entity.getNickname(), user.getNickname());

    }

}
