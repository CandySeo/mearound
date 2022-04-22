package com.candyseo.mearound.model.converter;

import com.candyseo.mearound.model.dto.user.User;
import com.candyseo.mearound.model.entity.user.UserEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UserMapper extends GenericMapper<User, UserEntity> {
    
}
