package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.CombatComponent;
import com.bennavetta.vikings.engine.components.IdentityComponent;
import com.bennavetta.vikings.engine.components.Mappers;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Group implementation for the world map
 */
public class WorldMap extends Group implements EntityListener
{
    private final Map<Entity, EntityActor> actors = new IdentityHashMap<>();

    public WorldMap()
    {
        setSize(1000, 500);

        addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y)
            {
                mouseLoc.set(x, y);
                return false;
            }
        });
    }

    private final Vector2 mouseLoc = new Vector2();

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(Assets.getMap(), getX(), getY(), getWidth(), getHeight());

//        BitmapFont font = Assets.getSkin().getFont("default-font");
//
//        batch.setColor(Color.BLACK);
//        font.draw(batch, String.format("(%.2ff, %.2f)", mouseLoc.x, mouseLoc.y), getX() + 30, getY() + 30);

        super.draw(batch, parentAlpha);
    }

    @Override
    public void entityAdded(Entity entity)
    {
        IdentityComponent id = Mappers.id.get(entity);
        if (id == null)
        {
            throw new IllegalStateException("Missing ID component");
        }

        CombatComponent cc = Mappers.combat.get(entity);
        boolean isPlayer = cc != null && cc.affinity == CombatComponent.Affinity.PLAYER;

        Gdx.app.log("WorldMap", String.format("Adding actor for entity '%s' (%s)", id.name, id.type));

        EntityActor actor = new EntityActor(entity);
        actor.setSize(10, 10);

        switch (id.type)
        {
            case SHIP:
                actor.setColor(isPlayer ? Color.BLUE : Color.RED);
                break;
            case CENTRAL_PLACE:
                actor.setColor(Color.GOLD);
                break;
            case EMPORIUM:
                actor.setColor(Color.ORANGE);
                break;
            case TOWN:
                actor.setColor(Color.PURPLE);
                break;
            case MONASTERY:
                actor.setColor(Color.BLACK);
                break;
            default:
                throw new IllegalArgumentException("Unknown entity type: " + id.type);
        }

        addActor(actor);
        actors.put(entity, actor);
    }

    @Override
    public void entityRemoved(Entity entity)
    {
        EntityActor actor = actors.get(entity);
        if (actor == null)
        {
            throw new IllegalStateException("Missing EntityActor for entity" + entity);
        }

        Gdx.app.log("WorldMap", String.format("Removing actor for entity %s", entity));

        actors.remove(entity);
        removeActor(actor);
    }
}
