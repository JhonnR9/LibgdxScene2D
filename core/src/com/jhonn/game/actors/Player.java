package com.jhonn.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.jhonn.game.configs.AnimationConfig;
import com.jhonn.game.managers.Animation;
import com.jhonn.game.managers.ResourceManager;

public final class Player extends BaseActor {
    private final Animation animation = new Animation();
    private static final String LEFT_RIGHT = "left", DOWN = "down", UP = "up";
    private final TopDownMove topDownMove = new TopDownMove(5);

    public Player(float x, float y) {
        setPosition(x, y);
        createAnimations();
        getPhysicalModel().setStatic(false);
        setFrame(animation.getFrame());
        getPhysicalModel().setLinearDamping(3f);
    }

    private void createAnimations() {
        Texture character = ResourceManager.getInstance().getTexture("character.png");
        animation.setTexture(character);

        AnimationConfig downConfig = new AnimationConfig(
                4, 1, 16, 16
        );

        AnimationConfig leftRightConfig = new AnimationConfig(
                4, 1, 16, 16, 0, 1
        );

        AnimationConfig upConfig = new AnimationConfig(
                4, 1, 16, 16, 0, 2
        );


        animation.create(downConfig, DOWN);
        animation.create(leftRightConfig, LEFT_RIGHT);
        animation.create(upConfig, UP);

    }


    @Override
    public void act(float delta) {
        super.act(delta);
        topDownMove.update(delta, getPhysicalModel().getBody());
        Vector2 velocity = getPhysicalModel().getBody().getLinearVelocity();
        if (velocity.isZero(.1f)) {
            return;
        }
        changeAnimation();
        animation.update(delta);


    }

    private void changeAnimation() {
        CardinalPoint cardinalPoint = topDownMove.getCardinalPoint();

        if (cardinalPoint == CardinalPoint.NULL) return;

        switch (cardinalPoint) {
            case NORTH: {
                animation.setCurrentAnimation(UP);
                break;
            }
            case SOUTH: {
                animation.setCurrentAnimation(DOWN);
                break;
            }
            case WEST: {
                animation.setCurrentAnimation(LEFT_RIGHT);
                animation.setFlip(false);
                break;
            }
            case EAST: {
                animation.setCurrentAnimation(LEFT_RIGHT);
                animation.setFlip(true);
                break;
            }

        }
    }

}
