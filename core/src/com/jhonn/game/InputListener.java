package com.jhonn.game;

import com.badlogic.gdx.InputProcessor;

import com.jhonn.game.utils.Interfaces.input.KeyboardListener;
import com.jhonn.game.utils.Interfaces.input.TouchListener;

import java.util.ArrayList;
import java.util.List;


public class InputListener implements InputProcessor {
    private final List<TouchListener> touchListeners = new ArrayList<>();
    private final List<KeyboardListener> keyboardListeners = new ArrayList<>();

    public void subscribeTouch(TouchListener touchListener) {
        touchListeners.add(touchListener);
    }

    public void subscribeKeyboard(KeyboardListener keyboardListener) {
        keyboardListeners.add(keyboardListener);
    }

    public void unsubscribeTouch(TouchListener touchListener) {
        touchListeners.remove(touchListener);
    }

    public void unsubscribeKeyboard(KeyboardListener keyboardListener) {
        keyboardListeners.remove(keyboardListener);
    }

    public void clearAll() {
        keyboardListeners.clear();
        touchListeners.clear();
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean result = false;
        for (KeyboardListener keyboardListener : keyboardListeners) {
            result = result || keyboardListener.keyDown(keycode);
        }
        return result;
    }


    @Override
    public boolean keyUp(int keycode) {
        boolean result = false;
        for (KeyboardListener keyboardListener : keyboardListeners) {
            result = result || keyboardListener.keyUp(keycode);
        }
        return result;
    }

    @Override
    public boolean keyTyped(char character) {
        for (KeyboardListener keyboardListener : keyboardListeners) {
            return keyboardListener.keyTyped(character);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean result = false;
        for (TouchListener touchListener : touchListeners) {
            result = result || touchListener.touchDown(screenX, screenY, pointer, button);
        }
        return result;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean result = false;
        for (TouchListener touchListener : touchListeners) {
            result = result || touchListener.touchUp(screenX, screenY, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean result = false;
        for (TouchListener touchListener : touchListeners) {
            result = result || touchListener.touchDragged(screenX, screenY, pointer);
        }
        return result;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        boolean result = false;
        for (TouchListener touchListener : touchListeners) {
            result = result || touchListener.mouseMoved(screenX, screenY);
        }
        return result;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        boolean result = false;
        for (TouchListener touchListener : touchListeners) {
            result = result || touchListener.scrolled(amountX, amountY);
        }
        return result;
    }


}
