package com.neworder.spaceshooter;

import com.badlogic.gdx.utils.Pool;

/**
 * Enemy class.
 * uses Object Pool because performance can be critical with heavy object creation.
 * @see <a href="https://github.com/libgdx/libgdx/wiki/Memory-management">Memory Managment</a> for more info
 * @author Melvin Schmidt
 *
 */
public class Enemy extends Gameobject implements Pool.Poolable{
    public boolean alive = false;
    float bulletTimeCounter = 0;
    public Enemy(){

    }
    public void init(){
        alive = true;
    }
    @Override
    public void reset() {
        this.getSprite().setPosition(0,0);
        this.setVelocity(0);
        alive = false;
    }
}
