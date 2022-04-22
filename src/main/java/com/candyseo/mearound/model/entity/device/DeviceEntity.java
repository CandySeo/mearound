package com.candyseo.mearound.model.entity.device;

import com.candyseo.mearound.model.entity.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEntity extends BaseTimeEntity {

    private String uuid;
    
    private String id;

    private String name;

}
