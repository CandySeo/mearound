package com.candyseo.mearound.repository.user;

import java.util.Optional;
import java.util.UUID;

import com.candyseo.mearound.model.entity.user.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUserId(String userId);
    
}
