package com.candyseo.mearound.repository.user;

import java.util.Optional;

import com.candyseo.mearound.model.entity.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUserId(String userId);
    
}
