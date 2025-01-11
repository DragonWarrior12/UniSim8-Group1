package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.achievements.AchievementManager;
import com.badlogic.UniSim2.mapmanager.Map;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This screen is used when the game is being played.
 */
public class GameScreen implements Screen {
    private Main game;
    private StretchViewport viewport;

    public Timer timer; // changed to public

    public GameMenu menu; // Used to make and display the game menu  // changed to public

    boolean isPaused = true; // changed to true

    // This variable is needed to stop a crash from occuring when the game ends.
    boolean hasEnded = false;

    private Map map;

    // new
    private AchievementManager achievementManager;

    public static GameScreen gameScreen; // new

    public GameScreen(Main game) {
        gameScreen = this; // new
        this.game = game;
        viewport = game.getViewport();
        timer = new Timer();
        map = new Map(game);
        menu = new GameMenu(game, timer, map.getBuildingManager());
        SoundManager.playMusic();

        // new, for events that start immediately. It can't be in GameMenu.initialiseEvents as it requires menu to be set
        menu.eventManager.updateEvents();
        menu.updateThoughts();

        achievementManager = new AchievementManager(menu); // new
    }

    @Override
    public void show() {
        menu.activate();
    }

    @Override
    public void render(float delta) {
        input();
        update(delta); // changed
        if (hasEnded == true) return;
        draw();
    }

    /**
     * Processes input. Will pause/resume the game if the space is pressed.
     */
    private void input() {
        menu.input();
        map.input();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (isPaused) {
                isPaused = false;
                menu.resume();
            } else {
                isPaused = true;
                menu.pause();
            }
        }

    }

    /**
     * Will update the timer or not (depending on whether the game is paused)
     * and will end the game if the timer has reached its max time.
     */
    private void update(float deltaTime) { // added delta time
        if (isPaused == false) {
            timer.update();
            if (timer.hasReachedMaxTime()) {
                game.endGame();
                hasEnded = true;
            }
        }
        achievementManager.checkAchievements(deltaTime); // new
    }

    /**
     * Draws the game. This means drawing the game menu, building menu and game
     * map.
     */
    private void draw() {
        viewport.apply();
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        map.draw();
        menu.draw();
    }

    // new
    public AchievementManager getAchievementManager() {
        return achievementManager;
    }

    @Override
    public void resize(int width, int height) {
        map.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        menu.dispose();
        gameScreen = null; // new
    }
}
