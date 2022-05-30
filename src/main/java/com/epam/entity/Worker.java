package com.epam.entity;

public class Worker extends Entity{
    private boolean isBusy;
    private WorkType type;

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public WorkType getType() {
        return type;
    }

    public void setType(WorkType type) {
        this.type = type;
    }
}
