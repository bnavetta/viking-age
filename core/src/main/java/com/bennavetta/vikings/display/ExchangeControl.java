package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.IdentityComponent;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.ResourcesComponent;

/**
 * Control for transferring resources between entities
 */
public class ExchangeControl extends Table
{
    private final Entity a;
    private final Entity b;

    public ExchangeControl(Entity a, Entity b)
    {
        super(Assets.getSkin());

        if (!Mappers.resources.has(a) || !Mappers.resources.has(b))
        {
            throw new IllegalArgumentException("Missing resources component");
        }

        this.a = a;
        this.b = b;

        pad(20);
        defaults().spaceLeft(10).spaceRight(10);

        IdentityComponent aId = Mappers.id.get(a);
        IdentityComponent bId = Mappers.id.get(b);
        add(String.format("%s <-> %s", aId.name, bId.name), "default-font", Color.BLUE).colspan(5).pad(5).row();

        configureResource("Wood", ResourcesComponent.WOOD);
        configureResource("Meat", ResourcesComponent.MEAT);
        configureResource("Iron", ResourcesComponent.IRON);
        configureResource("Wool", ResourcesComponent.WOOL);
        configureResource("Mead", ResourcesComponent.MEAD);
        configureResource("Bread", ResourcesComponent.BREAD);
        configureResource("Silver", ResourcesComponent.SILVER);
    }

    private void configureResource(String label, ResourcesComponent.ResourceHandle res)
    {
        configureLabel(add("0").width(60).getActor(), a, res);
        configureTransferButton(add(new TextButton("<-", Assets.getSkin())).width(50).getActor(), b, a, res);
        add(label);
        configureTransferButton(add(new TextButton("->", Assets.getSkin())).width(50).getActor(), a, b, res);
        configureLabel(add("0").width(60).getActor(), b, res);
        row();
    }

    private void configureLabel(Label label, Entity entity, ResourcesComponent.ResourceHandle res)
    {
        label.setAlignment(Align.right);
        label.addAction(new Action()
        {
            @Override
            public boolean act(float delta)
            {
                label.setText(String.format("%.2f", res.get(Mappers.resources.get(entity))));
                return false;
            }
        });
    }

    private void configureTransferButton(Button button, Entity from, Entity to, ResourcesComponent.ResourceHandle res)
    {
        ResourcesComponent fromRes = Mappers.resources.get(from);
        ResourcesComponent toRes = Mappers.resources.get(to);

        button.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                float fromAmount = res.get(fromRes);
                float toAmount = res.get(toRes);

                float rate = 0;
                if (fromAmount >= 1000)
                {
                    rate = 200;
                }
                else if (fromAmount >= 500)
                {
                    rate = 100;
                }
                else if (fromAmount >= 100)
                {
                    rate = 20;
                }
                else if (fromAmount >= 50)
                {
                    rate = 10;
                }
                else if (fromAmount >= 20)
                {
                    rate = 5;
                }
                else
                {
                    rate = 1;
                }

                if (fromAmount >= rate)
                {
                    res.set(fromRes, fromAmount - rate);
                    res.set(toRes, toAmount + rate);
                }
            }
        });
    }
}
