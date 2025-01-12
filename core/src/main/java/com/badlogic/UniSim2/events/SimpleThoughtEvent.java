package com.badlogic.UniSim2.events;

// new class

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.satisfaction.Satisfaction;
import com.badlogic.UniSim2.satisfaction.Thought;

public class SimpleThoughtEvent extends Event{
    private final Thought thought;

    public SimpleThoughtEvent(String name, float triggerTime, Thought thought) {
        this(name, triggerTime, thought, Consts.MAX_TIME + 1);
    }

    public SimpleThoughtEvent(String name, float triggerTime, Thought thought, float endTime) {
        super(name, triggerTime, endTime);
        this.thought = thought;
    }

    public void update(float time) {
        if (time >= triggerTime) {
            if (time >= endTime) {
                Satisfaction.satisfaction.removeThought(name);
                isFinished = true;
                return;
            }

            Satisfaction.satisfaction.setThought(name, thought);
        }
    }
}
