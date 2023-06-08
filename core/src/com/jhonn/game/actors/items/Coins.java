package com.jhonn.game.actors.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.jhonn.game.actors.Player;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.enums.BodyShape;

public class Coins extends BaseItem {
    public Coins(float x, float y) {
        super(x, y, 1, "coins");
        Sprite sprite = new Sprite(ResourceManager.getInstance().getDefaultAtlas().findRegion("coletable"));
        setFrame(sprite);
        physicalModel.setBodyShape(BodyShape.CIRCLE);
        setScale(.6f);

    }

}
