package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.achievements.Achievement;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.events.EventManager;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.satisfaction.Satisfaction;
import com.badlogic.UniSim2.satisfaction.Thought;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.Objects;

/**
 * This is the game menu that is shown by the {@link GameScreen}. It contains
 * the {@link Timer timer} for the game and {@link BuildingMenu the building menu}
 * which can be used to place new buildings.
 */
public class GameMenu {
    private Stage stage;
    private final Skin skin;
    private BuildingMenu buildingMenu;
    private Timer timer;
    private Label timerLabel;
    private boolean isPaused;

    // new
    private ProgressBar satisfactionBar;
    private ProgressBar satisfactionTarget;
    private Satisfaction satisfaction;
    EventManager eventManager; // package private for use in the GameScreen constructor
    private Table thoughtTable;
    private Image achievementImage;
    private Label achievementLabel;
    private float achievementDisplayTimer;

    public GameMenu(Main game, Timer timer, BuildingManager buildings) {
        stage = new Stage(game.getViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        buildingMenu = new BuildingMenu(stage, buildings);
        this.timer = timer;
        createMenu();
        pause(); // new
    }

    /**
     * Activates the input processor needed for the menu to use inputs.
     */
    public void activate() {
        Gdx.input.setInputProcessor(stage);
    }

    private void createMenu() {
        buildingMenu.createBuildingMenu();
        createSatisfactionBar(); // new
        initializeEvents(); // new
        createAchievementPopup(); // new
        createTimerLabel();
    }

    // Adds a label at the top of the screen displaying the time
    private void createTimerLabel() {

        // Initialize timerLabel
        timerLabel = new Label("00:00", skin);
        timerLabel.setFontScale(3);
        timerLabel.setAlignment(Align.center);
        timerLabel.setColor(Consts.TIMER_COLOR);

        // Position the label at the top center of the screen
        timerLabel.setPosition(Consts.TIMER_X, Consts.TIMER_Y, Align.center);

        // Add the label to the stage
        stage.addActor(timerLabel);
    }

    /**
     * Updates the time shown on the label to the elapsed time got from
     * the timer.
     */
    private void updateTimerLabel() {
        float elapsedTime = timer.getElapsedTime();
        int minutes = (int) (elapsedTime / 60);
        int seconds = (int) (elapsedTime % 60);
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }


    /**
     * Should be called when the game is paused.
     */
    public void pause() {
        timerLabel.setText("PAUSED");
        isPaused = true;
    }

    /**
     * Should be called when the game is resumed.
     */
    public void resume() {
        updateTimerLabel();
        isPaused = false;
    }

    /**
     * Processes any input.
     */
    public void input() {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    /**
     * Updates and draws the menu.
     */
    public void draw() {
        eventManager.updateEvents(GameScreen.gameScreen.getTimer().getElapsedTime()); // new
        if (isPaused == false) {
            updateTimerLabel();
            satisfaction.updateScore(); // new
        }
        updateSatisfactionBar(); // new
        updateThoughtsTable(); // new
        updateAchievementDisplay(Gdx.graphics.getDeltaTime()); // new
        buildingMenu.draw();
        stage.draw();
    }

    /**
     * @return true if the menu is paused and false if not.
     */
    public boolean getPaused() {
        return isPaused;
    }

    /**
     * Gets rid the all textures. This method should be called when the menu is
     * not going to be used anymore.
     */
    public void dispose() {
        buildingMenu.dispose();
        stage.dispose();
        skin.dispose();
    }

    // new
    private void createSatisfactionBar() {
        satisfaction = new Satisfaction();

        satisfactionBar = new ProgressBar(0, 100, 0.01f, false, skin);
        satisfactionBar.setSize(Consts.BUILDING_BUTTON_WIDTH, Consts.BUILDING_BUTTON_HEIGHT);
        satisfactionBar.setPosition(Consts.SATISFACTION_X, Consts.SATISFACTION_Y);
        stage.addActor(satisfactionBar);

        TextureRegionDrawable marker = new TextureRegionDrawable(Assets.targetMarker);

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle(null, marker);

        satisfactionTarget = new ProgressBar(0, 100, 0.01f, false, style);
        satisfactionTarget.setSize(Consts.BUILDING_BUTTON_WIDTH, Consts.BUILDING_BUTTON_HEIGHT);
        satisfactionTarget.setPosition(Consts.SATISFACTION_X, Consts.SATISFACTION_Y);
        stage.addActor(satisfactionTarget);

        updateSatisfactionBar();
    }

    // new
    private void updateSatisfactionBar() {
        satisfactionBar.setValue(satisfaction.getScore());
        satisfactionTarget.setValue(satisfaction.getTarget());

        if (satisfaction.getScore() > 70) {
            satisfactionBar.setColor(Color.GREEN);
        } else if (satisfaction.getScore() > 30) {
            satisfactionBar.setColor(Color.ORANGE);
        } else {
            satisfactionBar.setColor(Color.RED);
        }
    }

    // new, package private for use in the GameScreen constructor
    void updateThoughtsTable() {
        thoughtTable.clearChildren();

        for (Thought thought : satisfaction.listThoughts()) {
            if (Objects.equals(thought.getDescription(), "")) continue; // allows for hidden thoughts when description is empty

            Label label = new Label(thought.getDescription(), skin);

            if (thought.getModification() > 0)
                label.setColor(Color.OLIVE);
            else if (thought.getModification() < 0)
                label.setColor(Color.RED);
            else
                label.setColor(Color.GRAY);

            label.setFontScale(2);
            label.getStyle().font.getData().setLineHeight(14);
            label.setWrap(true);

            // if width isn't set here the text won't expand to the size of the table
            thoughtTable.add(label).pad(6).width(Consts.THOUGHT_TABLE_WIDTH);
            thoughtTable.row();
        }
    }

    // new
    public void createAchievementPopup() {
        achievementImage = new Image(Assets.achievementTexture);
        achievementImage.setPosition(Consts.ACHIEVEMENT_X, Consts.ACHIEVEMENT_Y);
        achievementImage.setSize(Consts.ACHIEVEMENT_WIDTH, Consts.ACHIEVEMENT_HEIGHT);
        achievementImage.setVisible(false);

        achievementLabel = new Label("", skin);
        achievementLabel.setPosition(Consts.ACHIEVEMENT_TEXT_X, Consts.ACHIEVEMENT_TEXT_Y);
        achievementLabel.setFontScale(2);
        achievementLabel.setColor(Color.BLACK);
        achievementImage.setVisible(false);

        getStage().addActor(achievementImage);
        getStage().addActor(achievementLabel);
    }

    // new
    public void displayAchievement(Achievement achievement) {
        achievementLabel.setText(achievement.getName() + "\n" + achievement.getDescription());
        achievementImage.setDrawable(new TextureRegionDrawable(new TextureRegion(achievement.getTexture())));
        achievementImage.setVisible(true);
        achievementLabel.setVisible(true);
        achievementDisplayTimer = 3;
    }

    // new
    public void updateAchievementDisplay(float deltaTime) {
        if (achievementDisplayTimer >= 0) {
            Gdx.app.log("AchievementTimer", String.valueOf(achievementDisplayTimer));
            Gdx.app.log("Delta", String.valueOf(achievementDisplayTimer));
            achievementDisplayTimer -= deltaTime;
            if (achievementDisplayTimer < 0) {
                achievementImage.setVisible(false);
                achievementLabel.setVisible(false);
            }
        }
    }

    // new
    public BuildingMenu getBuildingMenu() {
        return buildingMenu;
    }

    // new
    public Stage getStage() {
        return stage;
    }

    // new
    private void initializeEvents() {
        eventManager = new EventManager();

        Pixmap background = new Pixmap(1, 1, Pixmap.Format.RGB888);
        background.setColor(Color.WHITE);
        background.fill();

        thoughtTable = new Table();
        thoughtTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(background))));
        thoughtTable.setWidth(Consts.THOUGHT_TABLE_WIDTH);
        thoughtTable.setHeight(Consts.THOUGHT_TABLE_HEIGHT);
        thoughtTable.top().left();
        thoughtTable.setPosition(
            Consts.WORLD_WIDTH,
            0,
            Align.bottomRight
        );
        thoughtTable.pad(10);

        stage.addActor(thoughtTable);
    }
}
