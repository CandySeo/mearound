package com.candyseo.mearound.etl.message;

import org.springframework.lang.NonNull;

public class StringToMessageParsorPolicy implements DataParsorPolicy<Message, String> {

    private static final String SEPARATOR = ":";

    @Override
    public Message parseString(@NonNull String line) {
        
        String[] separated = line.split(SEPARATOR);

        if (separated.length < 3) {
            onError(String.format("String(%s) is invalid.", line));
        }

        for (String s: separated) {
            if (s == null) {
                onError(String.format("String(%s) is invalid.", line));
            }
        }

        return new Message(separated[0], separated[1], separated[2]);
    }

    public void onError(String message) {
        throw new RuntimeException(message);
    }
    
}
