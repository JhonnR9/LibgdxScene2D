package com.jhonn.game.utils;

import com.badlogic.gdx.graphics.Color;


public class HexColor {
    public static Color create(String hex) {
        Color color = new Color();
        color.set(Integer.valueOf(hex.substring(0, 2), 16) / 255f,
                Integer.valueOf(hex.substring(2, 4), 16) / 255f,
                Integer.valueOf(hex.substring(4, 6), 16) / 255f,
                1f);
        return color;
    }
}
