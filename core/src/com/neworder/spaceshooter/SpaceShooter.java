package com.neworder.spaceshooter;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Spaceshooter class.
 * loads all assets and initializes the startscreen.
 * @author Melvin Schmidt
 *
 */
public class SpaceShooter extends Game {
    public SpriteBatch batch;
    Assets assets;
	@Override
	public void create() {
        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();
        batch = new SpriteBatch();
        StartScreen startScreen = new StartScreen(this);
        this.setScreen(startScreen);
	}

    @Override
    public void render() {
        super.render();
    }
    public void dispose() {
        batch.dispose();
        assets.dispose();
    }

}
