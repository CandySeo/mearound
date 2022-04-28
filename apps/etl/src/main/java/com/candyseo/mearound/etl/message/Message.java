package com.candyseo.mearound.etl.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Message implements Serializable {
    
    private String deviceId;

    private String type;

    private String value;

    public Message(String value) {
        this.value = value;
    }

}
