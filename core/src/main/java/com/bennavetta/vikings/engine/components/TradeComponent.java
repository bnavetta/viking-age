package com.bennavetta.vikings.engine.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.ObjectFloatMap;
import com.badlogic.gdx.utils.Pool;

public class TradeComponent implements Component, Pool.Poolable
{
    /**
     * How much do we like the other entities?
     */
    public ObjectFloatMap<Entity> goodwill = new ObjectFloatMap<>();

    /**
     * In general, how willing are we to trade? This is basically a goodwill threshold
     */
    public float friendliness;

    /**
     * How much do we value any given resource? Our goal is to trade low-valued resources for high-valued resources.
     */
    public ObjectFloatMap<ResourcesComponent.ResourceHandle> value = new ObjectFloatMap<>();

    @Override
    public void reset()
    {
        goodwill.clear();
        value.clear();
    }

    /*
     * Goodwill:
     *     - goodwill w/ every entity in range increases steadily over time
     *     - goodwill w/ entities out of range gradually decreases
     * Trade algorithm:
     *     - only if goodwill above threshold
     *     - for every resource type pair:
     *         - if our sell price <= their buy price, swap 1 unit of the resources
     *
     * Restocking (separate system)
     *     - amount of each resource type gradually increases up to cap
     */
}
