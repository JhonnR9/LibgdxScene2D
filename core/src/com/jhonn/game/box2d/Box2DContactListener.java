package com.jhonn.game.box2d;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Box2DContactListener implements ContactListener {

    private Array<CollisionObserver> observers;

    public void setObservers(Array<CollisionObserver> observers) {
        this.observers = observers;
    }

    @Override
    public void beginContact(Contact contact) {
        if (observers == null) return;

        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        notifyBeginAll(bodyA, bodyB);

    }

    @Override
    public void endContact(Contact contact) {
        if (observers == null) return;

        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        notifyEndAll(bodyA, bodyB);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void notifyBeginAll(Body bodyA, Body bodyB) {
        Array.ArrayIterator<CollisionObserver> collisionObservers = new Array.ArrayIterator<>(observers);
        for (CollisionObserver observer : collisionObservers) {
            observer.beginContact(bodyA, bodyB);
        }
    }

    private void notifyEndAll(Body bodyA, Body bodyB) {
        Array.ArrayIterator<CollisionObserver> collisionObservers = new Array.ArrayIterator<>(observers);
        for (CollisionObserver observer : collisionObservers) {
            observer.endContact(bodyA, bodyB);
        }
    }
}
