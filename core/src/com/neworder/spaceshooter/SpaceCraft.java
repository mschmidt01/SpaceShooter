package com.neworder.spaceshooter;

/**
 * Spacecraft Class.
 *
 * @author Melvin Schmidt
 *
 */

public class SpaceCraft extends Gameobject {


    public SpaceCraft(){
        init();
    }

    private void init() {
        velocity = 1000.f;
    }
}
