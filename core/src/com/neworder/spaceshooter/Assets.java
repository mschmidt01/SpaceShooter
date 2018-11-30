package com.neworder.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Assets class.
 * Helper class to load manage all game assets.
 *
 * @author Melvin Schmidt
 */
public class Assets {
    public AssetManager manager = new AssetManager();

    public static final AssetDescriptor<TextureAtlas> textureAtlasAssetDescriptor =
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("packed/packed.atlas"), TextureAtlas.class);


    public void load() {
        manager.load(textureAtlasAssetDescriptor);
    }

    public void dispose() {
        manager.dispose();
    }

}
