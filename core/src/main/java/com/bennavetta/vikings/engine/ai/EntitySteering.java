package com.bennavetta.vikings.engine.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bennavetta.vikings.engine.components.LocationComponent;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.SteeringComponent;

public class EntitySteering implements Steerable<Vector2>
{
    private final Entity entity;

    public EntitySteering(Entity entity)
    {
        this.entity = entity;
    }

    private SteeringComponent getSteering()
    {
        return Mappers.steering.get(entity);
    }

    private LocationComponent getLocation()
    {
        return Mappers.location.get(entity);
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return getSteering().linearVelocity;
    }

    @Override
    public float getAngularVelocity()
    {
        return getSteering().angularVelocity;
    }

    @Override
    public float getBoundingRadius()
    {
        return 5;
    }

    @Override
    public boolean isTagged()
    {
        return getSteering().tagged;
    }

    @Override
    public void setTagged(boolean tagged)
    {
        getSteering().tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold()
    {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value)
    {

    }

    @Override
    public float getMaxLinearSpeed()
    {
        return 15;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed)
    {

    }

    @Override
    public float getMaxLinearAcceleration()
    {
        return 2;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {

    }

    @Override
    public float getMaxAngularSpeed()
    {
        return 20;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed)
    {

    }

    @Override
    public float getMaxAngularAcceleration()
    {
        return 3;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {

    }

    @Override
    public Vector2 getPosition()
    {
        return getLocation().coordinates;
    }

    @Override
    public float getOrientation()
    {
        return getSteering().orientation;
    }

    @Override
    public void setOrientation(float orientation)
    {
        getSteering().orientation = orientation;
    }

    @Override
    public float vectorToAngle(Vector2 vector)
    {
        return MathUtils.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = -MathUtils.sin(angle);
        outVector.y = MathUtils.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation()
    {
        return new EntityLocation();
    }


}
