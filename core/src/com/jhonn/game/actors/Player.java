package com.jhonn.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.jhonn.game.box2d.BodyFactory;
import com.jhonn.game.configs.AnimationConfig;
import com.jhonn.game.managers.AnimationManager;
import com.jhonn.game.managers.ResourceManager;

import java.util.Objects;

import static com.jhonn.game.constants.GameConst.toUnits;

public final class Player extends Actor {
    private final AnimationManager animationManager = new AnimationManager();
    private final String walkHorizontal = "walkHorizontal", walkDown = "walkDown", walkUp = "walkUp";

    private boolean isMoving = false;
    private float tileMapWidth, tileMapHeight;

    private Body body;

    private boolean right, left, up, down;


    public Player(World world) {
        createAnimations();
        setupSize();
        createBody(world);


    }

    public Player(World world, float x, float y) {
        setPosition(x, y);
        createAnimations();
        setupSize();
        createBody(world);
    }

    private void createBody(World world){
        BodyFactory bodyFactory = new BodyFactory(this);
        body = bodyFactory.createBox(world, false);

        float linearDamping = 5f;
        body.setLinearDamping(linearDamping);
    }

    private void createAnimations() {
        AnimationConfig walkDownConfig = new AnimationConfig(4, 1, 16, 16);
        AnimationConfig walkHorizontalConfig = new AnimationConfig(4, 1, 16, 16, 0, 1);
        AnimationConfig walkUpConfig = new AnimationConfig(4, 1, 16, 16, 0, 2);

        animationManager.setTexture(ResourceManager.getInstance().getTexture("character.png"));
        animationManager.createAnimation(walkDownConfig, this.walkDown);
        animationManager.createAnimation(walkHorizontalConfig, this.walkHorizontal);
        animationManager.createAnimation(walkUpConfig, this.walkUp);

    }

    public void setMapSize(float width, float height) {
        this.tileMapWidth = width;
        this.tileMapHeight = height;
    }

    private void updateCameraPosition() {
        Camera camera = getStage().getCamera();

        float actorCenterX = getX() + getWidth() / 2;
        float actorCenterY = getY() + getHeight() / 2;

        float minX = camera.viewportWidth / 2;
        float maxX = toUnits(tileMapWidth) - (camera.viewportWidth / 2);
        float minY = camera.viewportHeight / 2;
        float maxY = toUnits(tileMapHeight) - (camera.viewportHeight / 2);

        float newCameraX = MathUtils.clamp(actorCenterX, minX, maxX);
        float newCameraY = MathUtils.clamp(actorCenterY, minY, maxY);

        camera.position.set(newCameraX, newCameraY, 0);
        camera.update();
    }

    private void limitActorPosition() {
        float minX = 0;
        float maxX = toUnits(tileMapWidth) - getWidth();
        float minY = 0;
        float maxY = toUnits(tileMapHeight) - getHeight();

        float actorX = MathUtils.clamp(getX(), minX, maxX);
        float actorY = MathUtils.clamp(getY(), minY, maxY);

        setPosition(actorX, actorY);
    }

    private void setupSize() {
        TextureRegion frame = animationManager.getFrame(animationManager.getCurrentAnimationKey());
        setWidth(toUnits(frame.getRegionWidth()));
        setHeight(toUnits(frame.getRegionHeight()));
        setOrigin(Align.center);
    }

    private void updateInputStatus() {
        right = (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT));
        left = (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT));
        up = (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP));
        down = (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN));
    }

    private void move(float delta) {
        float speed = 400.0f;
        float velocity = speed * delta;

        isMoving = (right || left || up || down);
        if (!isMoving) return;

        float diagonalVelocity = (float) (velocity / Math.sqrt(2.0));

        if (right && up) {
            moveBy(diagonalVelocity, diagonalVelocity);
            updateAnimation(walkHorizontal);
            animationManager.setFlip(true);
        } else if (right && down) {
            moveBy(diagonalVelocity, -diagonalVelocity);
            updateAnimation(walkHorizontal);
            animationManager.setFlip(true);
        } else if (left && up) {
            moveBy(-diagonalVelocity, diagonalVelocity);
            updateAnimation(walkHorizontal);
            animationManager.setFlip(false);
        } else if (left && down) {
            moveBy(-diagonalVelocity, -diagonalVelocity);
            updateAnimation(walkHorizontal);
            animationManager.setFlip(false);
        } else if (right) {
            moveBy(velocity, 0);
            updateAnimation(walkHorizontal);
            animationManager.setFlip(true);
        } else if (left) {
            moveBy(-velocity, 0);
            updateAnimation(walkHorizontal);
            animationManager.setFlip(false);
        } else if (up) {
            moveBy(0, velocity);
            updateAnimation(walkUp);
        } else {
            moveBy(0, -velocity);
            updateAnimation(walkDown);
        }

    }

    @Override
    public void moveBy(float x, float y) {
        body.applyForceToCenter(x, y, true);
    }

    private void syncPosition(Vector2 position) {
        setPosition(position.x - getOriginX(), position.y - getOriginY());
    }

    private void updateAnimation(String animationKey) {
        String currentAnimation = animationManager.getCurrentAnimationKey();

        if (Objects.equals(currentAnimation, animationKey)) {
            return;
        }
        animationManager.setCurrentAnimationKey(animationKey);
        if (Objects.equals(animationKey, walkHorizontal)) {
            animationManager.setFlip(false);
        } else if (Objects.equals(animationKey, walkDown)) {
            animationManager.setFlip(true);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isMoving) {
            animationManager.update(delta);
        }


        updateInputStatus();
        move(delta);
        limitActorPosition();

        if (tileMapWidth != 0 && tileMapHeight != 0) {
            updateCameraPosition();
        }
        syncPosition(body.getPosition());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(
                animationManager.getFrame(animationManager.getCurrentAnimationKey()),
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );
    }
}
