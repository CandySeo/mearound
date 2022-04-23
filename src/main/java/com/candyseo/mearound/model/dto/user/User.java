package com.candyseo.mearound.model.dto.user;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private UUID identifier;

    private String userId;

    private String password;

    private String nickname;

}
