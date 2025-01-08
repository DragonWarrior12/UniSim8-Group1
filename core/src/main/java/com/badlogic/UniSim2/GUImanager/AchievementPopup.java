package com.badlogic.UniSim2.GUImanager;
import com.badlogic.UniSim2.achievements.Achievement;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.UniSim2.resources.Consts;
public class AchievementPopup {
    private final Skin skin;
    private Stage achievementStage;
    private GameMenu gameMenu;
    public AchievementPopup(GameMenu gameMenu) {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        achievementStage = new Stage();
        this.gameMenu = gameMenu;
    }
    public void displayAchievement(Achievement achievement) {
        
        Table achievementTable = new Table();
        achievementTable.setPosition(Consts.ACHIEVEMENT_X, Consts.ACHIEVEMENT_Y); // Set the position of the table
        achievementTable.setSize(Consts.ACHIEVEMENT_WIDTH, Consts.ACHIEVEMENT_HEIGHT); // Set the size of the table

        Image achievementImage = new Image(achievement.getAchievementTexture());
        achievementTable.add(achievementImage);

        Label achievementLabel = new Label(achievement.getAchievement(), skin);
        achievementTable.add(achievementLabel);

        achievementStage.addActor(achievementTable);

        gameMenu.getStage().addActor(achievementTable);

        achievementStage.act(Gdx.graphics.getDeltaTime());
        achievementStage.draw();
        
        achievementStage.act(3);
    }
}
