package com.bennavetta.vikings.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bennavetta.vikings.Assets;

/**
 * Shown while the game is loading
 */
public class LoadingScreen implements Screen
{
    private final Stage stage;
    private final ProgressBar progress;
    private final AssetManager manager;
    private final Game game;

    public LoadingScreen(AssetManager manager, Game game)
    {
        this.manager = manager;
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label label = new Label("Loading...", Assets.getSkin());
        root.add(label).center().pad(10);

        progress = new ProgressBar(0, 100, 1, false, Assets.getSkin());
        root.add(progress).center().pad(10);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        assert game.getScreen() == this;

        if (manager.update())
        {
            game.setScreen(new MainScreen());
        }

        progress.setValue(manager.getProgress() * 100);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
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
