package com.jhonn.game.actors.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jhonn.game.actors.Player;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.utils.enums.BodyShape;

public class Coins extends BaseItem {
    public Coins(float x, float y) {
        super(x,y,1,"coins");
        setScale(.7f);
        Sprite sprite = new Sprite(ResourceManager.getInstance().getDefaultAtlas().findRegion("coletable"));
        setFrame(sprite);
        physicalModel.setBodyShape(BodyShape.CIRCLE);
    }

}
