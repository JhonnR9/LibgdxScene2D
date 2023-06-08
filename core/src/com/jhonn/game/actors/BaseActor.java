package com.jhonn.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jhonn.game.utils.Interfaces.CollisionObserver;
import com.jhonn.game.models.PhysicalModel;

import static com.jhonn.game.box2d.Box2dWorld.toUnits;

/**
 * class base for create actors
 * yours actors can use physical
 */
public abstract class BaseActor extends Actor implements CollisionObserver {
    private final Sprite frame = new Sprite();
    protected final PhysicalModel physicalModel = new PhysicalModel();
    private Body body;


    public PhysicalModel getPhysicalModel() {
        return physicalModel;
    }

    private boolean isDestroyed = false;

    /**
     * @param frame sprite for draw in stage
     */
    protected void setFrame(Sprite frame) {

        float width = toUnits(frame.getRegionWidth());
        float height = toUnits(frame.getRegionHeight());

        setSize(width, height);
        setScale(getScaleX(), getScaleY());
        setOrigin(width / 2, height / 2);

        this.frame.setRegion(frame);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    protected void positionChanged() {
        frame.setPosition(getX(), getY());
    }

    @Override
    protected void scaleChanged() {
        frame.setScale(getScaleX(), getScaleY());

    }

    @Override
    protected void sizeChanged() {
        frame.setSize(getWidth(), getHeight());
        frame.setOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    protected void rotationChanged() {
        frame.setRotation(getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        attachBody();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color actorColor = getColor();
        if (actorColor != null) {
            frame.setColor(actorColor);
        }

        frame.draw(batch, parentAlpha);


    }

    private void attachFrame() {

    }

    private void attachBody() {
        if (body != null) {
            Vector2 bodyPosition = body.getPosition();
            float rotation = (float) Math.toDegrees(body.getAngle());

            setPosition(bodyPosition.x - getOriginX(), bodyPosition.y - getOriginY());
            setRotation(rotation);

        }
    }

    /**
     * use this for remove actor end body from world and stage its will to do in next frame
     */
    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

}
