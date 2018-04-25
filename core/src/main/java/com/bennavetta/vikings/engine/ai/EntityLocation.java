package com.bennavetta.vikings.engine.ai;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

class EntityLocation implements Location<Vector2>
{
    private Vector2 position = new Vector2();
    private float orientation = 0;

    @Override
    public Vector2 getPosition()
    {
        return position;
    }

    @Override
    public float getOrientation()
    {
        return orientation;
    }

    @Override
    public void setOrientation(float orientation)
    {
        this.orientation = orientation;
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
