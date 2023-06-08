package com.jhonn.game.actors.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.enums.BodyShape;

public class Potion extends  BaseItem{
    public Potion(float x, float y) {
        super(x, y, 2, "potion");
        setScale(.7f);
        Sprite sprite = new Sprite(ResourceManager.getInstance().getDefaultAtlas().findRegion("potion"));
        setFrame(sprite);
        physicalModel.setBodyShape(BodyShape.RECTANGLE);
    }

}
