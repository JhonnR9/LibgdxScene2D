package com.jhonn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jhonn.game.actors.Player;
import com.jhonn.game.box2d.Box2dModel;
import com.jhonn.game.tilemap.TilemapHandle;


public final class MainGame implements Screen {
    private static final float WORLD_WIDTH = 6.4f;
    private static final float WORLD_HEIGHT = 4.8f;
    private final Stage stage = new Stage(new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT));
    private final Box2dModel box2dModel = new Box2dModel();


    @Override
    public void show() {

        stage.clear();


        Player player = new Player(box2dModel.getWorld(),5,5);
        TilemapHandle tilemapHandle = new TilemapHandle("RAW/main.tmx");

        player.setMapSize(tilemapHandle.getWidth(), tilemapHandle.getHeight());



        stage.addActor(tilemapHandle);
        stage.addActor(player);
        stage.addActor(box2dModel);


        stage.setDebugUnderMouse(true);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
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
