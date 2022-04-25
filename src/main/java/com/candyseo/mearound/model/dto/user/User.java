package com.candyseo.mearound.model.dto.user;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@JsonInclude(value = Include.NON_NULL)
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private UUID identifier;

    @NonNull
    private String userId;

    private String password;

    @NonNull
    private String nickname;

    public User(String userId, String password, String nickname) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }
}
