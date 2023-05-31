package com.jhonn.game.managers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;

public class ResourceManager implements AssetErrorListener {
    private final AssetManager assetManager;
    private static final Logger log = new Logger(ResourceManager.class.getName(), Logger.DEBUG);
    private String defaultSkin;
    private String defaultAtlas;

    public void setDefaultAtlas(String defaultAtlas) {
        this.defaultAtlas = defaultAtlas;
    }

    private static ResourceManager instance;

    private ResourceManager() {
        assetManager = new AssetManager();
        assetManager.setErrorListener(this);
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    /**
     * Read all resources and load them at once so any new resources
     * must be placed in the method implementation
     */
    public void loadResources() {
        assetManager.load(defaultSkin, Skin.class);
        if (defaultAtlas != null){
            assetManager.load(defaultAtlas, TextureAtlas.class);
        }

        assetManager.finishLoading();

    }

    /**
     * @return default atlas of the game and must be defined before with /setDefaultAtlas();
     */
    public TextureAtlas getDefaultAtlas() {
        if (assetManager.isLoaded(defaultAtlas, TextureAtlas.class)) {
            return assetManager.get(defaultAtlas, TextureAtlas.class);
        } else {
            throw new RuntimeException("Texture '" + defaultAtlas + "' not loaded.");
        }
    }
    /**
     * @param path Path to the file with the extension for example "badlogic.jpg"
     * @return A reference to an instance of a Texture previously loaded with loadResources()
     */
    public Texture getTexture(String path) {
        if (assetManager.isLoaded(path, Texture.class)) {
            return assetManager.get(path, Texture.class);
        } else {
            throw new RuntimeException("Texture '" + path + "' not loaded.");
        }
    }

    public void setDefaultSkin(String defaultSkin) {
        this.defaultSkin = defaultSkin;
    }

    /**
     * @return default skin of the game and must be defined before with /setDefaultSkin();
     */
    public Skin getDefaultSkin() {
        if (assetManager.isLoaded(defaultSkin, Skin.class)) {
            return assetManager.get(defaultSkin, Skin.class);
        } else {
            throw new RuntimeException("Skin '" + defaultSkin + "' not loaded.");
        }
    }

    /**
     * @param path Path to the file with the extension for example "graphics.atlas"
     * @return A reference to an instance of a TextureAtlas previously loaded with loadResources()
     */
    public TextureAtlas getTextureAtlas(String path) {
        if (assetManager.isLoaded(path, TextureAtlas.class)) {
            return assetManager.get(path, TextureAtlas.class);
        } else {
            throw new RuntimeException("TextureAtlas '" + path + "' not loaded.");
        }
    }

    /**
     * @return current load progress
     */
    public float getLoadingProgress() {
        return assetManager.getProgress();
    }

    /**
     * @return true if it completed the load successfully
     */
    public boolean isLoadingComplete() {
        return assetManager.update();
    }

    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        log.error("Error loading asset: " + asset.fileName, throwable);
        throwable.printStackTrace();
    }
}
