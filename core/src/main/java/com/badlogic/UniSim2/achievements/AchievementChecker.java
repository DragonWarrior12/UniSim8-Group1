package com.badlogic.UniSim2.achievements;
import com.badlogic.UniSim2.achievements.Achievement;
import com.badlogic.UniSim2.GUImanager.GameMenu;
import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.resources.Assets;

import com.badlogic.UniSim2.GUImanager.AchievementPopup;

public class AchievementChecker {
    private GameMenu gameMenu;
    public boolean greenthumbBool;
    public boolean bookwormBool;
    public boolean utopiaBool;

    public AchievementChecker(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
        greenthumbBool = false;
        utopiaBool = false;
        bookwormBool = false;
    }

    public void checkAchievements() {
        BuildingMenu buildingMenu = gameMenu.getBuildingMenu();
        if (buildingMenu.natureCount > 2 && greenthumbBool == false) {
            Achievement greenThumb = new Achievement(Assets.achievementTexture,"Green Thumb", 5);
            AchievementPopup greenThumbPopup = new AchievementPopup(gameMenu);
            greenThumbPopup.displayAchievement(greenThumb);
            greenthumbBool = true;
            
    }
        if (gameMenu.getSatisfaction().getScore() == 100 && utopiaBool == false) {
            Achievement utopia = new Achievement(Assets.achievementTexture,"Utopia", 5);
            AchievementPopup utopiaPopup = new AchievementPopup(gameMenu);
            utopiaPopup.displayAchievement(utopia);
            utopiaBool = true;
    }
        if(buildingMenu.libraryCount > 10 && bookwormBool == false) {
            Achievement bookworm = new Achievement(Assets.achievementTexture,"Bookworm", 5);
            AchievementPopup bookwormPopup = new AchievementPopup(gameMenu);
            bookwormPopup.displayAchievement(bookworm);
            bookwormBool = true;
    }
}
}