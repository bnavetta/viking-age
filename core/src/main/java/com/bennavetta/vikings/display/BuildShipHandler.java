package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.ShipName;
import com.bennavetta.vikings.engine.components.*;

public class BuildShipHandler extends ClickListener
{
    private static final float WOOD_REQ = 400;
    private static final float IRON_REQ = 200;
    private static final float WOOL_REQ = 500;

    private final PooledEngine engine;
    private final Entity entity;

    public BuildShipHandler(PooledEngine engine, Entity entity)
    {
        this.engine = engine;
        this.entity = entity;
    }

    private boolean canBuild()
    {
        ResourcesComponent res = Mappers.resources.get(entity);
        return res.wood >= WOOD_REQ && res.iron >= IRON_REQ && res.wool >= WOOL_REQ;
    }

    @Override
    public void clicked(InputEvent event, float x, float y)
    {
        if (canBuild())
        {
            ResourcesComponent res = Mappers.resources.get(entity);
            res.wood -= WOOD_REQ;
            res.iron -= IRON_REQ;
            res.wool -= WOOL_REQ;

            LocationComponent loc = Mappers.location.get(entity);
            float startX = loc.coordinates.x + MathUtils.random(-10, 10);
            float startY = loc.coordinates.y + MathUtils.random(-10, 10);

            Entity ship = Entities.newRaidingShip(engine, ShipName.generate(), CombatComponent.Affinity.PLAYER, startX, startY);
            ship.add(engine.createComponent(TradeComponent.class));
            engine.addEntity(ship);
        }
        else
        {
            Dialog d = new Dialog("Not enough resources", Assets.getSkin());
            IdentityComponent id = Mappers.id.get(entity);
            d.text(id.name + " doesn't have enough resources to build a ship");
            d.button("Ok");
            d.show(event.getStage());
        }
    }
}
