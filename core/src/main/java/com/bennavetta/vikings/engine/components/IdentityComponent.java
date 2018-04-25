package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Describes what this entity *is*.
 */
public class IdentityComponent implements Component, Poolable
{
    public EntityType type;
    public String name;

    @Override
    public String toString()
    {
        return String.format("<%s %s>", type, name);
    }

    @Override
    public void reset()
    {
        this.type = null;
        this.name = null;
    }
}
