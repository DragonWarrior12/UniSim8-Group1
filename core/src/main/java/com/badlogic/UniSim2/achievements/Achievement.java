package com.badlogic.UniSim2.achievements;

// new class

public abstract class Achievement {
    public abstract boolean checkCompletion();

    public abstract String getName();

    public abstract float getScoreMultiplier();
}


