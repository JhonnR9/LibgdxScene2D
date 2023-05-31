package com.jhonn.game.models;

public class DimensionModel {
    private final float width;
    private final float height ;

    public DimensionModel(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
