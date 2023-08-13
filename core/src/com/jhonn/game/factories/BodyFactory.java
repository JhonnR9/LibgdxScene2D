package com.jhonn.game.factories;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Null;
import com.jhonn.game.entities.BaseActor;
import com.jhonn.game.utils.enums.BodyShape;
import com.jhonn.game.models.PhysicalModel;


public class BodyFactory {
    private PhysicalModel physicalModel;

    @Null
    public Body createBox(World world, BaseActor actor) {
        if (actor.getWidth() == 0) {
            return null;
        }

        physicalModel = actor.getPhysicalModel();

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = physicalModel.isStatic() ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        Vector2 bodyPosition = new Vector2(actor.getX() + actor.getOriginX(), actor.getY() + actor.getOriginY());

        bodyDef.position.set(new Vector2(bodyPosition).add(physicalModel.getBodyPositionOffset()));

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(physicalModel.isFixRotation());
        configureBoxShape(body, actor);

        body.setLinearDamping(physicalModel.getLinearDamping());
        body.setUserData(physicalModel.getBodyUserDate());


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
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.isSensor = physicalModel != null && physicalModel.isSensor();
        fixtureDef.density =  physicalModel != null ? physicalModel.getDensity(): 1f;

        bodyShape.setAsBox(size.x, size.y);
        fixtureDef.shape = bodyShape;
        body.createFixture(fixtureDef);
        bodyShape.dispose();

    }

    private void configureCircleShape(Body body, float radius) {
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape bodyShape = new CircleShape();

        fixtureDef.isSensor = physicalModel.isSensor();
        fixtureDef.density = physicalModel.getDensity();

        bodyShape.setRadius(radius);
        fixtureDef.shape = bodyShape;
        try {

            body.createFixture(fixtureDef);
        } finally {
            bodyShape.dispose();
        }
    }

}
