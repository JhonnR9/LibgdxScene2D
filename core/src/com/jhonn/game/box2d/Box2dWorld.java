package com.jhonn.game.box2d;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.jhonn.game.actors.BaseActor;
import com.jhonn.game.fatories.BodyFactory;
import com.jhonn.game.actors.tilemap.TilemapHandle;


public class Box2dWorld implements Disposable {
    private static final Vector2 GRAVITY = new Vector2(0, 0);
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    private static final float PPM = 16f;
    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer(
            true, true, false, false, false, false
    );
    private final World world = new World(GRAVITY, true);
    public Box2DContactListener getB2DContactListener() {
        return b2DContactListener;
    }

    private final Box2DContactListener b2DContactListener = new Box2DContactListener();

    private float accumulator = 0;

    public Box2dWorld() {
        world.setContactListener(b2DContactListener);
    }

    public World getWorld() {
        return world;
    }

    private void doPhysicsStep(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }

    }

    public static float toUnits(float value) {
        return value / PPM;
    }


    public void update(float delta) {
        doPhysicsStep(delta);
        destroyUnusedBodies();

    }

    public void render(Matrix4 projMatrix) {
        box2DDebugRenderer.render(world, projMatrix);
    }

    private void destroyUnusedBodies() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

        Array<Body> bodiesToDestroy = new Array<>();

        Array.ArrayIterator<Body> bodyArrayIterator = new Array.ArrayIterator<>(bodies);

        for (Body body : bodyArrayIterator) {
            Object userData = body.getUserData();
            if (userData != null && ClassReflection.isAssignableFrom(BaseActor.class, userData.getClass())) {
                BaseActor baseActor = (BaseActor) userData;
                if (baseActor.isDestroyed()) {
                    b2DContactListener.removeObserver(baseActor);
                    bodiesToDestroy.add(body);
                }
            }
        }

        Array.ArrayIterator<Body> bodyToDestroyArrayIterator = new Array.ArrayIterator<>(bodiesToDestroy);
        for (Body body : bodyToDestroyArrayIterator) {
            world.destroyBody(body);
        }
    }


    public void createTileColliders(TilemapHandle tile) {
        BodyFactory bodyFactory = new BodyFactory();
        if (tile.getRectanglesColliders() == null) return;

        Array.ArrayIterator<Rectangle> rectangles = tile.getRectanglesColliders();
        for (Rectangle rectangle : rectangles) {
            bodyFactory.createBox(world, rectangle);
        }
    }

    public void createBodies(Stage stage) {
        Array.ArrayIterator<Actor> actors = new Array.ArrayIterator<>(stage.getActors());

        Class<? extends Actor> baseActorChild = BaseActor.class;
        Array<BaseActor> filteredActors = new Array<>();

        for (Actor actor : actors) {
            if (ClassReflection.isAssignableFrom(baseActorChild, actor.getClass())) {
                BaseActor baseActor = (BaseActor) actor;
                filteredActors.add(baseActor);
            }

        }

        Array.ArrayIterator<BaseActor> filteredActorsI = new Array.ArrayIterator<>(filteredActors);
        BodyFactory bodyFactory = new BodyFactory();

        for (BaseActor actor : filteredActorsI) {
            Body body = bodyFactory.createBox(world, actor);
            actor.getPhysicalModel().setBody(body);

        }


    }


    @Override
    public void dispose() {
        world.dispose();
    }
}
