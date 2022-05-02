package com.candyseo.mearound.amqp;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message implements Serializable {
    
    private String deviceId;

    private String type;

    private String value;

    private String registedDateTime;

    public Message(String value) {
        this.value = value;
    }
}
