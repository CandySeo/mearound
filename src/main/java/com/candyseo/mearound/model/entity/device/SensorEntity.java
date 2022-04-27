package com.candyseo.mearound.model.entity.device;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensors")
public class SensorEntity implements Persistable<UUID> {
    
    @Id
    @Column(name = "identifier", length = 36)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID", 
        strategy = "org.hibernate.id.UUIDGenerator")
    private UUID identifier;

    /***
     * SensorType.name()
     */
    @Column(name = "type", nullable = false, length = 24 )
    private String type;

    @Transient
    private boolean isNew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_identifier")
    private DeviceEntity device;

    @Override
    public UUID getId() {
        return this.identifier;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }
    
}
