package com.candyseo.mearound.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private String id;

    private String password;

    private String uuid;

    private String nickname;

}
