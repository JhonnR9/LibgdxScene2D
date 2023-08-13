package com.jhonn.game.models;

import com.badlogic.gdx.math.Vector2;
import com.jhonn.game.entities.BaseActor;
import com.jhonn.game.utils.enums.BodyShape;

/**
 * class to define physical aspect in BaseActor material and constants like velocity damping and others
 *
 * @see com.jhonn.game.entities.BaseActor
 */
public class PhysicalModel {
    private boolean isStatic = true;
    private float linearDamping;
    private boolean isSensor = false;
    private Vector2 size;
    private float density = .5f;
    private BodyShape bodyShape = BodyShape.RECTANGLE;
    private BaseActor bodyUserDate;

    private boolean fixRotation = false;

    public boolean isFixRotation() {
        return fixRotation;
    }

    public void setFixRotation(boolean fixRotation) {
        this.fixRotation = fixRotation;
    }

    private Vector2 bodyPositionOffset = new Vector2();

    public Vector2 getBodyPositionOffset() {
        return bodyPositionOffset;
    }

    public void setBodyPositionOffset(Vector2 bodyPositionOffset) {
        this.bodyPositionOffset = bodyPositionOffset;
    }
    public void setBodyPositionOffsetX(float xOffset){
        this.bodyPositionOffset.x = xOffset;
    }
    public void setBodyPositionOffsetY(float yOffset){
        this.bodyPositionOffset.y = yOffset;
    }

    public PhysicalModel(BaseActor parent) {
        this.bodyUserDate = parent;
    }

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

    public boolean isSensor() {
        return isSensor;
    }

    public void setSensor(boolean sensor) {
        isSensor = sensor;
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

    public BaseActor getBodyUserDate() {
        return bodyUserDate;
    }

    public void setBodyUserDate(BaseActor bodyUserDate) {
        this.bodyUserDate = bodyUserDate;
    }


}
