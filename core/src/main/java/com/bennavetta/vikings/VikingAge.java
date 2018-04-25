package com.bennavetta.vikings;

import com.badlogic.gdx.Game;
import com.bennavetta.vikings.screens.LoadingScreen;
import com.bennavetta.vikings.screens.MainScreen;

public class VikingAge extends Game
{
	@Override
	public void create ()
	{
		Assets.init();

		this.setScreen(new LoadingScreen(Assets.getManager(), this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
