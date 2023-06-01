package com.jhonn.game.fatories;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Null;
import com.jhonn.game.actors.BaseActor;
import com.jhonn.game.utils.enums.BodyShape;
import com.jhonn.game.models.DimensionModel;
import com.jhonn.game.models.PhysicalModel;


public class BodyFactory {
    @Null
    public Body createBox(World world, BaseActor actor) {
        if (actor.getWidth() == 0) return null;

        PhysicalModel physicalModel = actor.getPhysicalModel();

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = physicalModel.isStatic() ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(new Vector2(actor.getX() + actor.getOriginX(), actor.getY() + actor.getOriginY()));

        Body body = world.createBody(bodyDef);

        configureBoxShape(body, actor);

        if (physicalModel.getLinearDamping() != null) {
            body.setLinearDamping(physicalModel.getLinearDamping());
        }
        if (physicalModel.getBodyUserDate() != null) {
            body.setUserData(physicalModel.getBodyUserDate());
        }
        return body;

    }

    @Null
    public void createBox(World world, Rectangle rectangle) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(rectangle.x, rectangle.y));


        Body body = world.createBody(bodyDef);
        configureBoxShape(body, new Vector2(rectangle.width, rectangle.height));

    }

    private void configureBoxShape(Body body, BaseActor actor) {

        PhysicalModel physicalModel = actor.getPhysicalModel();
        Vector2 size = physicalModel.getSize();

        float width = size != null ? size.x * actor.getScaleX() : actor.getOriginX() * actor.getScaleX();
        float height = size != null ? size.y * actor.getScaleY() : actor.getOriginY() * actor.getScaleY();

        Vector2 actorSize = new Vector2(width, height);

        if (physicalModel.getBodyShape() == BodyShape.RECTANGLE) {
            configureBoxShape(body, actorSize);
        } else if (physicalModel.getBodyShape() == BodyShape.CIRCLE) {
            float radius = (actor.getWidth() / 2) * actor.getScaleX();
            configureCircleShape(body, radius);
        }

    }

    private void configureBoxShape(Body body, Vector2 size) {
        PolygonShape bodyShape = new PolygonShape();
        try {
            bodyShape.setAsBox(size.x, size.y);
            body.createFixture(bodyShape, 0.0f);
        } finally {
            bodyShape.dispose();
        }
    }

    private void configureCircleShape(Body body, float radius) {
        CircleShape bodyShape = new CircleShape();
        try {
            bodyShape.setRadius(radius);
            body.createFixture(bodyShape, 0.0f);
        } finally {
            bodyShape.dispose();
        }
    }

}
