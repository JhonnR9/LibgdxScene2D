package com.jhonn.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jhonn.game.entities.BaseActor;
import com.jhonn.game.box2d.Box2dWorld;
import com.jhonn.game.factories.BodyFactory;
import com.jhonn.game.Interfaces.input.KeyboardListener;
import com.jhonn.game.Interfaces.input.TouchListener;


public class GameStage extends Stage {
    private final InputListener inputListener = new InputListener();
    private final BodyFactory bodyFactory = new BodyFactory();
    private Box2dWorld box2DWorld;

    public GameStage(Viewport viewport) {
        setViewport(viewport);

    }
    public GameStage(Viewport viewport, Box2dWorld box2DWorld) {
        this.box2DWorld = box2DWorld;
        setViewport(viewport);
    }


    @Override
    public void addActor(Actor actor) {
        if (actor instanceof KeyboardListener) {
            inputListener.subscribeKeyboard((KeyboardListener) actor);
        }
        if (actor instanceof TouchListener) {
            inputListener.subscribeTouch((TouchListener) actor);
        }
        if (actor instanceof BaseActor) {
            BaseActor baseActor = (BaseActor) actor;
            createBody(baseActor);
            box2DWorld.getB2DContactListener().addObserver(baseActor);
        }
        super.addActor(actor);
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        return inputListener.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        super.keyDown(keycode);
        return inputListener.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        super.keyTyped(character);
        return inputListener.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        return inputListener.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        return inputListener.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX, screenY, pointer);
        return inputListener.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        super.mouseMoved(screenX, screenY);
        return inputListener.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        super.scrolled(amountX, amountY);
        return inputListener.scrolled(amountX, amountY);
    }

    @Override
    public void setViewport(Viewport viewport) {
        if (viewport instanceof FillViewport) {
            FillViewport fillViewport = (FillViewport) viewport;
            super.setViewport(fillViewport);
        } else if (viewport instanceof FitViewport) {
            FitViewport fitViewport = (FitViewport) viewport;
            super.setViewport(fitViewport);
        } else if (viewport instanceof ExtendViewport) {
            ExtendViewport extendViewport = (ExtendViewport) viewport;
            super.setViewport(extendViewport);
        } else {
            super.setViewport(viewport);
        }
    }

    public void removeActor(Actor actor) {
        if (actor instanceof KeyboardListener) {
            inputListener.unsubscribeKeyboard((KeyboardListener) actor);
        }
        if (actor instanceof TouchListener) {
            inputListener.unsubscribeTouch((TouchListener) actor);
        }

        Actions.removeActor(actor);
    }

    private void createBody(BaseActor actor) {
        Body body = bodyFactory.createBox(box2DWorld.getWorld(), actor);
        actor.setBody(body);

    }

}
