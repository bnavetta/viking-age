package com.bennavetta.vikings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

/**
 * Type-safe container for game assets.
 */
public class Assets implements Disposable
{
    private static Assets INSTANCE;

    private final AssetManager manager;

    private static final AssetDescriptor<Texture> MAP_PIN = new AssetDescriptor<>("map-pin.png", Texture.class);
    private static final AssetDescriptor<Texture> MAP = new AssetDescriptor<>("map.png", Texture.class);
    private static final AssetDescriptor<Texture> BAR = new AssetDescriptor<>("bar.png", Texture.class);

    private final Skin uiSkin;

    private Assets()
    {
        this.manager = new AssetManager();
        manager.load(MAP_PIN);
        manager.load(MAP);
        manager.load(BAR);

        this.uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
    }

    public static AssetManager getManager()
    {
        return INSTANCE.manager;
    }

    public static Texture getMapPin()
    {
        return INSTANCE.manager.get(MAP_PIN);
    }

    public static Texture getMap()
    {
        return INSTANCE.manager.get(MAP);
    }

    public static Texture getBar()
    {
        return INSTANCE.manager.get(BAR);
    }

    public static Skin getSkin()
    {
        return INSTANCE.uiSkin;
    }

    public static void init()
    {
        INSTANCE = new Assets();
    }

    @Override
    public void dispose()
    {
        manager.dispose();
        uiSkin.dispose();
    }
}
