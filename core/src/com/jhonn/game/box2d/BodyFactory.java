package com.jhonn.game.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BodyFactory {
    Actor actor;

    public BodyFactory(Actor actor) {
        this.actor = actor;
    }

    public Body createBox(World world, boolean isStatic) {


        BodyDef bodyDef = new BodyDef();

        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(new Vector2(actor.getX() + actor.getOriginX(), actor.getY() + actor.getOriginY()));

        Body body = world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();

        bodyShape.setAsBox(actor.getOriginX(), actor.getOriginY());

        body.createFixture(bodyShape, 0.0f);
        bodyShape.dispose();


        // fixture.setFriction(500);
        // fixture.setDensity(200);


        return body;
    }
}
