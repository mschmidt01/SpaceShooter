package com.neworder.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

/**
 * Star class.
 *
 * @author Melvin Schmidt
 *
 */

public class Star extends Gameobject{
    public Star(Assets assets){
        TextureAtlas textureAtlas = assets.manager.get(Assets.textureAtlasAssetDescriptor);
        TextureAtlas.AtlasRegion starRegion = textureAtlas.findRegion("star");
        Sprite starSprite = new Sprite(starRegion);
        starSprite.setSize(MathUtils.random(1, 4), MathUtils.random(1, 4));
        this.setSprite(starSprite);
    }
}
