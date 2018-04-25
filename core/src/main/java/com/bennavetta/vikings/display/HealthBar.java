package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.CombatComponent;
import com.bennavetta.vikings.engine.components.Mappers;

public class HealthBar extends Widget
{
    private final Entity entity;

    private Drawable background;
    private Drawable bar;

    public HealthBar(Entity entity)
    {
        if (!Mappers.combat.has(entity))
        {
            throw new IllegalArgumentException("No combat component");
        }

        this.entity = entity;
        this.background = Assets.getSkin().getDrawable("default-slider");
        this.bar = new TextureRegionDrawable(new TextureRegion(Assets.getBar()));
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
        Gdx.graphics.requestRendering();
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        CombatComponent combat = Mappers.combat.get(entity);
        if (combat == null)
        {
            return;
        }

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        float x = getX();
        float y = getY();
        float width = getWidth();

        background.draw(batch, x, y, width, background.getMinHeight());

        float healthPercent = ((float) combat.healthPoints) / combat.maxHealthPoints;
        float barWidth = healthPercent * width;
        Color barColor = Color.RED.cpy().lerp(Color.GREEN, healthPercent);
        barColor.a *= parentAlpha;
        batch.setColor(barColor);
        bar.draw(batch, x, y, barWidth, background.getMinHeight());
    }

    @Override
    public float getPrefHeight()
    {
        return Math.max(background.getMinHeight(), 10);
    }

    @Override
    public float getPrefWidth()
    {
        return 140;
    }
}
