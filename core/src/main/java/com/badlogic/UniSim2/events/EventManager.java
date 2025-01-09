package com.badlogic.UniSim2.events;

import com.badlogic.UniSim2.GUImanager.GameScreen;
import com.badlogic.UniSim2.satisfaction.Thought;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Event> events;

    public EventManager() {
        events = new ArrayList<>();
        initializeEvents();
    }

    private void initializeEvents() {
        // Create thoughts for each event
        Thought freshersThought = new Thought(
            "Freshers Week",
            "Students are enjoying orientation activities!",
            20
        );

        Thought christmasThought = new Thought(
            "Christmas Break",
            "Students celebrating the holiday season!",
            15
        );

        Thought examThought = new Thought(
            "Exam Week",
            "Students are stressed about final exams!",
            -25
        );

        // Add events to list
        events.add(new SimpleThoughtEvent("Freshers Week", 20.0f, freshersThought));
        events.add(new SimpleThoughtEvent("Christmas Break", 60.0f, christmasThought));
        events.add(new SimpleThoughtEvent("Exam Week", 90.0f, examThought));
    }

    public void updateEvents() {
        for (Event event : getEvents()) {
            if (!event.getIsFinished())
                event.update(GameScreen.gameScreen.timer.getElapsedTime());
        }
    }

    public List<Event> getEvents() {
        return events;
    }
}
