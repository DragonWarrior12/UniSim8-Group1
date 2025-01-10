package com.badlogic.UniSim2.satisfaction;

// new class

public class Thought {
    private final String title;
    private final String description;
    private final int modification;

    public Thought(String _title, String _description, int _modification) {
        title = _title;
        description  = _description;
        modification = _modification;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getModification() {
        return modification;
    }
}
