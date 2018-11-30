package com.neworder.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

/**
 * Game Controller Class.
 * Implements the game logic like spawning enemies, shooting bullets etc.
 * Is called by the gamescreen class in the main game loop.
 * @author Melvin Schmidt
 */
public class GameController implements Disposable {
    TextureAtlas textureAtlas;
    SpaceCraft player;
    float playerBulletTimer;
    float enemyBulletTimer;
    boolean gameOver;
    boolean isNewHighscore;
    int gameScore;
    Music music;
    AssetManager assetManager;
    Preferences preferences;

    public Array<Bullet> activeBullets = new Array<Bullet>();

    public Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet();
        }
    };

    public Array<Enemy> activeEnemies = new Array<Enemy>();

    public StarBackground starBackground;
    public Pool<Enemy> enemyPool = new Pool<Enemy>() {
        @Override
        protected Enemy newObject() {
            return new Enemy();
        }
    };

    public GameController(Assets assets) {
        assetManager = new AssetManager();
        assetManager.load("sounds/spacegun.mp3", Music.class);
        assetManager.finishLoading();
        textureAtlas = new TextureAtlas(Gdx.files.internal("packed/packed.atlas"));
        starBackground = new StarBackground(assets);
        preferences = Gdx.app.getPreferences("spaceshooter");
        init();
    }

    public void init() {
        gameOver = false;
        isNewHighscore = false;
        initPlayer();
    }

    public void restart() {
        gameScore = 0;
        enemyPool.freeAll(activeEnemies);
        bulletPool.freeAll(activeBullets);
        enemyPool.clear();
        bulletPool.clear();
        init();
    }


    public void initPlayer() {
        player = new SpaceCraft();
        Sprite spaceCraftBodySprite = textureAtlas.createSprite("spaceshipbody");
        player.setSprite(spaceCraftBodySprite);
        player.getSprite().setSize(75, 150);
        player.getSprite().setPosition((Constants.WORLD_WIDTH / 2) - (player.getSprite().getWidth() / 2), 100);
    }

    public void update(float deltaTime) {
        if (!gameOver) {
            starBackground.updateStars(deltaTime);
            updatePlayer(deltaTime);
            spwanNewPlayerBullet(deltaTime);
            updateBullets(deltaTime);
            freeDeadBullets(deltaTime);
            spwawnEnemy(deltaTime);
            updateEnemy(deltaTime);
            freeDeadEnemies(deltaTime);
            spawnNewEnemyBullet(deltaTime);
            updateBullets(deltaTime);
            checkForCollision();
        }
    }


    private void checkForCollision() {
        for (Bullet bullet : activeBullets) {
            if (bullet.type.equals("enemy")) {
                if (player.getSprite().getBoundingRectangle().overlaps(bullet.getSprite().getBoundingRectangle())) {
                    gameOver = true;
                    setHighscore();
                }
            }
            if (bullet.type.equals( "player")) {
                for (Enemy enemy : activeEnemies) {
                    if (enemy.getSprite().getBoundingRectangle().overlaps(bullet.getSprite().getBoundingRectangle())) {
                        enemy.alive = false;
                        gameScore += 1;
                    }
                }
            }
        }
    }


    private void spawnNewEnemyBullet(float deltaTime) {
        for (Enemy enemy : activeEnemies) {
            enemy.bulletTimeCounter += deltaTime;
            if (enemy.bulletTimeCounter > 1.1) {
                enemy.bulletTimeCounter = 0;
                Bullet bullet = bulletPool.obtain();
                bullet.init("enemy");
                Sprite bulletSprite = textureAtlas.createSprite("spaceshipbullet");
                bulletSprite.setColor(1, 0, 0, 1);
                bullet.setSprite(bulletSprite);
                bullet.getSprite().setPosition(enemy.getSprite().getX() + enemy.getSprite().getWidth() / 2, enemy.getSprite().getY() - enemy.getSprite().getHeight());
                bullet.getSprite().setSize(8, 35);
                bullet.setVelocity(-1 * MathUtils.random(200, 600));
                activeBullets.add(bullet);
                if (assetManager.isLoaded("sounds/spacegun.mp3")) {
                    music = assetManager.get("sounds/spacegun.mp3", Music.class);
                    music.play();
                }
            }
        }

    }

    private void updateEnemy(float deltaTime) {
        for (Enemy item : activeEnemies) {
            item.getSprite().setPosition(item.getSprite().getX(), item.getSprite().getY() - (deltaTime * item.getVelocity()));
            if (item.getSprite().getY() < 0) {
                gameOver = true;
                setHighscore();
            }
        }
    }

    private void setHighscore() {
        if(preferences.getInteger("highscore",0) < gameScore){
            isNewHighscore = true;
            preferences.putInteger("highscore", gameScore);
            preferences.flush();
        }
    }

    private void spwawnEnemy(float deltaTime) {
        enemyBulletTimer += deltaTime;
        if (enemyBulletTimer > 0.9) {
            enemyBulletTimer = 0;
            Enemy item = enemyPool.obtain();
            item.alive = true;
            Sprite enemySprite = textureAtlas.createSprite("enemy");
            item.setSprite(enemySprite);
            item.getSprite().setSize(100, 100);
            item.getSprite().setPosition(MathUtils.random(0, Constants.WORLD_WIDTH - item.getSprite().getWidth()), Constants.WORLD_HEIGHT + item.getSprite().getHeight());

            item.setVelocity(400);
            activeEnemies.add(item);
        }
    }

    private void updateBullets(float deltaTime) {
        for (Bullet item : activeBullets) {
            item.getSprite().setPosition(item.getSprite().getX(), item.getSprite().getY() + (deltaTime * item.getVelocity()));
            if (item.getSprite().getX() > Constants.WORLD_WIDTH
                    || item.getSprite().getX() < 0
                    || item.getSprite().getY() > Constants.WORLD_HEIGHT
                    || item.getSprite().getY() < 0) {
                item.alive = false;
            }
        }

    }

    private void freeDeadEnemies(float deltaTime) {
        Enemy item;
        int len = activeEnemies.size;
        for (int i = len; --i >= 0; ) {
            item = activeEnemies.get(i);
            if (!item.alive) {
                activeEnemies.removeIndex(i);
                enemyPool.free(item);
            }
        }
    }

    private void freeDeadBullets(float deltaTime) {
        Bullet item;
        int len = activeBullets.size;
        for (int i = len; --i >= 0; ) {
            item = activeBullets.get(i);
            if (!item.alive) {
                activeBullets.removeIndex(i);
                bulletPool.free(item);
            }
        }
    }

    private void spwanNewPlayerBullet(float deltaTime) {
        playerBulletTimer += deltaTime;
        if (playerBulletTimer > 0.2) {
            playerBulletTimer = 0;
            Bullet item = bulletPool.obtain();
            item.init("player");
            Sprite spaceShipBullet = textureAtlas.createSprite("spaceshipbullet");
            item.setSprite(spaceShipBullet);
            item.getSprite().setPosition(player.getSprite().getX() + player.getSprite().getWidth() / 2,
                    player.getSprite().getY() + player.getSprite().getHeight());
            item.getSprite().setSize(8, 35);
            item.setVelocity(300);
            activeBullets.add(item);
        }
    }


    private void updatePlayer(float deltaTime) {
        float gyroY = Gdx.input.getGyroscopeY() * 2;
        player.getSprite().setPosition(Math.min(Math.max(player.getSprite().getX() + (deltaTime * player.getVelocity() * gyroY),
                player.getSprite().getWidth() / 2), Constants.WORLD_WIDTH - player.getSprite().getWidth()),
                player.getSprite().getY());

    }

    public void dispose() {
        textureAtlas.dispose();
        music.dispose();
        assetManager.dispose();
    }

}
