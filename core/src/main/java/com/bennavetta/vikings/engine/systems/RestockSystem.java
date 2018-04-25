package com.bennavetta.vikings.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.bennavetta.vikings.engine.components.EmporiumComponent;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.ResourcesComponent;
import com.bennavetta.vikings.engine.components.TownComponent;

public class RestockSystem extends IteratingSystem
{
    public RestockSystem()
    {
        super(Family.one(TownComponent.class, EmporiumComponent.class).all(ResourcesComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        ResourcesComponent resources = Mappers.resources.get(entity);

        if (resources.iron < 3000)
        {
            resources.iron += deltaTime * (MathUtils.randomTriangular() + 0.5f);
        }

        if (resources.meat < 3000)
        {
            resources.meat += deltaTime * (MathUtils.randomTriangular() + 0.5f);
        }

        if (resources.wood < 3000)
        {
            resources.wood += deltaTime * (MathUtils.randomTriangular() + 0.5f);
        }

        if (resources.silver < 3000)
        {
            resources.silver += deltaTime * (MathUtils.randomTriangular() + 0.5f);
        }

        if (resources.bread < 3000)
        {
            resources.bread += deltaTime * (MathUtils.randomTriangular() + 0.5f);
        }

        if (resources.wool < 3000)
        {
            resources.wool += deltaTime * (MathUtils.randomTriangular() + 0.5f);
        }

        if (resources.mead < 3000)
        {
            resources.mead += deltaTime * (MathUtils.randomTriangular() + 0.5f);
        }
    }
}
