package com.jhonn.game.actors;

import com.badlogic.gdx.physics.box2d.Body;

public class PhysicalModel {
    private Boolean isStatic;
    private Float linearDamping;
    private Body body;

    public Boolean getIsStatic() {
        return isStatic;
    }

    public PhysicalModel setStatic(Boolean aStatic) {
        isStatic = aStatic;
        return this;
    }

    public Float getLinearDamping() {
        return linearDamping;
    }

    public PhysicalModel setLinearDamping(Float linearDamping) {
        this.linearDamping = linearDamping;
        return this;
    }

    public Body getBody() {
        return body;
    }

    public PhysicalModel setBody(Body body) {
        this.body = body;
        return this;
    }
}
