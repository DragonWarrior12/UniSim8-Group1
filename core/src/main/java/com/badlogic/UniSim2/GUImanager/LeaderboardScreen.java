package com.badlogic.UniSim2.GUImanager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

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

import LeaderboardManager.LeaderboardManager;

public class LeaderboardScreen implements Screen {

	private Main game;
	private StretchViewport viewport;
	private Stage stage;
	private int score;
	private String username;
	private Table table;
	private Label titleLabel;
	
	private final Skin skin;

	/*
	 * Shows the leaderboard once the game has ended
	 */
	public LeaderboardScreen(Main game, String username, int score) throws Exception {
        this.game = game;
        this.viewport = game.getViewport();
        this.stage = new Stage(this.viewport);
        this.score = score;
        this.username = username;
        
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
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
	    table.setY(Gdx.graphics.getHeight() / 2 - table.getHeight() / 2 - 300); 

	    // setting up the font 
	    LabelStyle labelStyle = new LabelStyle();
	    BitmapFont font = new BitmapFont();
	    font.getData().setScale(2);
	    labelStyle.font = font;
	    labelStyle.fontColor = Color.BLACK; 

	    // Create title label 
	    titleLabel = new Label("Leaderboard", labelStyle); 
	    table.add(titleLabel).colspan(3).pad(20).center();
	    table.row();

	    // Add column headers
	    table.add(new Label("Rank", labelStyle)).pad(10).center();
	    table.add(new Label("Player Name", labelStyle)).pad(10).center(); 
	    table.add(new Label("Score", labelStyle)).pad(10).center();
	    table.row();

	    // Get the sorted leaderboard
	    ArrayList<Map.Entry<String, Integer>> leaderboard = LeaderboardManager.getSortedLeaderboard();
	    
	    // Setting the leaderboard
	    int rank = 1;
	    for (Map.Entry<String, Integer> entry : leaderboard) {
	        // Apply custom style to each label
	        table.add(new Label(String.valueOf(rank), labelStyle)).pad(5).center();
	        table.add(new Label(entry.getKey(), labelStyle)).pad(5).center();
	        table.add(new Label(String.valueOf(entry.getValue()), labelStyle)).pad(5).center();
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
	public void show() {
		// TODO Auto-generated method stub

	}

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
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		stage.dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
