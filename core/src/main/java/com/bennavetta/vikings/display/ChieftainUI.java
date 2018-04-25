package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.*;

public class ChieftainUI extends Group implements EntityListener
{
    private static final String TAG = "ChieftainUI";

    /**
     * The game map
     */
    private final WorldMap map;

    private final PooledEngine engine;

    /**
     * The currently-selected entity
     */
    private Entity curEntity;

    private final Table controlTable;

    public ChieftainUI(PooledEngine engine)
    {
        this.engine = engine;
        setSize(1200, 800);

        map = new WorldMap();
        map.setPosition(100, 300);
        map.addListener(new ControlListener());
        addActor(map);

        controlTable = new Table(Assets.getSkin());
        controlTable.setBounds(100, 0, 1000, 300);
        addActor(controlTable);
        controlTable.left().top();

        engine.addEntityListener(this);
        clearEntity();
    }

    public WorldMap getMap()
    {
        return map;
    }

    public void clearEntity()
    {
        curEntity = null;
        controlTable.clearChildren();
        controlTable.center();
        Label label = new Label("Who knows, my name may yet become\nRenowned far and wide in the end", Assets.getSkin());
        label.setAlignment(Align.center);
        controlTable.add(label).center();
    }

    private void setCurrentEntity(Entity entity)
    {
        CombatComponent cc = Mappers.combat.get(entity);

        this.curEntity = entity;

        IdentityComponent id = Mappers.id.get(entity);

        Gdx.app.log(TAG, String.format("Selecting entity '%s' (%s)", id.name, id.type.name()));

        // Reset to entity-specific set of controls
        controlTable.clearChildren();

        Label nameLabel = new Label(id.name, Assets.getSkin());

        VerticalGroup idGroup = new VerticalGroup();
        idGroup.addActor(nameLabel);
        controlTable.add(idGroup);

        HealthBar healthBar = new HealthBar(entity);
        idGroup.addActor(healthBar);

        if (Mappers.resources.has(entity))
        {
            controlTable.add(new ResourcesTable(entity));
        }

        if (Mappers.population.has(entity))
        {
            controlTable.add(new PopulationControl(entity));
        }

        if (id.type == EntityType.CENTRAL_PLACE)
        {
            controlTable.add(new PortControl(entity, engine)).top();

            TextButton shipButton = new TextButton("Build Ship", Assets.getSkin());
            shipButton.addListener(new BuildShipHandler(engine, entity));
            controlTable.add(shipButton).width(100).pad(10);
        }

        if (id.type == EntityType.SHIP)
        {
            TextButton settleButton = new TextButton("Settle", Assets.getSkin());
            settleButton.addListener(new SettleHandler(engine, entity));

            controlTable.add(settleButton).width(100).pad(10);
        }

        if (Mappers.trade.has(entity))
        {
            controlTable.add(new TradeControl(entity));
        }
    }

    private void moveEntity(float x, float y)
    {
        if (curEntity == null) return;

        NavigationComponent nav = Mappers.navigation.get(curEntity);
        if (nav == null) return;

        Gdx.app.log(TAG, String.format("Navigating current entity to (%f, %f)", x, y));
        nav.destination.set(x, y);
    }

    @Override
    public void entityAdded(Entity entity)
    {

    }

    @Override
    public void entityRemoved(Entity entity)
    {
        if (entity == curEntity)
        {
            clearEntity();
        }
    }

    private class ControlListener extends InputListener
    {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
        {
            if (event.getButton() == Input.Buttons.RIGHT)
            {
                moveEntity(x, y);
                return true;
            }
            else if (event.getTarget() instanceof EntityActor)
            {
                Entity target = ((EntityActor) event.getTarget()).getEntity();

                CombatComponent cc = Mappers.combat.get(target);
                if (cc == null || cc.affinity != CombatComponent.Affinity.PLAYER)
                {
                    if (Mappers.resources.has(target))
                    {
                        IdentityComponent id = Mappers.id.get(target);

                        Dialog resourcesDialog = new Dialog(id.name, Assets.getSkin());
                        resourcesDialog.getContentTable().add(new ResourcesTable(target)).center().row();

                        if (Mappers.combat.has(target))
                        {
                            resourcesDialog.getContentTable()
                                    .add(new HealthBar(target))
                                    .padTop(10).padBottom(10).center().row();;
                        }

                        resourcesDialog.button("Done");
                        resourcesDialog.show(getStage());
                    }
                }
                else
                {
                    setCurrentEntity(target);
                }

                return true;
            }

            return false;
        }
    }
}
