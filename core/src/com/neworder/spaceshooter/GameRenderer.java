package com.neworder.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;


/**
 * Render class.
 * Renders all game objects to the screen.
 * Is called by the gamescreen class in the main game loop.
 *
 * @author Melvin Schmidt
 */
public class GameRenderer implements Disposable {
    GameController controller;
    OrthographicCamera camera;
    StretchViewport viewport;
    SpriteBatch batch;
    BitmapFont scoreFont;
    BitmapFont gameOverFont;
    GlyphLayout glyphLayout;
    Preferences preferences;

    public GameRenderer(SpaceShooter game, GameController controller) {
        this.controller = controller;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        viewport.apply();
        batch = game.batch;
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 70;

        scoreFont = generator.generateFont(fontParameter);
        fontParameter.size = 50;
        gameOverFont = generator.generateFont(fontParameter);
        glyphLayout = new GlyphLayout();
        preferences = Gdx.app.getPreferences("spaceshooter");
    }

    public void render(float deltaTime) {

        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (Star star : controller.starBackground.stars) {
            star.getSprite().draw(batch);
        }
        if (controller.gameOver) {
            int highscore = preferences.getInteger("highscore", 0);
            if (controller.isNewHighscore) {
                gameOverFont.setColor(1, 0, 0, 1);
                gameOverFont.draw(batch, "New Highscore: " + Integer.toString(highscore), 0, Constants.WORLD_HEIGHT - 50);
                gameOverFont.setColor(255, 255, 255, 1);
            } else {
                gameOverFont.draw(batch, "Highscore: " + Integer.toString(highscore), 0, Constants.WORLD_HEIGHT - 50);
                gameOverFont.draw(batch, "Score: " + Integer.toString(controller.gameScore), 0, Constants.WORLD_HEIGHT - 150);
            }
            if (Gdx.input.isTouched()) {
                controller.restart();
            }
            gameOverFont.draw(batch, "Tap to restart!", 0, Constants.WORLD_HEIGHT / 2);
        } else {
            controller.player.getSprite().draw(batch);

            for (Bullet bullet : controller.activeBullets) {
                bullet.getSprite().draw(batch);
            }
            for (Enemy enemy : controller.activeEnemies) {
                enemy.getSprite().draw(batch);
            }
            scoreFont.draw(batch, Integer.toString(controller.gameScore), Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT - 200);
        }

        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void dispose() {
        scoreFont.dispose();
    }
}
