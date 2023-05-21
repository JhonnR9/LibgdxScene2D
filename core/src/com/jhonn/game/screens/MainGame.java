package com.jhonn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jhonn.game.actors.Player;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.tilemap.TilemapHandle;


public final class MainGame implements Screen {
    private final Stage stage;


    public MainGame() {
        float worldWidth = 6.4f;
        float worldHeight = 4.8f;
        stage = new Stage(new ExtendViewport(worldWidth, worldHeight));

    }

    @Override
    public void show() {

        stage.clear();

        TilemapHandle tilemapHandle = new TilemapHandle("RAW/main.tmx");
        Player player = new Player();
        player.setPosition(2, 2);
        player.setTileMapWidth(tilemapHandle.getWidth());
        player.setTileMapHeight(tilemapHandle.getHeight());


        stage.addActor(tilemapHandle);
        stage.addActor(player);
        stage.setDebugUnderMouse(true);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .3f, .0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
