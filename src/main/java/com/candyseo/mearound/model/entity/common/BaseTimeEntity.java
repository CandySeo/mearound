package com.candyseo.mearound.model.entity.common;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
public class BaseTimeEntity {
    
    private LocalDateTime regiatedDateTime;

    private LocalDateTime LastModifiedDateTime;
}
