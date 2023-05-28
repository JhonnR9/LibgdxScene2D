package com.jhonn.game.box2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.jhonn.game.actors.BaseActor;


public class Box2dModel implements Disposable {
    private static final Vector2 GRAVITY = new Vector2(0, 0);
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    private static final float PPM = 16f;
    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer(
            true, true, true, true, true, true
    );
    private final World world = new World(GRAVITY, true);

    public Box2DContactListener getB2DContactListener() {
        return b2DContactListener;
    }

    private final Box2DContactListener b2DContactListener = new Box2DContactListener();

    private float accumulator = 0;

    public Box2dModel() {
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

        Array.ArrayIterator<Body> bodyArrayIterator = new Array.ArrayIterator<>(bodies);

        for (Body body : bodyArrayIterator) {
            if (body.getUserData() != null) {
                if (ClassReflection.isAssignableFrom(BaseActor.class, body.getUserData().getClass())) {
                    BaseActor baseActor = (BaseActor) body.getUserData();
                    if (baseActor.getPhysicalModel().isDestroyed()) {
                        world.destroyBody(body);
                    }
                }

            }
        }
    }


    @Override
    public void dispose() {
        world.dispose();
    }
}
