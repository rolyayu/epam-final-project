package com.epam.entity;

public enum WorkType {
    TRAVELING("traveling"),
    REMOTE("remote"),
    HOME("home"),
    PART_TIME("part-time"),
    REGULAR("regular");
    private final String type;

    WorkType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
