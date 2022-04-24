package com.candyseo.mearound.model.entity.device;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.candyseo.mearound.model.entity.common.BaseTimeEntity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class DeviceEntity extends BaseTimeEntity implements Persistable<UUID> {

    @Id
    @Column(nullable = false, updatable = false, length = 36)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID", 
        strategy = "org.hibernate.id.UUIDGenerato"
    )
    private UUID identifier;
    
    @Column 
    private String deviceId;

    @Column
    private String name;

    @Transient
    private boolean isNew;

    @Override
    public UUID getId() {
        return this.identifier;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public DeviceEntity(UUID identifier, String id, String name) {
        this.identifier = identifier;
        this.deviceId = id;
        this.name = name;
    }
}
