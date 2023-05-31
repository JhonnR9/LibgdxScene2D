package com.jhonn.game;

import com.badlogic.gdx.Game;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.screens.MainGame;

public class Scene2D extends Game {
	ResourceManager resourceManager = ResourceManager.getInstance();

	@Override
	public void create () {
		resourceManager.setDefaultSkin("graphics/skin/cloud-form-ui.json");
		resourceManager.setDefaultAtlas("graphics/textures/libgdx_scene2d.atlas");
		resourceManager.loadResources();
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
