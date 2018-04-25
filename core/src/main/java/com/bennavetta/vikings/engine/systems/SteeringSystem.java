package com.bennavetta.vikings.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bennavetta.vikings.engine.components.LocationComponent;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.SteeringComponent;

public class SteeringSystem extends IteratingSystem
{
    private static final SteeringAcceleration<Vector2> steeringOutput =
            new SteeringAcceleration<>(new Vector2());

    public SteeringSystem()
    {
        super(Family.all(SteeringComponent.class, LocationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        SteeringComponent steering = Mappers.steering.get(entity);
        LocationComponent loc = Mappers.location.get(entity);

        if (steering.steeringBehavior != null)
        {
            steering.steeringBehavior.calculateSteering(steeringOutput);
            loc.coordinates.mulAdd(steering.linearVelocity, deltaTime);
            steering.linearVelocity.mulAdd(steeringOutput.linear, deltaTime).limit(steering.maxSpeed);
            if (!steering.linearVelocity.isZero(0.01f))
            {
                float newOrientation = vectorToAngle(steering.linearVelocity);
                steering.angularVelocity = (newOrientation - steering.orientation) * deltaTime;
                steering.orientation = newOrientation;

            }
        }
    }

    public float vectorToAngle(Vector2 vector)
    {
        return MathUtils.atan2(-vector.x, vector.y);
    }

    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = -MathUtils.sin(angle);
        outVector.y = MathUtils.cos(angle);
        return outVector;
    }

}
