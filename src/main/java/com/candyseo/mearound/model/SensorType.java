package com.candyseo.mearound.model;

public enum SensorType {
    
    TEMP(1, "온도", "℃", 10.0, 50.0);

    private int order;

    private String name;

    private String unit;

    private double minimum;

    private double maximum;

    SensorType(int order, String name, String unit, double minimum, double maximum) {
        this.order = order;
        this.name = name;
        this.unit = unit;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public int getOrder() {
        return this.order;
    }

    public String getName() {
        return this.name;
    }

    public String getUnit() {
        return this.unit;
    }

    public boolean isValid(double value) {
        return (this.minimum <= value) && (value <= this.maximum);
    }
}
