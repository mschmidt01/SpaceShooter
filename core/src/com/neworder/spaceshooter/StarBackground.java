package com.neworder.spaceshooter;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Starbackground class.
 *
 * @author Melvin Schmidt
 */
public class StarBackground {
    private Assets assets;
    List<Star> stars;

    public StarBackground(Assets assets) {
        stars = new ArrayList<Star>();
        this.assets = assets;
        initStars();
    }

    private void initStars() {
        int starCount = (int) (Constants.WORLD_HEIGHT * Constants.WORLD_WIDTH * Constants.STAR_DENSITY);
        for (int i = 0; i < starCount; i++) {
            Star star = new Star(assets);

            star.setPosition(MathUtils.random(0, Constants.WORLD_WIDTH - (star.getSprite().getWidth() / 2)),
                    MathUtils.random(0, Constants.WORLD_HEIGHT - (star.getSprite().getWidth() / 2)));

            star.setVelocity(MathUtils.random(0, 1000));
            stars.add(star);
        }

    }

    public void updateStars(float deltaTime) {
        for (Star star : stars) {
            star.getSprite().setPosition(star.getSprite().getX(), star.getSprite().getY() - star.getVelocity() * deltaTime);
            if (star.getSprite().getY() < 0) {
                star.getSprite().setY(Constants.WORLD_HEIGHT);
            }
        }
    }

}
