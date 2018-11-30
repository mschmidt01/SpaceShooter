package com.neworder.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Start Screen Class.
 *
 * @author Melvin Schmidt
 */
public class StartScreen implements Screen {
    public Camera camera;
    public StretchViewport viewport;
    public SpriteBatch batch;
    public SpaceShooter game;

    FreeTypeFontGenerator generator;
    BitmapFont mediumFont;
    BitmapFont titleFont;
    GlyphLayout glyphLayout;
    String title;
    StarBackground starBackground;

    public StartScreen(SpaceShooter game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        viewport.apply();
        batch = game.batch;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 65;
        titleFont = generator.generateFont(parameter);
        parameter.size = 50;
        mediumFont = generator.generateFont(parameter);
        glyphLayout = new GlyphLayout();
        starBackground = new StarBackground(game.assets);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (Star star : starBackground.stars) {
            star.getSprite().draw(batch);
        }
        title = "Space Shooter";
        glyphLayout.setText(titleFont, title);

        titleFont.draw(batch, glyphLayout, (Constants.WORLD_WIDTH - glyphLayout.width) / 2, Constants.WORLD_HEIGHT - Constants.WORLD_HEIGHT / 6);
        title = "Start";
        glyphLayout.setText(mediumFont, title);
        mediumFont.draw(batch, glyphLayout, (Constants.WORLD_WIDTH - glyphLayout.width) / 2, Constants.WORLD_HEIGHT / 2);
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        title = "Options";
        glyphLayout.setText(mediumFont, title);
        mediumFont.draw(batch, glyphLayout, (Constants.WORLD_WIDTH - glyphLayout.width) / 2, Constants.WORLD_HEIGHT / 3);
        batch.end();

    }

    private void update(float delta) {
        starBackground.updateStars(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
    }
}
