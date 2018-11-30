package com.neworder.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Game screen class.
 * contains the game loop.
 *
 * @author Melvin Schmidt
 */

public class GameScreen implements Screen {
    SpaceShooter game;
    GameController controller;
    GameRenderer renderer;
    FreeTypeFontGenerator generator;
    BitmapFont font;

    public GameScreen(SpaceShooter game) {
        this.game = game;
    }

    @Override
    public void show() {
        controller = new GameController(game.assets);
        renderer = new GameRenderer(game, controller);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
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
        controller.dispose();
        renderer.dispose();
        generator.dispose();
    }
}
