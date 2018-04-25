package com.bennavetta.vikings.engine.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bennavetta.vikings.engine.components.LocationComponent;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.ResourcesComponent;
import com.bennavetta.vikings.engine.components.TradeComponent;

public class TradeSystem extends IntervalIteratingSystem
{
    private static final Family FAMILY = Family.all(TradeComponent.class, ResourcesComponent.class, LocationComponent.class).get();

    public TradeSystem(float interval)
    {
        super(FAMILY, interval);
    }

    @Override
    protected void processEntity(Entity entity)
    {
        Vector2 ourLocation = Mappers.location.get(entity).coordinates;

        for (Entity partner : getEntities())
        {
            if (partner == entity) continue;

            TradeComponent ourTrade = Mappers.trade.get(entity);
            TradeComponent theirTrade = Mappers.trade.get(partner);
            float ourGoodwill = ourTrade.goodwill.get(partner, 0.05f);
            float theirGoodwill = theirTrade.goodwill.get(entity, 0.05f);

            Vector2 theirLocation = Mappers.location.get(partner).coordinates;
            if (ourLocation.dst2(theirLocation) > 100)
            {
                float newGoodwill = ourGoodwill * 0.8f;
                if (newGoodwill < 0.05f)
                {
                    ourTrade.goodwill.remove(partner, 0f);
                }
                else
                {
                    ourTrade.goodwill.put(partner, newGoodwill);
                }
            }
            else if (ourGoodwill >= ourTrade.friendliness && theirGoodwill >= theirTrade.friendliness)
            {
                ResourcesComponent ourResources = Mappers.resources.get(entity);
                ResourcesComponent theirResources = Mappers.resources.get(partner);

                ResourcesComponent.ResourceHandle[] resources = new ResourcesComponent.ResourceHandle[ResourcesComponent.ALL_RESOURCES.length];
                System.arraycopy(ResourcesComponent.ALL_RESOURCES, 0, resources, 0, resources.length);
                shuffle(resources);

                for (ResourcesComponent.ResourceHandle ourRes : resources)
                {
                    for (ResourcesComponent.ResourceHandle theirRes : resources)
                    {
                        if (ourRes == theirRes) continue;

                        // Trade if we want their resource more than they do and they want our resource more than we do
                        boolean shouldTrade =
                                ourTrade.value.get(ourRes, 0f) < theirTrade.value.get(ourRes, 0f)
                                && ourTrade.value.get(theirRes, 0f) > theirTrade.value.get(theirRes, 0f);
                        if (shouldTrade && ourRes.get(ourResources) >= 10 && theirRes.get(theirResources) >= 10)
                        {
                            float amount;
                            float limiter = Math.min(ourRes.get(ourResources), theirRes.get(theirResources));
                            if (limiter >= 1000)
                            {
                                amount = 100;
                            }
                            else if (limiter >= 500)
                            {
                                amount = 50;
                            }
                            else if (limiter >= 100)
                            {
                                amount = 10;
                            }
                            else
                            {
                                amount = 1;
                            }

                            ourRes.increment(ourResources, -amount);
                            theirRes.increment(ourResources, amount);
                            theirRes.increment(theirResources, -amount);
                            ourRes.increment(theirResources, amount);

                            return; // Only trade one unit per iteration
                        }
                    }
                }
            }
            else
            {
                float newGoodwill = Math.min(ourGoodwill * 1.5f, 1f);
                ourTrade.goodwill.put(partner, newGoodwill);
            }
        }
    }

    /**
     * Fisher-Yates Shuffle
     */
    private static <T> void shuffle(T[] arr)
    {
        for (int i = arr.length - 1; i > 0; i--)
        {
            int j = MathUtils.random(i);
            T tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
