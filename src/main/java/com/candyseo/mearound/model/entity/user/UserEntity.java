package com.candyseo.mearound.model.entity.user;

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
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseTimeEntity implements Persistable<UUID> {
    
    @Id
    @Column(nullable = false, updatable = false, length = 36)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID identifier;

    @Column(name = "user_id", nullable = false, unique = true, length = 36)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 12)
    private String nickname;

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

    public UserEntity(UUID identifier, String userId, String password, String nickname) {
        this.identifier = identifier;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }

}
