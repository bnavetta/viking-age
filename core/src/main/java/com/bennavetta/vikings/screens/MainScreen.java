package com.bennavetta.vikings.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bennavetta.vikings.ShipName;
import com.bennavetta.vikings.display.ChieftainUI;
import com.bennavetta.vikings.engine.components.*;
import com.bennavetta.vikings.engine.data.MapData;
import com.bennavetta.vikings.engine.data.MapLoader;
import com.bennavetta.vikings.engine.systems.*;

public class MainScreen implements Screen
{
    private Stage stage;

    private final PooledEngine engine;

    public MainScreen()
    {
        stage = new Stage(new FitViewport(1200, 800));


        engine = new PooledEngine();
        engine.addSystem(new WorkerSystem(1f));
        engine.addSystem(new GrowthSystem(1f));
        engine.addSystem(new NavigationSystem());
        engine.addSystem(new RaidingSystem(1f));
        engine.addSystem(new EnemySystem());
        engine.addSystem(new SteeringSystem());
        engine.addSystem(new TradeSystem(2f));
        engine.addSystem(new RestockSystem());

        ChieftainUI gameUI = new ChieftainUI(engine);
        stage.getRoot().addActor(gameUI);
        engine.addEntityListener(gameUI.getMap());

        MapLoader.apply(MapData.load(Gdx.files.internal("map.json")), engine);

        Entity oseberg = Entities.newCentralPlace(engine, "Oseberg", 490, 331);
        PopulationComponent pop = Mappers.population.get(oseberg);
        pop.shepherds = 2;
        pop.brewers = 2;
        pop.foresters = 2;
        pop.wheatFarmers = 2;
        pop.smelters = 2;
        pop.cattleFarmers = 2;
        engine.addEntity(oseberg);

        Entity ship = Entities.newRaidingShip(engine, ShipName.generate(), CombatComponent.Affinity.PLAYER, 490, 315);
        ship.add(engine.createComponent(TradeComponent.class));
        engine.addEntity(ship);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        engine.update(delta);
        stage.act(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
