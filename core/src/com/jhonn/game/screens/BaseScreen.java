package com.jhonn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jhonn.game.actors.BaseActor;
import com.jhonn.game.actors.physical.box2d.Box2dModel;
import com.jhonn.game.actors.physical.box2d.BodyFactory;

import static com.jhonn.game.constants.GameConst.toUnits;

public class BaseScreen implements Screen {
    private static final float VIEWPORT_WIDTH = 16.0f;
    private static final float VIEWPORT_HEIGHT = 9.0f;
    private final Stage stage = new Stage(new ExtendViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
    private Color backgroundColor = new Color(.1f, .1f, .1f, 1);
    private final Box2dModel box2dModel = new Box2dModel();

    public void setBackgroundColor(Color backgrundColor) {
        this.backgroundColor = backgrundColor;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
        stage.clear();

    }

    public void createBodies() {
        Array.ArrayIterator<Actor> actors = new Array.ArrayIterator<>(stage.getActors());

        Class<? extends Actor> baseActorClass = BaseActor.class;
        Array<BaseActor> filteredActors = new Array<>();

        for (Actor actor : actors) {
            if (ClassReflection.isAssignableFrom(baseActorClass, actor.getClass())) {
                BaseActor baseActor = (BaseActor) actor;
                filteredActors.add(baseActor);
            }

        }

        Array.ArrayIterator<BaseActor> filteredActorsI = new Array.ArrayIterator<>(filteredActors);

        for (BaseActor actor : filteredActorsI) {
            BodyFactory bodyFactory = new BodyFactory();
            if (actor.getPhysicalModel().getIsStatic() != null) {
                Body body = bodyFactory.createBox(box2dModel.getWorld(),actor);
                actor.getPhysicalModel().setBody(body);
            }

        }


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        box2dModel.update(delta);
        stage.act();
        stage.draw();
        box2dModel.render(stage.getCamera().combined);
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
        box2dModel.dispose();
    }

    public void centerCameraActor(Actor actor, float mapWidth, float mapHeight) {
        Camera camera = stage.getCamera();

        float actorCenterX = actor.getX() + actor.getWidth() / 2;
        float actorCenterY = actor.getY() + actor.getHeight() / 2;

        float minX = camera.viewportWidth / 2;
        float maxX = toUnits(mapWidth) - (camera.viewportWidth / 2);
        float minY = camera.viewportHeight / 2;
        float maxY = toUnits(mapHeight) - (camera.viewportHeight / 2);

        float newCameraX = MathUtils.clamp(actorCenterX, minX, maxX);
        float newCameraY = MathUtils.clamp(actorCenterY, minY, maxY);

        camera.position.set(newCameraX, newCameraY, 0);
        camera.update();
    }

}
