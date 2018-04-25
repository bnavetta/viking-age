package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.LocationComponent;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.NavigationComponent;

public class EntityActor extends Actor
{
    private final Entity entity;

    public EntityActor(Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
    {
        return entity;
    }

    @Override
    public void act(float delta)
    {
        LocationComponent loc = Mappers.location.get(entity);
        setPosition(loc.coordinates.x, loc.coordinates.y);

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(Assets.getMapPin(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes)
    {
        super.drawDebug(shapes);

        // Show navigation lines
        if (Mappers.navigation.has(entity))
        {
            NavigationComponent nav = Mappers.navigation.get(entity);
            if (nav.hasDestination())
            {
                LocationComponent loc = Mappers.location.get(entity);

                shapes.set(ShapeRenderer.ShapeType.Line);
                shapes.setColor(Color.PURPLE);
                shapes.line(loc.coordinates, nav.destination);
            }
        }
    }
}
