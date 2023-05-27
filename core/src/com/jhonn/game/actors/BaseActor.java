package com.jhonn.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import static com.jhonn.game.constants.GameConst.toUnits;

public class BaseActor extends Actor {
    private Sprite frame;

    private final PhysicalModel physicalModel = new PhysicalModel();


    public PhysicalModel getPhysicalModel() {
        return physicalModel;
    }


    public Sprite getFrame() {
        return frame;
    }

    public void setFrame(Sprite frame) {
        if (this.getWidth() == 0 || this.getHeight() == 0) {
            float width = toUnits(frame.getRegionWidth()) * getScaleX();
            float height = toUnits(frame.getRegionHeight()) * getScaleY();
            setSize(width, height);
            setOrigin(Align.center);
        }
        this.frame = frame;
    }

    private void boundaryEnforcement() {

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
        if (physicalModel.getBody() != null){
            Body body = physicalModel.getBody();
            Vector2 position = body.getPosition();
            float rotation = (float) Math.toDegrees(body.getAngle());

            setPosition(position.x - getOriginX(), position.y - getOriginY());
            setRotation(rotation);

        }
    }
}
