package com.jhonn.game.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.jhonn.game.actors.BaseActor;
import com.jhonn.game.actors.Player;

/**
 * class to define physical aspect in BaseActor material and constants like velocity damping and others
 *
 * @see com.jhonn.game.actors.BaseActor
 */
public class PhysicalModel {
    private boolean isStatic = true;
    private Float linearDamping;
    private boolean isTrigger = false;
    private boolean isDestroyed = false;

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    private BaseActor bodyUserDate;

    public BaseActor getBodyUserDate() {
        return bodyUserDate;
    }

    public void setBodyUserDate(BaseActor bodyUserDate) {
        this.bodyUserDate = bodyUserDate;
    }

    /**
     * @return it true if you want the body to respond like a trigger
     */
    public boolean isTrigger() {
        return isTrigger;
    }

    /**
     * @param trigger set it true if you want the body to respond like a trigger
     */
    public void setTrigger(boolean trigger) {
        isTrigger = trigger;
    }

    private Body body;


    public Boolean getIsStatic() {
        return isStatic;
    }

    public void setStatic(Boolean aStatic) {
        isStatic = aStatic;
    }

    public Float getLinearDamping() {
        return linearDamping;
    }

    public void setLinearDamping(Float linearDamping) {
        this.linearDamping = linearDamping;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
