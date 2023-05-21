package com.jhonn.game.constants;

public class GameConst {
    private static final float PPM = 16f;

    public static float toUnits(float value) {
        return value / PPM;
    }

    public static float toPx(float value) {
        return value * PPM;
    }

}
