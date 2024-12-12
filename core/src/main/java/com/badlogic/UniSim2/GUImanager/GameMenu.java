package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.satisfaction.Satisfaction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

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

    public GameMenu(Main game, Timer timer, BuildingManager buildings){
        stage = new Stage(game.getViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        buildingMenu = new BuildingMenu(stage, buildings);
        this.timer = timer;
        createMenu();
        pause();
    }

    /**
     * Activates the input processor needed for the menu to use inputs.
     */
    public void activate() {
        Gdx.input.setInputProcessor(stage);
    }

    private void createMenu(){
        buildingMenu.createBuildingMenu();
        createSatisfactionBar(); // new
        createTimerLabel();
    }

    // Adds a label at the top of the screen displaying the time
    private void createTimerLabel(){

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
    private void updateTimerLabel(){
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
    public void input(){
        stage.act(Gdx.graphics.getDeltaTime());
    }

    /**
     * Updates and draws the menu.
     */
    public void draw(){
        if (isPaused == false) {
            updateTimerLabel();
            updateSatisfaction(); // new
        }
        buildingMenu.draw();
        stage.draw();
    }

    /**
     * @return true if the menu is paused and false if not.
     */
    public boolean getPaused(){
        return isPaused;
    }

    /**
     * Gets rid the all textures. This method should be called when the menu is
     * not going to be used anymore.
     */
    public void dispose(){
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

        updateSatisfaction();
    }

    private void updateSatisfaction() {
        satisfaction.updateScore();
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
}
