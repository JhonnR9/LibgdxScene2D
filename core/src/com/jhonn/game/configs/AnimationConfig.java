package com.jhonn.game.configs;

public class AnimationConfig {
    private int columns, rows, frameWidth, frameHeight, startX = 0, startY = 0;
    private float frameDuration = 1/8f;

    public float getFrameDuration() {
        return frameDuration;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }

    public AnimationConfig(int columns, int rows, int frameWidth, int frameHeight) {
        this.columns = columns;
        this.rows = rows;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }
    public AnimationConfig(int columns, int rows, int frameWidth, int frameHeight,int startX,int startY) {
        this.columns = columns;
        this.rows = rows;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.startX = startX;
        this.startY = startY;
    }

    private boolean isLoop = true;

    public int getColumns() {
        return columns;
    }


    public int getRows() {
        return rows;
    }


    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

}
