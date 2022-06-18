package com.epam.entity;

public class Request extends Entity{
    private WorkScale workScale;
    private int timeToDo;
    private WorkType workType;

    private Lodger lodger;

    private boolean inProcess;

    public boolean isInProcess() {
        return inProcess;
    }

    public void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }

    public WorkScale getWorkScale() {
        return workScale;
    }

    public void setWorkScale(WorkScale workScale) {
        this.workScale = workScale;
    }

    public int getTimeToDo() {
        return timeToDo;
    }

    public void setTimeToDo(int timeToDo) {
        this.timeToDo = timeToDo;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public Lodger getLodger() {
        return lodger;
    }

    public void setLodger(Lodger lodger) {
        this.lodger = lodger;
    }

    @Override
    public String toString() {
        return "Request{" +
                "workScale=" + workScale +
                ", timeToDo=" + timeToDo +
                ", workType=" + workType +
                ", lodger=" + lodger +
                '}';
    }
}
