package com.jhonn.game.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.enums.BodyShape;

public class Collectable extends BaseActor {
    public Collectable(float x, float y) {

        setPosition(x, y);
        Sprite sprite = new Sprite(ResourceManager.getInstance().getDefaultAtlas().findRegion("coletable"));
        setFrame(sprite);
        setScale(.5f);

        physicalModel.setStatic(true);
        physicalModel.setLinearDamping(3f);
        physicalModel.setBodyUserDate(this);
        physicalModel.setBodyShape(BodyShape.CIRCLE);
    }


    @Override
    public void beginContact(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() == null || bodyB.getUserData() == null) return;

        if (ClassReflection.isInstance(Player.class, bodyA.getUserData()) &&
                ClassReflection.isInstance(Collectable.class, bodyB.getUserData())) {

            Collectable collectable = (Collectable) bodyB.getUserData();
            if (collectable == this){
                Player player = (Player) bodyA.getUserData();
                collectable.collect(player);
            }


        } else if (ClassReflection.isInstance(Collectable.class, bodyA.getUserData()) &&
                ClassReflection.isInstance(Player.class, bodyB.getUserData())) {
            Collectable collectable = (Collectable) bodyA.getUserData();
            if (collectable == this){
                Player player = (Player) bodyB.getUserData();
                collectable.collect(player);
            }


        }
    }

    @Override
    public void endContact(Body bodyA, Body bodyB) {

    }


    private void collect(Player player) {
        remove();
        player.setLabelCollected(player.getLabelCollected() + 1);
        setDestroyed(true);

    }
}
