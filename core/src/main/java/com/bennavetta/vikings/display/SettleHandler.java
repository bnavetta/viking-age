package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.*;

public class SettleHandler extends ClickListener
{
    private static final float WOOD_REQ = 1000;
    private static final float MEAT_REQ = 400;
    private static final float IRON_REQ = 200;
    private static final float WOOL_REQ = 400;
    private static final float MEAD_REQ = 300;
    private static final float BREAD_REQ = 800;

    private final Entity entity;
    private final PooledEngine engine;

    public SettleHandler(PooledEngine engine, Entity entity)
    {
        this.entity = entity;
        this.engine = engine;
    }

    private boolean canSettle()
    {
        ResourcesComponent res = Mappers.resources.get(entity);
        return res.wood >= WOOD_REQ && res.meat >= MEAT_REQ && res.iron >= IRON_REQ && res.wool >= WOOL_REQ
                && res.mead >= MEAD_REQ && res.bread >= BREAD_REQ;
    }

    @Override
    public void clicked(InputEvent event, float x, float y)
    {
        if (canSettle())
        {
            TextField name = new TextField("", Assets.getSkin());

            Dialog setup = new Dialog("Found Settlement", Assets.getSkin()) {
                @Override
                protected void result(Object object)
                {
                    if (object == Boolean.TRUE)
                    {
                        LocationComponent loc = Mappers.location.get(entity);
                        float startX = loc.coordinates.x + MathUtils.random(-10, 10);
                        float startY = loc.coordinates.y + MathUtils.random(-10, 10);
                        Entity settlement = Entities.newCentralPlace(engine, name.getText(), startX, startY);

                        ResourcesComponent shipRes = Mappers.resources.get(entity);
                        ResourcesComponent settleRes = Mappers.resources.get(settlement);
                        shipRes.wood -= WOOD_REQ;
                        shipRes.meat -= MEAT_REQ;
                        shipRes.iron -= IRON_REQ;
                        shipRes.wool -= WOOL_REQ;
                        shipRes.mead -= MEAD_REQ;
                        shipRes.bread -= BREAD_REQ;

                        settleRes.wood = WOOD_REQ;
                        settleRes.meat = MEAT_REQ;
                        settleRes.iron = IRON_REQ;
                        settleRes.wool = WOOL_REQ;
                        settleRes.mead = MEAD_REQ;
                        settleRes.bread = BREAD_REQ;

                        PopulationComponent pop = Mappers.population.get(settlement);
                        pop.unassigned = 15;

                        engine.addEntity(settlement);
                    }
                }
            };
            setup.getContentTable().pad(30);
            setup.getContentTable().add(new Label("Name:", Assets.getSkin())).padRight(5).center();
            setup.getContentTable().add(name).center();
            setup.button("Found", Boolean.TRUE);
            setup.button("Cancel", Boolean.FALSE);
            setup.show(event.getStage());
        }
        else
        {
            Dialog sorry = new Dialog("Not enough resources", Assets.getSkin());
            sorry.text("Your ship doesn't have enough resources to start a settlement");
            sorry.button("Ok");
            sorry.show(event.getStage());
        }
    }
}
