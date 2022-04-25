package com.candyseo.mearound.util;

import java.util.UUID;

public class UuidUtils {
    
    public static boolean isUuidFormat(String uuidString) {

        if (uuidString == null || uuidString.length() == 0) {
            return false;
        }
        
        try {
            UUID.fromString(uuidString);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
