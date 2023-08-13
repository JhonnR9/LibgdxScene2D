package com.jhonn.game.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jhonn.game.models.AnimationModel;
import com.jhonn.game.managers.ResourceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP;
import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.NORMAL;

public class AnimationFactory {
    private final Map<String, Animation<TextureRegion>> animations = new HashMap<>();
    private final Sprite sprite = new Sprite();
    private float elapsedTime;
    private String currentAnimationKey;
    private final ResourceManager resourceManager = ResourceManager.getInstance();

    /**
     * Sets the key of the current animation.
     *
     * @param currentAnimationKey the enum key of the animation to be set as current.
     */
    public <T extends Enum<T>> void setCurrentAnimation(T currentAnimationKey) {
        if (!Objects.equals(this.currentAnimationKey, currentAnimationKey.name())) {
            this.currentAnimationKey = currentAnimationKey.name();
        }

    }

    /**
     * Gets the current frame of the specified animation.
     *
     * @return the current texture region of the specified animation.
     */
    public Sprite getFrame() {
        return sprite;
    }


    /**
     * Creates an array of texture regions from a specified animation config.
     *
     * @param animationModel the animation config containing information about the animation.
     * @return an array of texture regions created from the specified animation config.
     */
    private TextureRegion[] createTextureRegions(AnimationModel animationModel) {
        TextureRegion textureRegion = resourceManager.getDefaultAtlas().findRegion(animationModel.getTextureRegion());
        TextureRegion[][] frames = textureRegion.split(animationModel.getFrameWidth(), animationModel.getFrameHeight());
        TextureRegion[] textureRegions = new TextureRegion[animationModel.getColumns() * animationModel.getRows()];
        int index = 0;
        for (int row = animationModel.getStartY(); row < animationModel.getStartY() + animationModel.getRows(); row++) {
            for (int col = animationModel.getStartX(); col < animationModel.getStartX() + animationModel.getColumns(); col++) {
                textureRegions[index++] = frames[row][col];
            }
        }
        return textureRegions;
    }


    /**
     * Creates an animation from the given {@link AnimationModel} and adds it to the map of animations.
     *
     * @param animationModel the {@link AnimationModel} containing the animation parameters
     * @param animationKey   the key to associate with the animation
     */
    public <T extends Enum<T>> void create(AnimationModel animationModel, T animationKey) {
        Animation<TextureRegion> animation;
        animation = new Animation<>(animationModel.getFrameDuration(), createTextureRegions(animationModel));

        if (animationModel.isLoop()) {
            animation.setPlayMode(LOOP);
        } else {
            animation.setPlayMode(NORMAL);
        }

        animations.put(animationKey.name(), animation);

        if (currentAnimationKey == null) {
            currentAnimationKey = animationKey.name();
            sprite.setRegion(animations.get(currentAnimationKey).getKeyFrame(elapsedTime));
        }
        Gdx.app.log("AnimationFactory", "animation '" + animationKey.name() + "' was created with successful");
    }

    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        sprite.setRegion(animations.get(currentAnimationKey).getKeyFrame(elapsedTime));
    }

}
