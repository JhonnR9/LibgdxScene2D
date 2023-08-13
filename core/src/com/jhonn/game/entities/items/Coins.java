package com.jhonn.game.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.enums.BodyShape;

public final class Coins extends BaseItem {
    public Coins(float x, float y) {
        super(x, y, 1, "coins");
        Sprite sprite = new Sprite(ResourceManager.getInstance().getDefaultAtlas().findRegion("coletable"));
        setFrame(sprite,true);
        physicalModel.setBodyShape(BodyShape.CIRCLE);
        setScale(.6f);

    }

}
