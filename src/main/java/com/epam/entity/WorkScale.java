package com.epam.entity;

public enum WorkScale {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large");
    private final String scale;

    public String getScale() {
        return scale;
    }

    WorkScale(String scale) {
        this.scale = scale;
    }
}
