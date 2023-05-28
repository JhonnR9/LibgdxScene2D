package com.jhonn.game.actors.physical.box2d;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Null;
import com.jhonn.game.actors.BaseActor;
import com.jhonn.game.actors.PhysicalModel;
import com.jhonn.game.utils.Dimension;


public class BodyFactory {

    @Null
    public Body createBox(World world, BaseActor actor) {

        if (actor.getWidth() == 0) return null;

        PhysicalModel physicalModel = actor.getPhysicalModel();

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = physicalModel.getIsStatic() ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(actor.getX() + actor.getOriginX(), actor.getY() + actor.getOriginY()));

        Body body = world.createBody(bodyDef);

        configureBoxShape(body, actor);

        if (physicalModel.getLinearDamping() != null) {
            body.setLinearDamping(physicalModel.getLinearDamping());
        }

        return body;

    }
    @Null
    public Body createBox(World world, Rectangle rectangle) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(rectangle.x, rectangle.y));

        Body body = world.createBody(bodyDef);
        configureBoxShape(body, new Dimension(rectangle.width, rectangle.height));

        return body;

    }

    private void configureBoxShape(Body body, BaseActor actor) {
        Dimension actorSize = new Dimension(actor.getOriginX() * actor.getScaleX(), actor.getOriginY() * actor.getScaleY());
        configureBoxShape(body, actorSize);
    }

    private void configureBoxShape(Body body, Dimension size) {
        PolygonShape bodyShape = new PolygonShape();
        try {
            bodyShape.setAsBox(size.getWidth(), size.getHeight());
            body.createFixture(bodyShape, 0.0f);
        } finally {
            bodyShape.dispose();
        }
    }

}
