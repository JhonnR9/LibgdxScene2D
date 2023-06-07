package com.jhonn.game.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.jhonn.game.GameStage;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.Interfaces.input.KeyboardListener;
import com.jhonn.game.utils.Interfaces.input.TouchListener;

import static com.jhonn.game.box2d.Box2dWorld.toUnits;

public class Inventory extends Actor implements KeyboardListener {
    private TextureRegion textureRegion;

    public Inventory() {
        TextureAtlas atlas = ResourceManager.getInstance().getDefaultAtlas();
        textureRegion = atlas.findRegion("inventory_big");
        setScale(.7f);

        if (this.getWidth() == 0 || this.getHeight() == 0) {
            float width = textureRegion.getRegionWidth() * getScaleX();
            float height = textureRegion.getRegionHeight() * getScaleY();
            setSize(width, height);
        }
        addAction(Actions.hide());


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                textureRegion,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );


    }

    public void center(Stage stage) {
        int yOffset = 20;
        float x = ((stage.getWidth() / 2) - ((getWidth() / 2)) * getScaleX());
        float y = (((stage.getHeight()) / 2) - ((getHeight() / 2)) * getScaleY()) - yOffset;

        this.setPosition(x, y);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.I) {
            if (isVisible()) {
                addAction(Actions.hide());
            } else {
                addAction(Actions.show());
            }
        }
        if (keycode == Input.Keys.R){
            GameStage gameStage = (GameStage) getStage();
            gameStage.removeActor(this);
        }
        return true;
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
