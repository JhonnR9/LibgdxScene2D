package com.jhonn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jhonn.game.GameStage;
import com.jhonn.game.InputListener;
import com.jhonn.game.actors.BaseActor;

import com.jhonn.game.box2d.Box2dWorld;
import com.jhonn.game.utils.Interfaces.CollisionObserver;
import com.jhonn.game.models.DimensionModel;
import com.jhonn.game.utils.TopDownCamera;


public class BaseScreen implements Screen {
    private static final float VIEWPORT_WIDTH = 16.0f;
    private static final float VIEWPORT_HEIGHT = 9.0f;
    private static final float UI_SCALE = 30.0f;
    protected final GameStage stage;
    protected final GameStage uiStage;
    protected final Box2dWorld box2DWorld = new Box2dWorld();
    protected final TopDownCamera topDownCamera;
    private final InputMultiplexer inputMultiplexer;
    protected final InputListener inputListener;

    public BaseScreen() {
        topDownCamera = new TopDownCamera();
        stage = new GameStage(new ExtendViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, topDownCamera), box2DWorld);

        Viewport uiViewport = new FillViewport(VIEWPORT_WIDTH * UI_SCALE, VIEWPORT_HEIGHT * UI_SCALE);
        uiStage = new GameStage(uiViewport);

        inputMultiplexer = new InputMultiplexer();
        inputListener = new InputListener();
    }

    @Override
    public void show() {
        stage.clear();
        uiStage.clear();

        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(inputListener);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        box2DWorld.update(delta);
        stage.act();
        stage.draw();
        uiStage.act();
        uiStage.draw();
        box2DWorld.render(stage.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width,height,true);
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
        box2DWorld.dispose();
    }



}
