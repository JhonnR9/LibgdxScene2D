package com.jhonn.game.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.enums.BodyShape;

public class Collectable extends BaseActor {
    public Collectable(float x, float y) {
        setPosition(x, y);
        Sprite sprite = new Sprite(ResourceManager.getInstance().getDefaultAtlas().findRegion("coletable"));
        setFrame(sprite);
        setScale(.5f);

        physicalModel.setStatic(false);
        physicalModel.setLinearDamping(3f);
        physicalModel.setBodyUserDate(this);
        physicalModel.setBodyShape(BodyShape.CIRCLE);
    }

    @Override
    public void beginContact(Body bodyA, Body bodyB) {

    }

    @Override
    public void endContact(Body bodyA, Body bodyB) {

    }


    public void collect() {
        remove();
        setDestroyed(true);

    }
}
