package com.bennavetta.vikings.display;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.bennavetta.vikings.Assets;
import com.bennavetta.vikings.engine.components.Mappers;
import com.bennavetta.vikings.engine.components.PopulationComponent;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class PopulationControl extends Table
{
    private Entity entity;

    private final Label shepardLabel = new Label("0", Assets.getSkin());
    private final Button shepardInc = new TextButton("+", Assets.getSkin());
    private final Button shepardDec = new TextButton("-", Assets.getSkin());

    private final Label smelterLabel = new Label("0", Assets.getSkin());
    private final Button smelterInc = new TextButton("+", Assets.getSkin());
    private final Button smelterDec = new TextButton("-", Assets.getSkin());

    private final Label foresterLabel = new Label("0", Assets.getSkin());
    private final Button foresterInc = new TextButton("+", Assets.getSkin());
    private final Button foresterDec = new TextButton("-", Assets.getSkin());

    private final Label cattleFarmerLabel = new Label("0", Assets.getSkin());
    private final Button cattleFarmerInc = new TextButton("+", Assets.getSkin());
    private final Button cattleFarmerDec = new TextButton("-", Assets.getSkin());

    private final Label wheatFarmerLabel = new Label("0", Assets.getSkin());
    private final Button wheatFarmerInc = new TextButton("+", Assets.getSkin());
    private final Button wheatFarmerDec = new TextButton("-", Assets.getSkin());
             
    private final Label brewerLabel = new Label("0", Assets.getSkin());
    private final Button brewerInc = new TextButton("+", Assets.getSkin());
    private final Button brewerDec = new TextButton("-", Assets.getSkin());

    private Label unassignedLabel = new Label("0", Assets.getSkin());

    public PopulationControl(Entity entity)
    {
        super(Assets.getSkin());

        if (!Mappers.population.has(entity))
        {
            throw new IllegalArgumentException("Entity does not have a population");
        }

        this.entity = entity;

        defaults().space(10);
        pad(10);

        shepardLabel.setAlignment(Align.right);
        smelterLabel.setAlignment(Align.right);
        foresterLabel.setAlignment(Align.right);
        cattleFarmerLabel.setAlignment(Align.right);
        wheatFarmerLabel.setAlignment(Align.right);
        brewerLabel.setAlignment(Align.right);
        unassignedLabel.setAlignment(Align.right);

        add(new Label("Foresters:", Assets.getSkin())).left().padRight(5);
        add(foresterLabel).width(100);
        add(foresterInc).width(50);
        add(foresterDec).width(50).row();

        add(new Label("Cattle Farmers:", Assets.getSkin())).left().padRight(5);
        add(cattleFarmerLabel).width(100);
        add(cattleFarmerInc).width(50);
        add(cattleFarmerDec).width(50).row();

        add(new Label("Smelters:", Assets.getSkin())).left().padRight(5);
        add(smelterLabel).width(100);
        add(smelterInc).width(50);
        add(smelterDec).width(50).row();

        add(new Label("Shepards:", Assets.getSkin())).left().padRight(5);
        add(shepardLabel).width(100);
        add(shepardInc).width(50);
        add(shepardDec).width(50).row();

        add(new Label("Brewers:", Assets.getSkin())).left().padRight(5);
        add(brewerLabel).width(100);
        add(brewerInc).width(50);
        add(brewerDec).width(50).row();

        add(new Label("Wheat Farmers:", Assets.getSkin())).left().padRight(5);
        add(wheatFarmerLabel).width(100);
        add(wheatFarmerInc).width(50);
        add(wheatFarmerDec).width(50).row();

        add(new Label("Idle:", Assets.getSkin())).left().padRight(5);
        add(unassignedLabel).width(100).row();

        addAction(new UpdateAction());

        PopulationComponent pop = Mappers.population.get(entity);
        configureButtons(shepardInc, shepardDec, delta -> pop.shepherds += delta, () -> pop.shepherds);
        configureButtons(smelterInc, smelterDec, delta -> pop.smelters += delta, () -> pop.smelters);
        configureButtons(foresterInc, foresterDec, delta -> pop.foresters += delta, () -> pop.foresters);
        configureButtons(cattleFarmerInc, cattleFarmerDec, delta -> pop.cattleFarmers += delta, () -> pop.cattleFarmers);
        configureButtons(wheatFarmerInc, wheatFarmerDec, delta -> pop.wheatFarmers += delta, () -> pop.wheatFarmers);
        configureButtons(brewerInc, brewerDec, delta -> pop.brewers += delta, () -> pop.brewers);
    }


    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    private class UpdateAction extends Action
    {
        @Override
        public boolean act(float delta)
        {
            PopulationComponent pop = Mappers.population.get(entity);

            shepardLabel.setText(Integer.toString(pop.shepherds));
            smelterLabel.setText(Integer.toString(pop.smelters));
            foresterLabel.setText(Integer.toString(pop.foresters));
            cattleFarmerLabel.setText(Integer.toString(pop.cattleFarmers));
            wheatFarmerLabel.setText(Integer.toString(pop.wheatFarmers));
            brewerLabel.setText(Integer.toString(pop.brewers));
            unassignedLabel.setText(Integer.toString(pop.unassigned));

            if (pop.unassigned <= 0)
            {
                shepardInc.setDisabled(true);
                smelterInc.setDisabled(true);
                foresterInc.setDisabled(true);
                cattleFarmerInc.setDisabled(true);
                wheatFarmerInc.setDisabled(true);
                brewerInc.setDisabled(true);
            }
            else
            {
                shepardInc.setDisabled(false);
                smelterInc.setDisabled(false);
                foresterInc.setDisabled(false);
                cattleFarmerInc.setDisabled(false);
                wheatFarmerInc.setDisabled(false);
                brewerInc.setDisabled(false);
            }

            return false;
        }
    }

    /**
     * Helper to add the button listeners
     * @param inc the increment button
     * @param dec the decrement button
     * @param updater a function that applies the delta to the population component
     * @param getter a function to return the current value of the population component
     */
    private void configureButtons(Button inc, Button dec, IntConsumer updater, IntSupplier getter)
    {
        PopulationComponent pop = Mappers.population.get(entity);

        inc.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                pop.unassigned--;
                updater.accept(1);
            }
        });

        dec.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                pop.unassigned++;
                updater.accept(-1);
            }
        });

        dec.addAction(new Action()
        {
            @Override
            public boolean act(float delta)
            {
                dec.setDisabled(getter.getAsInt() <= 0);
                return false;
            }
        });
    }
}
