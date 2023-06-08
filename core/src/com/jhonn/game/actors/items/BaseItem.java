package com.jhonn.game.actors.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.jhonn.game.actors.BaseActor;
import com.jhonn.game.actors.Player;


public abstract class BaseItem extends BaseActor {
    protected final int id;

    public BaseItem(float x, float y, int id, String name) {
        this.id = id;
        this.setName(name);
        setPosition(x, y);

        physicalModel.setStatic(false);
        physicalModel.setBodyUserDate(this);
        physicalModel.setTrigger(true);

    }

    public int getId() {
        return id;
    }

    @Override
    public void beginContact(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() == null || bodyB.getUserData() == null) return;

        if (ClassReflection.isInstance(Player.class, bodyA.getUserData()) &&
                ClassReflection.isInstance(BaseItem.class, bodyB.getUserData())) {

            BaseItem baseItem = (BaseItem) bodyB.getUserData();
            if (baseItem == this) {
                Player player = (Player) bodyA.getUserData();
                BaseItem item = (BaseItem) bodyB.getUserData();
                baseItem.collect(player, item);
            }

        } else if (ClassReflection.isInstance(BaseItem.class, bodyA.getUserData()) &&
                ClassReflection.isInstance(Player.class, bodyB.getUserData())) {
            BaseItem baseItem = (BaseItem) bodyA.getUserData();
            if (baseItem == this) {
                Player player = (Player) bodyB.getUserData();
                BaseItem item = (BaseItem) bodyA.getUserData();
                baseItem.collect(player, item);
            }
        }
    }

    @Override
    public void endContact(Body bodyA, Body bodyB) {

    }

    protected void collect(Player player, BaseItem item) {
        player.getInventory().addItem(item, player.getInventory().getItemQuantity(item.id) + 1);
        setDestroyed(true);
        setVisible(false);

    }

}
