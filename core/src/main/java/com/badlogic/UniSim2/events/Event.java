package com.badlogic.UniSim2.events;

public abstract class Event {
    protected final String name;
    protected final float triggerTime;
    protected boolean isFinished;

    public Event(String name, float triggerTime) {
        this.name = name;
        this.triggerTime = triggerTime;
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
