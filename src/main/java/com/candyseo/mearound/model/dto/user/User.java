package com.candyseo.mearound.model.dto.user;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(value = Include.NON_NULL)
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
