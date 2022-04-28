package com.candyseo.mearound.model.dto.report;

public enum ReportLevel {
    
    VERY_GOOD(5, "It is in very good condition. Please continue to manage it in the future."),
    GOOD(4, "It is in good condition. Please manage it so that it becomes a more pleasant environment."),
    NOT_BAD(3, "It is in plain condition. Please take care to make it better."),
    BAD(2, "The environment is not good. Continued exposure can adversely affect health."),
    VERY_BAD(1, "It is in very bad condition. It adversely affects health.");

    int value;
    String message;

    ReportLevel(int value, String message) {
        this.value = value;
        this.message = message;
    }

    int getValue() {
        return this.value;
    }

    String getMessage() {
        return this.message;
    }

}
