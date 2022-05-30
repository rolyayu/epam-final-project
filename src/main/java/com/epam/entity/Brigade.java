package com.epam.entity;

public class Brigade extends Entity{
    private Worker[] workers;

    public Worker[] getWorkers() {
        return workers;
    }

    public void setWorkers(Worker[] workers) {
        this.workers = workers;
    }
}
