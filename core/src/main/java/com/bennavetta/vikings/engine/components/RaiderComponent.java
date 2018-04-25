package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * State for an entity that can raid other entities.
 */
public class RaiderComponent implements Component, Poolable
{
    /**
     * The distance an entity must be within for this entity to attack it.
     */
    public float attackRange = 0f;

    /**
     * How likely we are to attack neutral targets. Values closer to 1 indicate that we're more likely to attack.
     */
    public float hostility;

    @Override
    public void reset()
    {
        attackRange = 0f;
        hostility = 0f;
    }
}
