package com.badlogic.UniSim2.achievements;

import com.badlogic.gdx.graphics.Texture;


public class Achievement {
    private final Texture achievementTexture;
    private String name;
    public float scoreIncrease;
    public boolean unlocked;

    public Achievement(Texture achievementText, String achievementName, float score) {
        achievementTexture = achievementText;
        name = achievementName;
        scoreIncrease = score;
        unlocked = false;
    }

    public String getAchievement() {
        return name;
    }
    public Texture getAchievementTexture() {
        return achievementTexture;
    }
    public float getScoreIncrease() {
        return scoreIncrease;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlockAchievement() {
        unlocked = true;
    }
    }


