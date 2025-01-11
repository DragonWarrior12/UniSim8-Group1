package com.badlogic.UniSim2.achievements;

// new class

import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.GUImanager.GameMenu;
import com.badlogic.UniSim2.GUImanager.GameScreen;
import com.badlogic.UniSim2.resources.Assets;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.satisfaction.Thought;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

public class AchievementManager {
    private GameMenu menu;
    public List<Achievement> incompleteAchievements;
    public List<Achievement> completeAchievements;

    public AchievementManager(GameMenu menu) {
        this.menu = menu;

        incompleteAchievements = new ArrayList<>();
        completeAchievements = new ArrayList<>();

        createAchievements();
    }

    public void checkAchievements() {
        for (int x = 0; x < incompleteAchievements.size(); x++) {
            Achievement ach = incompleteAchievements.get(x);
            if (ach.checkCompletion()) {
                incompleteAchievements.remove(x);
                completeAchievements.add(ach);
                menu.displayAchievement(ach);
                ach.onCompletion.fire();
                x--;
            }
        }
    }

    public void createAchievements() {
        incompleteAchievements.add(new Achievement(
            "Bookworn",
            "Have four or more libraries, x1.2 final score",
            () -> BuildingMenu.buildingCounts[2] > 3,
            1.2f));

        Thought greenThumbThought = new Thought("Green Thumb", "Students love the green space around campus", 10);
        incompleteAchievements.add(new Achievement(
            "Green Thumb",
            "Have five or more nature spaces,\n+10 satisfaction target",
            () -> BuildingMenu.buildingCounts[6] >= 5,
            1f,
            () -> GameScreen.gameScreen.menu.getSatisfaction().setThought("Green Thumb", greenThumbThought)));

        incompleteAchievements.add(new Achievement(
            "Catastrophe",
            "Reach 0 satisfaction, x0.4 score",
            () -> GameScreen.gameScreen.menu.getSatisfaction().getScore() <= 0.001, // not exact check due to floating point accuracy
            0.4f,
            Assets.badAchievementTexture));
    }
}
