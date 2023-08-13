package com.jhonn.game.factories;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class SlotPositionFactory {
    private final float x, y;

    public SlotPositionFactory(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public List<Vector2> getSlotPositions(float padding, float margin, float rows, float columns) {
        List<Vector2> slotPositions = new ArrayList<Vector2>((int) (rows * columns));
        float slotOffsetX = 1;
        float slotOffsetY = 1;

        for (int j = 1; j <= rows; j++) {
            for (int i = 0; i < columns; i++) {
                float offsetX = (slotOffsetX == 1) ? padding : (padding + margin) * slotOffsetX;
                float offsetY = (padding + margin) * slotOffsetY;

                slotPositions.add(new Vector2(x + offsetX, y + offsetY));
                slotOffsetX += 2;
            }
            slotOffsetY += 2;
            slotOffsetX = 1;
        }
        return slotPositions;
    }
}
