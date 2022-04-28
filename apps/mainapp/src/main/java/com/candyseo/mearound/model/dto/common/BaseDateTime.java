package com.candyseo.mearound.model.dto.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseDateTime {
    
    private LocalDateTime regiatedDateTime;

    private LocalDateTime LastModifiedDateTime;
    
}
