package com.jhonn.game.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.enums.BodyShape;

public final class Potion extends  BaseItem{
    public Potion(float x, float y) {
        super(x, y, 2, "potion");
        Sprite sprite = new Sprite(ResourceManager.getInstance().getDefaultAtlas().findRegion("potion"));
        setFrame(sprite,true);
        physicalModel.setBodyShape(BodyShape.RECTANGLE);
        setScale(.5f);
    }

}
