package com.jhonn.game.utils.Interfaces.input;

public interface TouchListener {
    boolean touchDown(int screenX, int screenY, int pointer, int button);

    boolean touchUp(int screenX, int screenY, int pointer, int button);

    boolean touchDragged(int screenX, int screenY, int pointer);

    boolean mouseMoved(int screenX, int screenY);

    boolean scrolled(float amountX, float amountY);
}