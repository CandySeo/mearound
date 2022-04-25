package com.candyseo.mearound.model.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.candyseo.mearound.model.entity.common.BaseDateTimeEntity;
import com.candyseo.mearound.model.entity.key.UserDeviceEntityKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_devices")
@IdClass(value = UserDeviceEntityKey.class)
public class UserDeviceEntity extends BaseDateTimeEntity implements Serializable {
    
    @Id
    @Column(name = "user_id", nullable = false, updatable = false, length = 36)
    private String userId;

    @Id
    @Column(name = "device_id", nullable = false, updatable = false, length = 36)
    private String deviceId;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
