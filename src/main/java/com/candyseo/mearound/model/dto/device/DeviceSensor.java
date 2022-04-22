package com.candyseo.mearound.model.dto.device;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSensor {
    
    private Device device;

    private List<Sensor> sensors;

}
