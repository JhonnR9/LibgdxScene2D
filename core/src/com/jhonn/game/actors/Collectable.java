package com.jhonn.game.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.PhysicalModel;

public class Collectable extends BaseActor {
    public Collectable(float x, float y) {
        setPosition(x, y);
        Sprite sprite = new Sprite(ResourceManager.getInstance().getTexture("coletable.png"));
        setFrame(sprite);
        PhysicalModel physicalModel = getPhysicalModel();
        physicalModel.setStatic(false);
        physicalModel.setLinearDamping(3f);
        physicalModel.setBodyUserDate(this);
    }

    @Override
    public void beginContact(Body bodyA, Body bodyB) {

    }

    @Override
    public void endContact(Body bodyA, Body bodyB) {

    }

    public void collect() {
        destroy();
    }
}
