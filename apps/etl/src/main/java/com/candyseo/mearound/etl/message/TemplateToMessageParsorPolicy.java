package com.candyseo.mearound.etl.message;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class TemplateToMessageParsorPolicy extends StringToMessageParsorPolicy {

    @Override
    public Message parseString(String line) {
                
        String[] separated = line.split(SEPARATOR);

        if (separated.length < 5) {
            onError(String.format("String(%s) is invalid.", line));
        }

        for (String s: separated) {
            if (s == null) {
                onError(String.format("String(%s) is invalid.", line));
            }
        }

        return makeMessage(separated[0], separated[1], separated[2], separated[3], separated[4]);
    }

    private Message makeMessage(String deviceId, String sensorType, String decimalPoint, String minimum, String maximum) {

        ThreadLocalRandom random = ThreadLocalRandom.current();

        double min = Double.valueOf(minimum);
        double max = Double.valueOf(maximum);
        double dp = Double.valueOf(decimalPoint);
        double sensorValue = random.nextDouble(max - min) + min;
        sensorValue = Math.ceil(sensorValue * 100) / dp;
        return new Message(deviceId, sensorType, String.valueOf(sensorValue), LocalDateTime.now().toString());
    }
    
}
