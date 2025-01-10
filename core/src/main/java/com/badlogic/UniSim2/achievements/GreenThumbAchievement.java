package com.badlogic.UniSim2.achievements;

// new class

import com.badlogic.UniSim2.GUImanager.BuildingMenu;

public class GreenThumbAchievement extends Achievement{
    @Override
    public boolean checkCompletion() {
        return BuildingMenu.buildingCounts[6] > 2;
    }

    @Override
    public String getName() {
        return "Green Thumb";
    }

    @Override
    public float getScoreMultiplier() {
        return 1.1f;
    }
}
