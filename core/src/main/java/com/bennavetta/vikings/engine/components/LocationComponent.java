package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * The location of an entity on the world map
 */
public class LocationComponent implements Component, Poolable
{
    /**
     * The (latitude, longitude) coordinates of the entity.
     */
    public Vector2 coordinates = new Vector2();

    @Override
    public void reset()
    {
        coordinates.set(0, 0);
    }
}
