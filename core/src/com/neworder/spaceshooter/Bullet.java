package com.neworder.spaceshooter;

import com.badlogic.gdx.utils.Pool;
import com.neworder.spaceshooter.Gameobject;


/**
 * Bullet Class.
 * uses an Object Pool because performance can be critical with heavy object creation.
 * @see <a href="https://github.com/libgdx/libgdx/wiki/Memory-management">Memory Managment</a> for more Info
 * @author Melvin Schmidt
 *
 */
public class Bullet extends Gameobject implements Pool.Poolable{
    public boolean alive;
    public String type;
    public Bullet(){
        alive = false;
    }
    public void init(String type){
        this.type = type;
        alive = true;
    }
    @Override
    public void reset() {
        this.getSprite().setPosition(0,0);
        this.setVelocity(0);
        alive = false;
        type = null;
    }
}
