package com.jhonn.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.jhonn.game.box2d.Box2dWorld.toUnits;


public class TopDownCamera extends OrthographicCamera {
    private float mapWidth, mapHeight;
    private float minX = 0f;
    private float minY = 0f;
    private float maxX = 500;
    private float maxY = 500;


    public void setMapSize(float width, float height) {
        this.mapWidth = width;
        this.mapHeight = height;

    }

    private void defineLimits() {
        minX = viewportWidth / 2;
        minY = viewportHeight / 2;
        maxX = toUnits(mapWidth) - (viewportWidth / 2);
        maxY = toUnits(mapHeight) - (viewportHeight / 2);
    }

    public void centerToActor(Actor actor) {
        defineLimits();

        float actorCenterX = actor.getX() + (actor.getWidth() / 2);
        float actorCenterY = actor.getY() + (actor.getHeight() / 2);

        float newCameraX = MathUtils.clamp(actorCenterX, minX, maxX);
        float newCameraY = MathUtils.clamp(actorCenterY, minY, maxY);

        position.set(newCameraX, newCameraY, 0);
        update();
    }


    public void centerToActorSmooth(Actor actor, float smoothness) {
        defineLimits();

        float actorCenterX = actor.getX() + actor.getWidth() / 2;
        float actorCenterY = actor.getY() + actor.getHeight() / 2;

        float newCameraX = MathUtils.clamp(actorCenterX, minX, maxX);
        float newCameraY = MathUtils.clamp(actorCenterY, minY, maxY);

        float interpolatedCameraX = MathUtils.lerp(position.x, newCameraX, smoothness);
        float interpolatedCameraY = MathUtils.lerp(position.y, newCameraY, smoothness);

        position.set(interpolatedCameraX, interpolatedCameraY, 0);

        update();
    }


}