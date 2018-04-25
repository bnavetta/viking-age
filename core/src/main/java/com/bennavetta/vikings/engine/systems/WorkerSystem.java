package com.bennavetta.vikings.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.PopulationComponent;
import com.bennavetta.vikings.engine.components.ResourcesComponent;

/**
 * Applies the labor done by workers to the resources an entity possesses.
 */
public class WorkerSystem extends IntervalIteratingSystem
{
    private static final float BREAD_RATE = 12f;
    private static final float IRON_RATE = 0.5f;
    private static final float MEAD_RATE = 4f;
    private static final float MEAT_RATE = 9f;
    private static final float WOOD_RATE = 7f;
    private static final float WOOL_RATE = 2f;

    public WorkerSystem(float interval)
    {
        super(Family.all(ResourcesComponent.class, PopulationComponent.class).get(), interval);
    }

    @Override
    protected void processEntity(Entity entity)
    {
        ResourcesComponent resources = Mappers.resources.get(entity);
        PopulationComponent population = Mappers.population.get(entity);

        resources.bread += BREAD_RATE * population.wheatFarmers;
        resources.iron += IRON_RATE * population.smelters;
        resources.mead += MEAD_RATE * population.brewers;
        resources.meat += MEAT_RATE * population.cattleFarmers;
        resources.wood += WOOD_RATE * population.foresters;
        resources.wool += WOOL_RATE * population.shepherds;
    }
}
