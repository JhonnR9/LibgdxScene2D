package com.jhonn.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;


public class TopDownMove {
    private final float velocity;

    public TopDownMove(float velocity) {
        this.velocity = velocity * 100;
    }

    private CardinalPoint cardinalPoint = CardinalPoint.NULL;

    private void move(float delta, Body body) {
        float force = velocity * delta;

        if (body == null) {
            return;
        }

        float diagonalVelocity = (float) (force / Math.sqrt(2.0));

        switch (cardinalPoint) {
            case NULL: {
                break;
            }
            case NORTHEAST: {
                body.applyForceToCenter(diagonalVelocity, diagonalVelocity, true);
                break;
            }
            case NORTHWEST: {
                body.applyForceToCenter(-diagonalVelocity, diagonalVelocity, true);
                break;
            }
            case SOUTHEAST: {
                body.applyForceToCenter(diagonalVelocity, -diagonalVelocity, true);
                break;
            }
            case SOUTHWEST: {
                body.applyForceToCenter(-diagonalVelocity, -diagonalVelocity, true);
                break;
            }
            case NORTH: {
                body.applyForceToCenter(0, force, true);
                break;
            }
            case WEST: {
                body.applyForceToCenter(-force, 0, true);
                break;
            }
            case EAST: {
                body.applyForceToCenter(force, 0, true);
                break;
            }
            case SOUTH: {
                body.applyForceToCenter(0, -force, true);
                break;
            }

        }
    }


    public void update(float delta, Body body) {
        updateInputStatus();
        move(delta, body);

    }

    public CardinalPoint getCardinalPoint() {
        return cardinalPoint;
    }

    private void updateInputStatus() {
        boolean east = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean west = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean north = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean south = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (north && west) {
            cardinalPoint = CardinalPoint.NORTHWEST;
        } else if (north && east) {
            cardinalPoint = CardinalPoint.NORTHEAST;
        } else if (south && west) {
            cardinalPoint = CardinalPoint.SOUTHWEST;
        } else if (south && east) {
            cardinalPoint = CardinalPoint.SOUTHEAST;
        } else if (north) {
            cardinalPoint = CardinalPoint.NORTH;
        } else if (west) {
            cardinalPoint = CardinalPoint.WEST;
        } else if (east) {
            cardinalPoint = CardinalPoint.EAST;
        } else if (south) {
            cardinalPoint = CardinalPoint.SOUTH;
        } else {
            cardinalPoint = CardinalPoint.NULL;
        }
    }


}
