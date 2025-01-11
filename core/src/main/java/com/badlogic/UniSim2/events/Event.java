package com.badlogic.UniSim2.events;

// new class

public abstract class Event {
    protected final String name;
    protected final float triggerTime;
    protected final float endTime;
    protected boolean isFinished;

    public Event(String name, float triggerTime, float endTime) {
        this.name = name;
        this.triggerTime = triggerTime;
        this.endTime = endTime;
        this.isFinished = false;
    }

    public abstract void update(float time);

    public String getName() {
        return name;
    }

    public boolean getIsFinished() {
        return isFinished;
    }
}
