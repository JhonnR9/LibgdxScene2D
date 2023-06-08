package com.jhonn.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jhonn.game.actors.BaseActor;
import com.jhonn.game.utils.enums.BodyShape;

import java.awt.*;

/**
 * class to define physical aspect in BaseActor material and constants like velocity damping and others
 *
 * @see com.jhonn.game.actors.BaseActor
 */
public class PhysicalModel{
    private boolean isStatic = true;
    private Float linearDamping;
    private boolean isTrigger = false;
    private Vector2 size;
    private float density = .5f;
    private BodyShape bodyShape = BodyShape.RECTANGLE;
    private boolean isDestroyed = false;
    private BaseActor bodyUserDate;


    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public Float getLinearDamping() {
        return linearDamping;
    }

    public void setLinearDamping(Float linearDamping) {
        this.linearDamping = linearDamping;
    }

    public boolean isTrigger() {
        return isTrigger;
    }

    public void setTrigger(boolean trigger) {
        isTrigger = trigger;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public BodyShape getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(BodyShape bodyShape) {
        this.bodyShape = bodyShape;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public BaseActor getBodyUserDate() {
        return bodyUserDate;
    }

    public void setBodyUserDate(BaseActor bodyUserDate) {
        this.bodyUserDate = bodyUserDate;
    }


}
