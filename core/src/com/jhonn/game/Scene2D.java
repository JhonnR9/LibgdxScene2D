package com.jhonn.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.jhonn.game.box2d.Box2dModel;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.screens.MainGame;

public class Scene2D extends Game {


	@Override
	public void create () {
		ResourceManager.getInstance().setDefaultSkin("skin/cloud-form-ui.json");
		ResourceManager.getInstance().loadResources();
		setScreen(new MainGame());
	}

	@Override
	public void render() {
		super.render();

	}

	@Override
	public void dispose() {
		super.dispose();
		ResourceManager.getInstance().dispose();
	}
}
