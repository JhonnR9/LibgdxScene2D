package com.jhonn.game.utils.Interfaces;

import com.badlogic.gdx.physics.box2d.Body;

public interface CollisionObserver {
    void beginContact(Body bodyA, Body bodyB);
    void endContact(Body bodyA, Body bodyB);
}
