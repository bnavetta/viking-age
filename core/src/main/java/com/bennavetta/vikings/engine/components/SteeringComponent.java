package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class SteeringComponent implements Component, Pool.Poolable
{
    public float orientation;
    public Vector2 linearVelocity = new Vector2();
    public float angularVelocity;
    public float maxSpeed;
    public boolean independentFacing;
    public SteeringBehavior<Vector2> steeringBehavior;
    public boolean tagged;

    @Override
    public void reset()
    {
        this.orientation = 0;
        this.linearVelocity.setZero();
        this.angularVelocity = 0;
        this.maxSpeed = 0;
        this.independentFacing = false;
        this.steeringBehavior = null;
        this.tagged = false;
    }
}
