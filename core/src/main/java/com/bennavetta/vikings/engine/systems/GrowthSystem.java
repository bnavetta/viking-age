package com.bennavetta.vikings.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.PopulationComponent;
import com.bennavetta.vikings.engine.components.ResourcesComponent;

/**
 * Implements population growth
 */
public class GrowthSystem extends IntervalIteratingSystem
{
    public GrowthSystem(float interval)
    {
        super(Family.all(PopulationComponent.class, ResourcesComponent.class).get(), interval);
    }

    @Override
    protected void processEntity(Entity entity)
    {
        PopulationComponent pop = Mappers.population.get(entity);
        int totalPop = pop.getTotalPopulation();

        // How much of each resource is consumed by the population
        float meadConsumed = totalPop * 0.5f;
        float meatConsumed = totalPop * 1.1f;
        float woolConsumed = totalPop * 0.2f;
        float breadConsumed = totalPop * 1.5f;
        float woodConsumed = totalPop * 1.1f;

        ResourcesComponent res = Mappers.resources.get(entity);
        res.meat -= meatConsumed;
        res.mead -= meadConsumed;
        res.wool -= woolConsumed;
        res.bread -= breadConsumed;
        res.wood -= woodConsumed;

        // Adjust growth proportionally to surplus
        float growthAdj = 0;

        if (res.meat > 0)
        {
            growthAdj += 0.2;
        }
        else
        {
            growthAdj -= 0.3;
        }

        if (res.mead > 0)
        {
            growthAdj += 0.1;
        }
        else
        {
            growthAdj -= 0.3;
        }

        if (res.wool > 0)
        {
            growthAdj += 0.1;
        }
        else
        {
            growthAdj -= 0.3;
        }

        if (res.wood > 0)
        {
            growthAdj += 0.1;
        }
        else
        {
            growthAdj -= 0.3;
        }

        if (res.bread > 0)
        {
            growthAdj += 0.2;
        }
        else
        {
            growthAdj -= 0.3;
        }

        pop.partialGrowth += growthAdj;

        if (pop.partialGrowth > 1)
        {
//            pop.unassigned += (int) pop.partialGrowth;
            if (res.meat < 0)
            {
                pop.cattleFarmers++;
            }
            else if (res.mead < 0)
            {
                pop.brewers++;
            }
            else if (res.wool < 0)
            {
                pop.shepherds++;
            }
            else if (res.wood < 0)
            {
                pop.foresters++;
            }
            else if (res.bread < 0)
            {
                pop.wheatFarmers++;
            }
            else
            {
                pop.unassigned++;
            }

            pop.partialGrowth = 0;
        }
        else if (pop.partialGrowth < -1)
        {
            // This *should* let the growth rate become increasingly negative and still induce a population penalty
            pop.unassigned--;
            if (pop.unassigned < 0) pop.unassigned = 0;
            pop.partialGrowth = 0;
        }

        if (res.meat < 0) res.meat = 0;
        if (res.mead < 0) res.mead = 0;
        if (res.wool < 0) res.wool = 0;
        if (res.bread < 0) res.bread = 0;
        if (res.wood < 0) res.wood = 0;
    }
}
