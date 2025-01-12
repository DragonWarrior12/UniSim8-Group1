package com.badlogic.UniSim2.GUImanager;

import java.util.List;
import java.util.Map;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import com.badlogic.UniSim2.leaderboardManager.LeaderboardManager;

public class LeaderboardScreen implements Screen {
	private StretchViewport viewport;
	private Stage stage;
	private Table table;

	/*
	 * Shows the leaderboard once the game has ended
	 */
	public LeaderboardScreen(Main game, String username, float score) {
        this.viewport = game.getViewport();
        this.stage = new Stage(this.viewport);

        LeaderboardManager.addScore(username, score);

        setupLeaderboard();
	}

	/*
	 * setting up the leaderboard note to dev the code is still bunched into one I still have to clean
	 * this code up breaking it into methods so that the code is more readable
	 */
	private void setupLeaderboard() {
	    table = new Table();
	    table.setFillParent(true);
	    stage.addActor(table);

	    // positioning the table
	    table.center();
        table.setY(Consts.LEADERBOARD_Y);

	    // setting up the font
	    LabelStyle labelStyle = new LabelStyle();
	    BitmapFont font = new BitmapFont();
	    font.getData().setScale(2);
	    labelStyle.font = font;
	    labelStyle.fontColor = Color.BLACK;

	    // Add column headers
	    table.add(new Label("Rank", labelStyle)).pad(10).center();
	    table.add(new Label("Player Name", labelStyle)).pad(10).center();
	    table.add(new Label("Score", labelStyle)).pad(10).center();
	    table.row();

        // keep top 5
	    int topEntries = Math.min(5, LeaderboardManager.getSize());
	    List<Map.Entry<String, Float>> leaderboard = LeaderboardManager.getSortedLeaderboard().subList(0, topEntries);

	    // Setting the leaderboard
	    int rank = 1;
	    for (Map.Entry<String, Float> entry : leaderboard) {
	        // Apply custom style to each label
	        table.add(new Label(String.valueOf(rank), labelStyle)).pad(1).center();
	        table.add(new Label(entry.getKey(), labelStyle)).pad(1).center();
	        table.add(new Label(String.format("%.2f", entry.getValue()), labelStyle)).pad(1).center();
	        table.row();
	        rank++;
	    }
	}

    private void drawBackground(){
        SpriteBatch spriteBatch = new SpriteBatch();
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(Assets.startBackgroundTexture, 0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        spriteBatch.end();
    }
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		viewport.apply();
		ScreenUtils.clear(Consts.BACKGROUND_COLOR);
		drawBackground();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
        viewport.update(width, height, true);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {
		stage.dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
