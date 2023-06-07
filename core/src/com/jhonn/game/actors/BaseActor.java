package com.jhonn.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.jhonn.game.utils.Interfaces.CollisionObserver;
import com.jhonn.game.models.PhysicalModel;

import static com.jhonn.game.box2d.Box2dWorld.toUnits;

/**
 * class base for create actors
 * yours actors can use physical
 */
public abstract class BaseActor extends Actor implements CollisionObserver {
    private Sprite frame;
    protected final PhysicalModel physicalModel = new PhysicalModel();

    public PhysicalModel getPhysicalModel() {
        return physicalModel;
    }

    private boolean isDestroyed = false;

    /**
     * @param frame sprite for draw in stage
     */
    public void setFrame(Sprite frame) {
        if (this.getWidth() == 0 || this.getHeight() == 0) {
            float width = toUnits(frame.getRegionWidth()) * getScaleX();
            float height = toUnits(frame.getRegionHeight()) * getScaleY();
            setSize(width, height);
            setOrigin(Align.center);
        }
        this.frame = frame;
    }


    @Override
    public void act(float delta) {
        attachBodyToActor();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible()) return;
        if (frame == null) return;
        if (getColor() != null) {
            frame.setColor(getColor());
        }
        batch.draw(
                frame,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }

    private void attachBodyToActor() {
        if (physicalModel.getBody() != null) {
            Body body = physicalModel.getBody();
            Vector2 position = body.getPosition();
            float rotation = (float) Math.toDegrees(body.getAngle());

            setPosition(position.x - getOriginX(), position.y - getOriginY());
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
