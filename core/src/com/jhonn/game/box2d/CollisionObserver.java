package com.jhonn.game.box2d;

import com.badlogic.gdx.physics.box2d.Body;

public interface CollisionObserver {
    void beginContact(Body bodyA, Body bodyB);
    void endContact(Body bodyA, Body bodyB);
}
