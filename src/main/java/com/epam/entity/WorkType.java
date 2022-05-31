package com.epam.entity;

public enum WorkType {
    TRAVELING("TRAVELING"),
    REMOTE("REMOTE"),
    HOME("HOME"),
    PART_TIME("PART_TIME"),
    REGULAR("REGULAR");
    private final String type;

    WorkType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
