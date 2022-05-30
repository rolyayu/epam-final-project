package com.epam.entity;

public class Lodger extends Entity{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Request sendRequest(WorkScale scale,int timeToDo,WorkType type) {
        Request request = new Request();
        request.setWorkScale(scale);
        request.setTimeToDo(timeToDo);
        request.setWorkType(type);
        request.setLodger(this);
        return request;
    }

    @Override
    public String toString() {
        return "Lodger{" +
                "name='" + name + '\'' +
                '}';
    }
}
