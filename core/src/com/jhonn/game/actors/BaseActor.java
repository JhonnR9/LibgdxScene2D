package com.jhonn.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.jhonn.game.box2d.CollisionObserver;
import com.jhonn.game.utils.PhysicalModel;

import static com.jhonn.game.box2d.Box2dModel.toUnits;

/**
 * class base for create actors
 * yours actors can use physical
 */
public abstract class BaseActor extends Actor implements CollisionObserver {

    private Sprite frame;

    private final PhysicalModel physicalModel = new PhysicalModel();

    /**
     *
     * @return PhysicalModel for define the body and other physical aspect
     * @see PhysicalModel
     */
    public PhysicalModel getPhysicalModel() {
        return physicalModel;
    }


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
     * use this for remove actor end body from world and stage
     */
    public void destroy(){
        getPhysicalModel().setDestroyed(true);
        this.remove();
    }

}
