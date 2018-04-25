package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IdentityMap;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.*;

/**
 * UI for interacting with a player's in-range ships. Kinda like a port I guess
 */
public class PortControl extends VerticalGroup
{
    private static final Family SHIP_FAMILY = Family.all(ResourcesComponent.class, CombatComponent.class, LocationComponent.class, IdentityComponent.class).get();

    private final Entity entity;
    private final Engine engine;

    private final IdentityMap<Entity, Actor> currentContents = new IdentityMap<>();

    public PortControl(Entity entity, Engine engine)
    {
        this.engine = engine;
        this.entity = entity;

        Label title = new Label("Port", Assets.getSkin());
        title.setWidth(100);
        title.setAlignment(Align.center);
        addActor(title);

        update();
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
        update();
    }

    private void update()
    {
        LocationComponent ourLoc = Mappers.location.get(entity);

        for (Entity e : engine.getEntitiesFor(SHIP_FAMILY))
        {
            IdentityComponent id = Mappers.id.get(e);
            if (id.type != EntityType.SHIP) continue;

            CombatComponent cc = Mappers.combat.get(e);
            if (cc.affinity != CombatComponent.Affinity.PLAYER) continue;

            LocationComponent loc = Mappers.location.get(e);
            if (ourLoc.coordinates.dst2(loc.coordinates) < 300)
            {
                if (!currentContents.containsKey(e))
                {
                    dockShip(e);
                }
            }
            else
            {
                Actor current = currentContents.get(e);
                if (current != null)
                {
                    currentContents.remove(e);
                    removeActor(current);
                }
            }
        }
    }

    private void dockShip(Entity e)
    {
        IdentityComponent id = Mappers.id.get(e);
        Button b = new Button(new Label(id.name, Assets.getSkin()), Assets.getSkin());

        b.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Dialog portDialog = new Dialog(id.name, Assets.getSkin());

                ExchangeControl ex = new ExchangeControl(entity, e);
                ex.pack();

                portDialog.setSize(ex.getPrefWidth(), ex.getPrefHeight());
                portDialog.getContentTable().add(ex).center().row();

                Button repair = new TextButton("Repair", Assets.getSkin());
                repair.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor)
                    {
                        ResourcesComponent ourRes = Mappers.resources.get(entity);
                        CombatComponent theirCombat = Mappers.combat.get(e);

                        float damage = theirCombat.maxHealthPoints - theirCombat.healthPoints;
                        float woodNeeded = damage * 1.3f;
                        float woolNeeded = damage * 1.1f;
                        float ironNeeded = damage * 0.7f;
                        if (woodNeeded <= ourRes.wood && woolNeeded <= ourRes.wool && ironNeeded <= ourRes.iron)
                        {
                            theirCombat.healthPoints = theirCombat.maxHealthPoints;
                            ourRes.wood -= woodNeeded;
                            ourRes.wool -= woolNeeded;
                            ourRes.iron -= ironNeeded;
                        }
                    }
                });
                portDialog.getContentTable().add(repair).center().pad(10);

                portDialog.button("Done");
                portDialog.show(getStage());
            }
        });

        b.setWidth(100);
        addActor(b);
        currentContents.put(e, b);
    }
}
