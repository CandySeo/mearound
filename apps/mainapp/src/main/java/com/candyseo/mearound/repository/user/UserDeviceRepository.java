package com.candyseo.mearound.repository.user;

import java.util.List;
import java.util.Optional;

import com.candyseo.mearound.model.entity.key.UserDeviceEntityKey;
import com.candyseo.mearound.model.entity.user.UserDeviceEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDeviceEntity, UserDeviceEntityKey> {
    
    Optional<UserDeviceEntity> findByDeviceIdAndIsActive(String deviceIdentifier, boolean isActive);

    List<UserDeviceEntity> findByUserId(String userIdentifier);

}
