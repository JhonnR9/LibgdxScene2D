package com.jhonn.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.jhonn.game.configs.AnimationConfig;
import com.jhonn.game.constants.GameConst;
import com.jhonn.game.managers.AnimationManager;
import com.jhonn.game.managers.ResourceManager;

import static com.jhonn.game.constants.GameConst.toPx;
import static com.jhonn.game.constants.GameConst.toUnits;

public final class Player extends Actor {
    private final AnimationManager animationManager = new AnimationManager();
    private final String walkRight = "walkLeft";
    private final String walkLeft = "walkRight";
    private boolean isMove = false;
    private Float tileMapWidth, tileMapHeight;
    private final float speed = 4.0f;

    public void setTileMapWidth(Float tileMapWidth) {
        this.tileMapWidth = tileMapWidth;
    }

    public void setTileMapHeight(Float tileMapHeight) {
        this.tileMapHeight = tileMapHeight;
    }

    private final Sprite frame = new Sprite();

    public Player() {
        AnimationConfig walkLeftConfig = new AnimationConfig(4, 1, 16, 16);
        AnimationConfig walkRightConfig = new AnimationConfig(4, 1, 16, 16);
        walkRightConfig.setStartY(1);
        animationManager.setTexture(ResourceManager.getInstance().getTexture("character.png"));
        animationManager.createAnimation(walkLeftConfig, walkRight);
        animationManager.createAnimation(walkRightConfig, walkLeft);
        configStart();

    }

    private void updateCameraPosition() {
        Camera camera = getStage().getCamera();

        float actorCenterX = this.getX() + this.getWidth() / 2;
        float actorCenterY = this.getY() + this.getHeight() / 2;

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
        float maxX = toUnits(tileMapWidth) - this.getWidth();
        float minY = 0;
        float maxY = toUnits(tileMapHeight) - this.getHeight();

        float actorX = MathUtils.clamp(this.getX(), minX, maxX);
        float actorY = MathUtils.clamp(this.getY(), minY, maxY);

        this.setPosition(actorX, actorY);
    }




    private void configStart() {
        TextureRegion frame = animationManager.getFrame(animationManager.getCurrentAnimationKey());
        this.setWidth(toUnits(frame.getRegionWidth()));
        this.setHeight(toUnits(frame.getRegionHeight()));

    }

    private void move(float delta) {
        float velocity = speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveBy(velocity, 0);
            isMove = true;
            animationManager.setCurrentAnimationKey(walkLeft);
            frame.setFlip(true, false);

        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveBy(-velocity, 0);
            isMove = true;
            animationManager.setCurrentAnimationKey(walkLeft);

        } else if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveBy(0, velocity);
            isMove = true;
            animationManager.setCurrentAnimationKey(walkRight);


        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveBy(0, -velocity);
            isMove = true;
            animationManager.setCurrentAnimationKey(walkRight);

        } else {
            isMove = false;
        }

    }

    @Override
    protected void sizeChanged() {
        setOrigin(Align.center);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (isMove) {
            animationManager.update(delta);
        }
        frame.setTexture(animationManager.getFrame(animationManager.getCurrentAnimationKey()).getTexture());
        frame.setRegion(animationManager.getFrame(animationManager.getCurrentAnimationKey()));
        move(delta);
        limitActorPosition();
        if (tileMapWidth != null && tileMapHeight != null) {
            updateCameraPosition();
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                frame,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );


    }


}
