package com.candyseo.mearound.model.dto.device;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Device {

    private String identifier;
    
    @NonNull
    private String id;

    @NonNull
    private String name;

}
