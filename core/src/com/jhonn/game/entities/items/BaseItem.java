package com.jhonn.game.entities.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import com.jhonn.game.entities.BaseActor;
import com.jhonn.game.entities.Player;
import com.jhonn.game.Interfaces.input.TouchListener;



public abstract class BaseItem extends BaseActor implements TouchListener {
    protected final int id;
    private boolean mouseOverlap = false;
    private TextureRegion icon;

    public BaseItem(float x, float y, int id, String name) {
        this.id = id;
        this.setName(name);
        setPosition(x, y);

        physicalModel.setStatic(false);
        physicalModel.setBodyUserDate(this);
        physicalModel.setSensor(true);

        DragListener dragListener = new DragListener() {
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                mouseOverlap = (BaseItem.this == event.getTarget());
            }
        };
        addListener(dragListener);
    }

    public TextureRegion getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    @Override
    public void beginContact(Body bodyA, Body bodyB) {
        if (bodyA.getUserData() == null || bodyB.getUserData() == null) return;

        if (ClassReflection.isInstance(Player.class, bodyA.getUserData()) &&
                ClassReflection.isInstance(BaseItem.class, bodyB.getUserData())) {

            BaseItem baseItem = (BaseItem) bodyB.getUserData();
            if (baseItem == this) {
                Player player = (Player) bodyA.getUserData();
                BaseItem item = (BaseItem) bodyB.getUserData();
                baseItem.collect(player, item);
            }

        } else if (ClassReflection.isInstance(BaseItem.class, bodyA.getUserData()) &&
                ClassReflection.isInstance(Player.class, bodyB.getUserData())) {
            BaseItem baseItem = (BaseItem) bodyA.getUserData();
            if (baseItem == this) {
                Player player = (Player) bodyB.getUserData();
                BaseItem item = (BaseItem) bodyA.getUserData();
                baseItem.collect(player, item);
            }
        }
    }

    @Override
    protected void setFrame(Sprite frame, boolean firstTime) {
        if (firstTime){
            icon = frame;
        }
        super.setFrame(frame, firstTime);
    }

    @Override
    public void endContact(Body bodyA, Body bodyB) {

    }

    protected void collect(Player player, BaseItem item) {
        setDestroyed(true);
        setVisible(false);
        player.getInventoryManager().addItem(this,1);

    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseOverlap = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (getStage() == null) return false;

        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        getStage().getCamera().unproject(worldCoordinates);

        float x = worldCoordinates.x;
        float y = worldCoordinates.y;

        if (mouseOverlap) {
            getBody().setTransform(x, y, 0);

        }
        return mouseOverlap;

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


}
