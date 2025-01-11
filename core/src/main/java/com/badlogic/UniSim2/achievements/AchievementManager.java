package com.badlogic.UniSim2.achievements;

// new class

import com.badlogic.UniSim2.GUImanager.GameMenu;
import com.badlogic.UniSim2.resources.Assets;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class AchievementManager {
    private Image achievementImage;
    private Label achievementLabel;
    private float achievementDisplayTimer;
    public List<Achievement> incompleteAchievements;
    public List<Achievement> completeAchievements;

    public AchievementManager(GameMenu gameMenu) {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        achievementImage = new Image(Assets.achievementTexture);
        achievementImage.setPosition(Consts.ACHIEVEMENT_X, Consts.ACHIEVEMENT_Y);
        achievementImage.setSize(Consts.ACHIEVEMENT_WIDTH, Consts.ACHIEVEMENT_HEIGHT);
        achievementImage.setVisible(false);

        achievementLabel = new Label("", skin);
        achievementLabel.setPosition(Consts.ACHIEVEMENT_TEXT_X, Consts.ACHIEVEMENT_TEXT_Y);
        achievementLabel.setFontScale(3);
        achievementLabel.setColor(Color.BLACK);
        achievementImage.setVisible(false);

        gameMenu.getStage().addActor(achievementImage);
        gameMenu.getStage().addActor(achievementLabel);

        incompleteAchievements = new ArrayList<>();
        completeAchievements = new ArrayList<>();
        incompleteAchievements.add(new GreenThumbAchievement());
        incompleteAchievements.add(new BookwormAchievement());
    }

    public void checkAchievements(float deltaTime) {
        for (int x = 0; x < incompleteAchievements.size(); x++) {
            Achievement ach = incompleteAchievements.get(x);
            if (ach.checkCompletion()) {
                incompleteAchievements.remove(x);
                completeAchievements.add(ach);
                displayAchievement(ach);
                x--;
            }
        }

        if (achievementDisplayTimer >= 0){
            achievementDisplayTimer -= deltaTime;
            if (achievementDisplayTimer < 0) {
                achievementImage.setVisible(false);
                achievementLabel.setVisible(false);
            }
        }
    }

    public void displayAchievement(Achievement achievement) {
        achievementLabel.setText(achievement.getName() + String.format("\nx%.1f score multiplier", achievement.getScoreMultiplier()));
        achievementImage.setVisible(true);
        achievementLabel.setVisible(true);
        achievementDisplayTimer = 3;
    }
}
