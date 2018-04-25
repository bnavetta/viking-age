package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Contains the population at some site, with details about their labor allocation and population growth rate.
 */
public class PopulationComponent implements Component, Poolable
{
    /**
     * Accumulates population growth so we know when to add population
     */
    public float partialGrowth;

    /**
     * Workers assigned to care for sheep
     */
    public int shepherds;

    /**
     * Workers who produce iron
     */
    public int smelters;

    /**
     * Workers who harvest wood
     */
    public int foresters; // probably not the historically accurate term, but idk

    /**
     * Workers who raise cattle
     */
    public int cattleFarmers;

    /**
     * Workers who grow grain
     */
    public int wheatFarmers; // TODO: figure out which grain this would be. Maybe barley?

    /**
     * Workers who produce alcohol
     */
    public int brewers;

    /**
     * Population not assigned to a particular task
     */
    public int unassigned;

    public int getTotalPopulation()
    {
        return shepherds + smelters + foresters + cattleFarmers + wheatFarmers + brewers + unassigned;
    }

    @Override
    public void reset()
    {
        this.partialGrowth = 0;
        this.shepherds = 0;
        this.smelters = 0;
        this.foresters = 0;
        this.cattleFarmers = 0;
        this.wheatFarmers = 0;
        this.brewers = 0;
        this.unassigned = 0;
    }
}
