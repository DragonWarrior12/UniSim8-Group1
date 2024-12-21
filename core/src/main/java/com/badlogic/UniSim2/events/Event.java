package com.badlogic.UniSim2.events;

import com.badlogic.UniSim2.satisfaction.Thought;

public class Event {
    private final String name;
    private final float triggerTime;
    private final Thought thought;
    private boolean hasTriggered;

    public Event(String name, float triggerTime, Thought thought) {
        this.name = name;
        this.triggerTime = triggerTime;
        this.thought = thought;
        this.hasTriggered = false;
    }

    public boolean shouldTrigger(float currentTime) {
        return !hasTriggered && currentTime >= triggerTime;
    }

    public void trigger() {
        hasTriggered = true;
    }

    public String getName() {
        return name;
    }

    public Thought getThought() {
        return thought;
    }
} 