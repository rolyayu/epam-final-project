package com.epam.entity;

public enum WorkScale {
    SMALL("SMALL") {
        @Override
        public int getNeededWorkers() {
            return 2;
        }
    },
    MEDIUM("MEDIUM") {
        @Override
        public int getNeededWorkers() {
            return 3;
        }
    },
    LARGE("LARGE") {
        @Override
        public int getNeededWorkers() {
            return 4;
        }
    };
    private final String scale;

    public String getScale() {
        return scale;
    }

    public abstract int getNeededWorkers();

    WorkScale(String scale) {
        this.scale = scale;
    }
}
