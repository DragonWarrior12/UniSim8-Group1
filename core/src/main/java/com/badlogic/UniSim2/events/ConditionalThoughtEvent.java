package com.badlogic.UniSim2.events;

// new class

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.satisfaction.Satisfaction;
import com.badlogic.UniSim2.satisfaction.Thought;

public class ConditionalThoughtEvent extends Event {
    private final Thought unsatisfiedThought;
    private final Thought satisfiedThought;
    private final Condition condition;

    @FunctionalInterface
    public interface Condition {
        boolean check();
    }

    public ConditionalThoughtEvent(String name, float triggerTime, Thought unsatisfiedThought, Thought satisfiedThought, Condition condition, float endTime) {
        super(name, triggerTime, endTime);
        this.satisfiedThought = satisfiedThought;
        this.unsatisfiedThought = unsatisfiedThought;
        this.condition = condition;
    }

    public ConditionalThoughtEvent(String name, float triggerTime, Thought unsatisfiedThought, Thought satisfiedThought, Condition condition) {
        this(name, triggerTime, unsatisfiedThought, satisfiedThought, condition, Consts.MAX_TIME + 1);
    }

    public void update(float time) {
        if (time >= triggerTime) {
            if (time >= endTime) {
                Satisfaction.satisfaction.removeThought(name);
                isFinished = true;
                return;
            }

            if (condition.check()) {
                Satisfaction.satisfaction.setThought(name, satisfiedThought);
            } else {
                Satisfaction.satisfaction.setThought(name, unsatisfiedThought);
            }
        }
    }
}
