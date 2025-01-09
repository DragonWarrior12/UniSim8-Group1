package com.badlogic.UniSim2.events;

// new class

import com.badlogic.UniSim2.GUImanager.GameScreen;
import com.badlogic.UniSim2.satisfaction.Thought;

public class SimpleThoughtEvent extends Event{
    private final Thought thought;

    public SimpleThoughtEvent(String name, float triggerTime, Thought thought) {
        super(name, triggerTime);
        this.thought = thought;
    }

    public void update(float time) {
        if (time >= triggerTime) {
            GameScreen.gameScreen.menu.satisfaction.setThought(name, thought);
            GameScreen.gameScreen.menu.showThoughtMessage(thought.getDescription());
            isFinished = true;
        }
    }
}
