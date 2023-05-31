package com.jhonn.game.fatories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jhonn.game.models.AnimationModel;
import com.jhonn.game.managers.ResourceManager;

import java.util.HashMap;
import java.util.Map;

public class AnimationFactory {
    private final Map<String, com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>> animations = new HashMap<>();
    private final Sprite sprite = new Sprite();
    private boolean isFlip;
    private float elapsedTime;
    private String currentAnimationKey;
    private final ResourceManager resourceManager = ResourceManager.getInstance();


    public void setFlip(boolean flip) {
        isFlip = flip;
    }


    /**
     * Sets the key of the current animation.
     *
     * @param currentAnimationKey the string key of the animation to be set as current.
     */
    public void setCurrentAnimation(String currentAnimationKey) {
        if (this.currentAnimationKey != currentAnimationKey){
            this.currentAnimationKey = currentAnimationKey;
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
     * @param ac the animation config containing information about the animation.
     * @return an array of texture regions created from the specified animation config.
     */
    private TextureRegion[] createTextureRegions(AnimationModel ac) {
        TextureRegion textureRegion = resourceManager.getDefaultAtlas().findRegion(ac.getTextureRegion());
        TextureRegion[][] frames = textureRegion.split(ac.getFrameWidth(), ac.getFrameHeight());
        TextureRegion[] textureRegions = new TextureRegion[ac.getColumns() * ac.getRows()];
        int index = 0;
        for (int row = ac.getStartY(); row < ac.getStartY() + ac.getRows(); row++) {
            for (int col = ac.getStartX(); col < ac.getStartX() + ac.getColumns(); col++) {
                textureRegions[index++] = frames[row][col];
            }
        }
        return textureRegions;
    }


    /**
     * Creates an animation from the given {@link AnimationModel} and adds it to the map of animations.
     *
     * @param ac           the {@link AnimationModel} containing the animation parameters
     * @param animationKey the key to associate with the animation
     */
    public void create(AnimationModel ac, String animationKey) {
        com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animation;
        animation = new com.badlogic.gdx.graphics.g2d.Animation<>(ac.getFrameDuration(), createTextureRegions(ac));
        if (ac.isLoop()) {
            animation.setPlayMode(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
        } else {
            animation.setPlayMode(com.badlogic.gdx.graphics.g2d.Animation.PlayMode.NORMAL);
        }
        animations.put(animationKey, animation);
        if (currentAnimationKey == null) {
            currentAnimationKey = animationKey;
            sprite.setRegion(animations.get(currentAnimationKey).getKeyFrame(elapsedTime));
        }
    }

    public void update(float deltaTime) {
        elapsedTime += deltaTime;
        sprite.setRegion(animations.get(currentAnimationKey).getKeyFrame(elapsedTime));
        sprite.setFlip(isFlip, false);
    }


    public void setTexture(Texture texture) {
        sprite.setTexture(texture);
    }
}
