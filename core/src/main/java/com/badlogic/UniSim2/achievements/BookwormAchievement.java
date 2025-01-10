package com.badlogic.UniSim2.achievements;

// new class

import com.badlogic.UniSim2.GUImanager.BuildingMenu;

public class BookwormAchievement extends Achievement {
    @Override
    public boolean checkCompletion() {
        return BuildingMenu.buildingCounts[2] > 3;
    }

    @Override
    public String getName() {
        return "Bookworm";
    }

    @Override
    public float getScoreMultiplier() {
        return 1.2f;
    }
}
