package com.candyseo.mearound.model.mapper;

import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.entity.user.UserEntity;

import org.springframework.stereotype.Component;

@Component
public class UserMapper implements GenericMapper<User, UserEntity> {

    @Override
    public User toDto(UserEntity entity) throws IllegalArgumentException {
        if (entity.getIdentifier() == null) {
            throw new IllegalArgumentException("Entity `identifier` should be not null.");
        }

        return User.builder()
                   .identifier(entity.getIdentifier())
                   .userId(entity.getUserId())
                   .nickname(entity.getNickname())
                   .build();
    }

    @Override
    public UserEntity toEntity(User dto) {
        if (dto.getUserId() == null || dto.getUserId().isBlank()) {
            throw new IllegalArgumentException("`userId` should be not null.");
        }
        
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("`password` should be not null.");
        }
        
        if (dto.getNickname() == null || dto.getNickname().isBlank()) {
            throw new IllegalArgumentException("`nickname` should be not null.");
        }

        return UserEntity.builder()
                         .identifier(dto.getIdentifier())
                         .userId(dto.getUserId())
                         .password(dto.getPassword())
                         .nickname(dto.getNickname())
                         .build();
    }
    
}
