package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.ResourcesComponent;

public class ResourcesTable extends Table
{
    private final Entity entity;

    private final Label wood = new Label("0", Assets.getSkin());
    private final Label meat = new Label("0", Assets.getSkin());
    private final Label iron = new Label("0", Assets.getSkin());
    private final Label wool = new Label("0", Assets.getSkin());
    private final Label mead = new Label("0", Assets.getSkin());
    private final Label bread = new Label("0", Assets.getSkin());
    private final Label silver = new Label("0", Assets.getSkin());

    public ResourcesTable(Entity entity)
    {
        super(Assets.getSkin());

        if (!Mappers.resources.has(entity))
        {
            throw new IllegalArgumentException("No resources component");
        }

        this.entity = entity;
        defaults().space(10);
        pad(10).top();

        addRow("Wood:", wood);
        addRow("Meat:", meat);
        addRow("Iron:", iron);
        addRow("Wool:", wool);
        addRow("Mead:", mead);
        addRow("Bread:", bread);
        addRow("Silver:", silver);
    }

    private void addRow(String name, Label value)
    {
        add(name).left().padRight(5);
        add(value).width(100).right().row();
    }

    @Override
    public void act(float delta)
    {
        ResourcesComponent res = Mappers.resources.get(entity);
        if (res != null)
        {
            wood.setText(String.format("%.0f", res.wood));
            meat.setText(String.format("%.0f", res.meat));
            iron.setText(String.format("%.0f", res.iron));
            wool.setText(String.format("%.0f", res.wool));
            mead.setText(String.format("%.0f", res.mead));
            bread.setText(String.format("%.0f", res.bread));
            silver.setText(String.format("%.0f", res.silver));
        }

        super.act(delta);
    }
}
