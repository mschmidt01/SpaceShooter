package com.neworder.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Base class for game objects.
 * @author Melvin Schmidt
 */

public abstract class Gameobject {
    public float velocity;
    public Sprite sprite;

    public Sprite getSprite() {
        return sprite;
    }

    public void setPosition(float x , float y){
        sprite.setPosition(x,y);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }


}
