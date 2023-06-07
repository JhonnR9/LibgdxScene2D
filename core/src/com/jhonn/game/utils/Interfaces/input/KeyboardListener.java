package com.jhonn.game.utils.Interfaces.input;

public interface KeyboardListener {
    boolean keyDown(int keycode);
    boolean keyUp(int keycode);
    boolean keyTyped(char character);
}
