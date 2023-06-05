package com.jhonn.game.actors.items;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.jhonn.game.actors.BaseActor;
import com.jhonn.game.actors.Player;


public abstract class BaseItem extends BaseActor {
    protected final int id;
    public BaseItem(float x, float y, int id,String name) {
        this.id = id;
        this.setName(name);
        setPosition(x, y);

        physicalModel.setStatic(true);
        physicalModel.setBodyUserDate(this);

    }


    @Override
    public void beginContact(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() == null || bodyB.getUserData() == null) return;

        if (ClassReflection.isInstance(Player.class, bodyA.getUserData()) &&
                ClassReflection.isInstance(BaseItem.class, bodyB.getUserData())) {

            BaseItem baseItem = (BaseItem) bodyB.getUserData();
            if (baseItem == this){
                Player player = (Player) bodyA.getUserData();
                baseItem.collect(player);
            }


        } else if (ClassReflection.isInstance(BaseItem.class, bodyA.getUserData()) &&
                ClassReflection.isInstance(Player.class, bodyB.getUserData())) {
            BaseItem baseItem = (BaseItem) bodyA.getUserData();
            if (baseItem == this){
                Player player = (Player) bodyB.getUserData();
                baseItem.collect(player);
            }


        }
    }

    @Override
    public void endContact(Body bodyA, Body bodyB) {

    }


    protected void collect(Player player) {
        remove();
        player.setLabelCollected(player.getLabelCollected() + 1);
        setDestroyed(true);

    }
}
