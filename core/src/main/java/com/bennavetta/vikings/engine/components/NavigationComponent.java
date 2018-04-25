package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class NavigationComponent implements Component, Pool.Poolable
{
    public final Vector2 destination = new Vector2(-1, -1);
    public float speed = 0f;

    public boolean hasDestination()
    {
        return destination.x > -1;
    }

    public void clearDestination()
    {
        destination.set(-1, -1);
    }

    @Override
    public void reset()
    {
        clearDestination();
        speed = 0f;
    }
}
