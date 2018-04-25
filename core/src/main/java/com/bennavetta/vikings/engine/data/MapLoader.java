package com.bennavetta.vikings.engine.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.bennavetta.vikings.engine.components.Entities;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.ResourcesComponent;
import com.bennavetta.vikings.engine.components.TradeComponent;

/**
 * Applies map data to an entity system
 */
public class MapLoader
{
    public static void apply(MapData data, PooledEngine engine)
    {
        for (MapData.PlaceInfo emporium : data.emporia)
        {
            Entity entity = Entities.newEmporium(engine, emporium.name, emporium.x, emporium.y);

            ResourcesComponent res = Mappers.resources.get(entity);
            res.iron = MathUtils.random(2000);
            res.wood = MathUtils.random(5000);
            res.bread = MathUtils.random(4000);
            res.meat = MathUtils.random(5000);
            res.mead = MathUtils.random(2000);
            res.wool = MathUtils.random(4000);
            res.silver = MathUtils.random(1000);

            TradeComponent trade = Mappers.trade.get(entity);
            trade.friendliness = MathUtils.random();
            trade.value.put(ResourcesComponent.IRON, MathUtils.random());
            trade.value.put(ResourcesComponent.WOOD, MathUtils.random());
            trade.value.put(ResourcesComponent.BREAD, MathUtils.random());
            trade.value.put(ResourcesComponent.MEAT, MathUtils.random());
            trade.value.put(ResourcesComponent.MEAD, MathUtils.random());
            trade.value.put(ResourcesComponent.WOOL, MathUtils.random());
            trade.value.put(ResourcesComponent.SILVER, MathUtils.random());

            engine.addEntity(entity);
        }

        for (MapData.PlaceInfo town : data.towns)
        {
            Entity entity = Entities.newTown(engine, town.name, town.x, town.y);
            ResourcesComponent res = Mappers.resources.get(entity);
            res.iron = MathUtils.random(3000);
            res.wood = MathUtils.random(3000);
            res.bread = MathUtils.random(3000);
            res.meat = MathUtils.random(3000);
            res.mead = MathUtils.random(3000);
            res.wool = MathUtils.random(3000);
            res.silver = MathUtils.random(3000);
            engine.addEntity(entity);
        }
    }
}
