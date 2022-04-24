package com.candyseo.mearound.service.user;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.entity.user.UserEntity;
import com.candyseo.mearound.model.mapper.UserMapper;
import com.candyseo.mearound.repository.user.UserRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper,
                            UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public String regist(User user) {
        
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new IllegalArgumentException("Alredy registed userId `" + user.getUserId() + "`");
        }

        UserEntity convert = userMapper.toEntity(user);
        log.info("DTO convert to entity: {}", convert);
        UserEntity newEntity = userRepository.save(userMapper.toEntity(user));

        return newEntity.getId().toString();
    }

    @Override
    public User get(String userId) {
        
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("Unregisted user."));

        User user = userMapper.toDto(userEntity);

        return new User(null, user.getUserId(), null, user.getNickname());
    }
    
}
