package com.jhonn.game.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.jhonn.game.managers.InventoryManager;
import com.jhonn.game.models.AnimationModel;
import com.jhonn.game.factories.AnimationFactory;
import com.jhonn.game.Interfaces.input.KeyboardListener;
import com.jhonn.game.utils.enums.CardinalPoint;
import com.jhonn.game.utils.TopDownMove;

public final class Player extends BaseActor implements KeyboardListener {
    private final AnimationFactory animationFactory = new AnimationFactory();
    private final TopDownMove topDownMove = new TopDownMove(5);
    private final ColorAction restoreColorAction = Actions.color(getColor(), 0.1f);
    private final ColorAction hitColorAction = Actions.color(Color.RED, 0.1f);
    private final InventoryManager inventoryManager;


    private enum Animations {
        DOWN, LEFT, RIGHT, UP, DOWN_LEFT, DOWN_RIGHT, UP_LEFT, UP_RIGHT
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public Player(float x, float y, InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        setPosition(x, y);
        createAnimations();
        setFrame(animationFactory.getFrame(), true);
        physicalModel.setStatic(false);
        physicalModel.setLinearDamping(3f);
        physicalModel.setBodyUserDate(this);
        physicalModel.setSize(new Vector2(.5f, .5f));
        physicalModel.setDensity(1f);
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


        animationFactory.create(downConfig, Animations.DOWN);
        animationFactory.create(leftConfig, Animations.LEFT);
        animationFactory.create(rightConfig, Animations.RIGHT);
        animationFactory.create(upConfig, Animations.UP);
        animationFactory.create(upLConfig, Animations.UP_LEFT);
        animationFactory.create(upRConfig, Animations.UP_RIGHT);
        animationFactory.create(downLConfig, Animations.DOWN_LEFT);
        animationFactory.create(downRConfig, Animations.DOWN_RIGHT);


    }

    private void hitDamage() {
        addAction(Actions.sequence(hitColorAction, restoreColorAction));
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        topDownMove.update(delta, getBody());
        if (!isMoving()) return;
        changeAnimation();
        animationFactory.update(delta);
        setFrame(animationFactory.getFrame(), false);

    }
    private boolean isMoving(){
        return topDownMove.getCardinalPoint() != CardinalPoint.NULL;
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
                animationFactory.setCurrentAnimation(Animations.UP);
                break;
            }
            case SOUTH: {
                animationFactory.setCurrentAnimation(Animations.DOWN);
                break;
            }
            case WEST: {
                animationFactory.setCurrentAnimation(Animations.LEFT);
                break;
            }
            case EAST: {
                animationFactory.setCurrentAnimation(Animations.RIGHT);
                break;
            }
            case NORTHEAST:
                animationFactory.setCurrentAnimation(Animations.UP_RIGHT);
                break;
            case NORTHWEST:
                animationFactory.setCurrentAnimation(Animations.UP_LEFT);
                break;
            case SOUTHEAST:
                animationFactory.setCurrentAnimation(Animations.DOWN_RIGHT);
                break;
            case SOUTHWEST:
                animationFactory.setCurrentAnimation(Animations.DOWN_LEFT);
                break;

        }
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean isDamage = (keycode == Input.Keys.E);
        if (isDamage) hitDamage();
        return isDamage;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
