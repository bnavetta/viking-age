package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.ResourcesComponent;
import com.bennavetta.vikings.engine.components.TradeComponent;

public class TradeControl extends Table
{
    private final Entity entity;

    public TradeControl(Entity entity)
    {
        super(Assets.getSkin());
        this.entity = entity;

        TradeComponent trade = Mappers.trade.get(entity);
        if (trade == null)
        {
            throw new IllegalArgumentException("Non-trading entity");
        }

        add("Trade Valuation").colspan(2).row();
        addControl("Wood", trade, ResourcesComponent.WOOD);
        addControl("Meat", trade, ResourcesComponent.MEAT);
        addControl("Iron", trade, ResourcesComponent.IRON);
        addControl("Wool", trade, ResourcesComponent.WOOL);
        addControl("Mead", trade, ResourcesComponent.MEAD);
        addControl("Bread", trade, ResourcesComponent.BREAD);
        addControl("Silver", trade, ResourcesComponent.SILVER);
    }

    private void addControl(String name, TradeComponent trade, ResourcesComponent.ResourceHandle res)
    {
        Label label = new Label(name, Assets.getSkin());
        label.setAlignment(Align.left);
        add(label).pad(5);

        Slider slider = new Slider(0f, 1f, 0.01f, false, Assets.getSkin());
        slider.setValue(trade.value.get(res, 0));
        slider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                trade.value.put(res, slider.getValue());
            }
        });
        add(slider).pad(5).row();
    }
}
