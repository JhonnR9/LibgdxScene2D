package com.jhonn.game.actors.physical.box2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;


public class Box2dModel implements Disposable {
    private static final Vector2 GRAVITY = new Vector2(0, 0);
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;


    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer(
            true, true, true, true, true, true
    );
    private final World world = new World(GRAVITY, true);

    private float accumulator = 0;


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


    public void update(float delta) {
        doPhysicsStep(delta);
    }


    public void render(Matrix4 projMatrix) {
        box2DDebugRenderer.render(world, projMatrix);
    }


    @Override
    public void dispose() {
        world.dispose();
    }
}
