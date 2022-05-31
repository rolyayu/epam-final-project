package com.epam.entity;

public enum WorkScale {
    SMALL("SMALL"),
    MEDIUM("MEDIUM"),
    LARGE("LARGE");
    private final String scale;

    public String getScale() {
        return scale;
    }

    WorkScale(String scale) {
        this.scale = scale;
    }
}
