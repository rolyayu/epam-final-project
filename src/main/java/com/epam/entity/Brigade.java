package com.epam.entity;

import java.util.Arrays;
import java.util.List;

public class Brigade extends Entity{
    private List<Worker> workers;

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Long[] getWorkersId() {
        return this.getWorkers().stream()
                .map(Entity::getId)
                .toArray(Long[]::new);
    }
}
