package com.bennavetta.vikings.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.bennavetta.vikings.engine.components.LocationComponent;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.NavigationComponent;

/**
 * Handles AI navigation of entities.
 */
public class NavigationSystem extends IteratingSystem
{
    private final Vector2 tmp = new Vector2();

    public NavigationSystem()
    {
        super(Family.all(NavigationComponent.class, LocationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        LocationComponent loc = Mappers.location.get(entity);
        NavigationComponent nav = Mappers.navigation.get(entity);

        if (nav.hasDestination())
        {
            tmp.set(nav.destination).sub(loc.coordinates);
            tmp.setLength(nav.speed * deltaTime);
            loc.coordinates.add(tmp);

            float dstRemaining = nav.destination.dst2(loc.coordinates);
            if (dstRemaining < 0.1)
            {
                nav.clearDestination();
            }
        }
    }
}
