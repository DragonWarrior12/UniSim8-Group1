package com.badlogic.UniSim2.achievements;

// new class

import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.gdx.graphics.Texture;

public class Achievement {
    private final String name;
    private final String description;
    private final UnlockCondition unlockCondition;
    private final float scoreMultiplier;
    private final Texture texture;
    public final OnCompletion onCompletion;

    @FunctionalInterface
    public interface UnlockCondition {
        boolean check();
    }

    @FunctionalInterface
    public interface OnCompletion {
        void fire();
    }

    public Achievement(String name, String description, UnlockCondition unlockCondition, float scoreMultiplier, OnCompletion onCompletion, Texture texture) {
        this.name = name;
        this.description = description;
        this.unlockCondition = unlockCondition;
        this.scoreMultiplier = scoreMultiplier;
        this.onCompletion = onCompletion;
        this.texture = texture;
    }

    public Achievement(String name, String description, UnlockCondition unlockCondition, float scoreMultiplier, Texture texture) {
        this(name, description, unlockCondition, scoreMultiplier, () -> {}, texture);
    }

    public Achievement(String name, String description, UnlockCondition unlockCondition, float scoreMultiplier, OnCompletion onCompletion) {
        this(name, description, unlockCondition, scoreMultiplier, onCompletion, Assets.achievementTexture);
    }

    public Achievement(String name, String description, UnlockCondition unlockCondition, float scoreMultiplier) {
        this(name, description, unlockCondition, scoreMultiplier, () -> {}, Assets.achievementTexture);
    }

    public boolean checkCompletion() {
        return unlockCondition.check();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    public Texture getTexture() {
        return texture;
    }
}


