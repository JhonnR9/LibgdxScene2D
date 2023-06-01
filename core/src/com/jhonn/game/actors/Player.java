package com.jhonn.game.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.jhonn.game.models.AnimationModel;
import com.jhonn.game.fatories.AnimationFactory;
import com.jhonn.game.utils.enums.CardinalPoint;
import com.jhonn.game.utils.TopDownMove;

import java.awt.*;

public final class Player extends BaseActor {
    private final AnimationFactory animationFactory = new AnimationFactory();
    private static final String LEFT = "left", DOWN = "down", UP = "up", RIGHT = "right";
    private static final String UP_LEFT = "up_left", UP_RIGHT = "up_right", DOWN_LEFT = "down_left", DOWN_RIGHT = "down_right";
    private final TopDownMove topDownMove = new TopDownMove(5);
    private int labelCollected;


    public Player(float x, float y) {

        setPosition(x, y);
        createAnimations();

        setFrame(animationFactory.getFrame());

        physicalModel.setStatic(false);
        physicalModel.setLinearDamping(3f);
        physicalModel.setBodyUserDate(this);
        physicalModel.setSize(new Vector2(.5f, .5f));

    }

    public int getLabelCollected() {
        return labelCollected;
    }

    public void setLabelCollected(int labelCollected) {
        this.labelCollected = labelCollected;
    }

    private void createAnimations() {
        int frameSize = 32;

        AnimationModel downConfig = new AnimationModel("character/down", 4, 1, frameSize, frameSize);
        AnimationModel leftConfig = new AnimationModel("character/left", 4, 1, frameSize, frameSize);
        AnimationModel rightConfig = new AnimationModel("character/right", 4, 1, frameSize, frameSize);
        AnimationModel upConfig = new AnimationModel("character/up", 4, 1, frameSize, frameSize);
        AnimationModel upLConfig = new AnimationModel("character/up_left", 4, 1, frameSize, frameSize);
        AnimationModel upRConfig = new AnimationModel("character/up_right", 4, 1, frameSize, frameSize);
        AnimationModel downLConfig = new AnimationModel("character/down_left", 4, 1, frameSize, frameSize);
        AnimationModel downRConfig = new AnimationModel("character/down_right", 4, 1, frameSize, frameSize);


        animationFactory.create(downConfig, DOWN);
        animationFactory.create(leftConfig, LEFT);
        animationFactory.create(rightConfig, RIGHT);
        animationFactory.create(upConfig, UP);
        animationFactory.create(upLConfig, UP_LEFT);
        animationFactory.create(upRConfig, UP_RIGHT);
        animationFactory.create(downLConfig, DOWN_LEFT);
        animationFactory.create(downRConfig, DOWN_RIGHT);

    }


    @Override
    public void act(float delta) {
        super.act(delta);
        topDownMove.update(delta, physicalModel.getBody());

        if (topDownMove.getCardinalPoint() == CardinalPoint.NULL) return;
        changeAnimation();
        animationFactory.update(delta);

    }

    @Override
    public void beginContact(Body bodyA, Body bodyB) {

    }

    @Override
    public void endContact(Body bodyA, Body bodyB) {

    }

    private void changeAnimation() {
        CardinalPoint cardinalPoint = topDownMove.getCardinalPoint();
        if (cardinalPoint == CardinalPoint.NULL) return;

        switch (cardinalPoint) {
            case NORTH: {
                animationFactory.setCurrentAnimation(UP);
                break;
            }
            case SOUTH: {
                animationFactory.setCurrentAnimation(DOWN);
                break;
            }
            case WEST: {
                animationFactory.setCurrentAnimation(LEFT);
                break;
            }
            case EAST: {
                animationFactory.setCurrentAnimation(RIGHT);
                break;
            }
            case NORTHEAST:
                animationFactory.setCurrentAnimation(UP_RIGHT);
                break;
            case NORTHWEST:
                animationFactory.setCurrentAnimation(UP_LEFT);
                break;
            case SOUTHEAST:
                animationFactory.setCurrentAnimation(DOWN_RIGHT);
                break;
            case SOUTHWEST:
                animationFactory.setCurrentAnimation(DOWN_LEFT);
                break;

        }
    }
}
