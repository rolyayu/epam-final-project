package com.epam.entity;

public class Worker extends Entity {
    private boolean isBusy;

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

}
