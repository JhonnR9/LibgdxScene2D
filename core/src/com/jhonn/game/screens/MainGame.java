package com.jhonn.game.screens;

import com.badlogic.gdx.math.Vector2;
import com.jhonn.game.entities.Inventory;
import com.jhonn.game.entities.Player;
import com.jhonn.game.entities.items.Coins;
import com.jhonn.game.entities.items.Potion;
import com.jhonn.game.entities.tilemap.TilemapHandle;
import com.jhonn.game.managers.InventoryManager;

import java.util.concurrent.ThreadLocalRandom;

import static com.jhonn.game.box2d.Box2dWorld.toUnits;


public final class MainGame extends BaseScreen {
    private Player player;

    @Override
    public void show() {
        super.show();
        Inventory inventory = new Inventory( new InventoryManager());
        inventory.center(uiStage);
        uiStage.addActor(inventory);

        player = new Player(5, 3, inventory.getInventoryManager());
        stage.addActor(player);

        TilemapHandle tile = new TilemapHandle("graphics/tiled/main.tmx");

        stage.addActor(tile);

        int quantity = 5;
        for (int i = 0; i < quantity; i++) {
            Vector2 position = getRandomPosition((int) toUnits(tile.getWidth() - 1), (int) toUnits(tile.getHeight() - 1));
            Coins coins = new Coins(position.x, position.y);
            stage.addActor(coins);
        }
        int quantityP = 5;
        for (int i = 0; i < quantityP; i++) {
            Vector2 position = getRandomPosition((int) toUnits(tile.getWidth() - 1), (int) toUnits(tile.getHeight() - 1));
            Potion p = new Potion(position.x, position.y);
            stage.addActor(p);
        }

        box2DWorld.createTileColliders(tile);

        topDownCamera.setMapSize(tile.getWidth(), tile.getHeight());

    }

    private Vector2 getRandomPosition(int maxX, int maxY) {
        int x = ThreadLocalRandom.current().nextInt(2, maxX);
        int y = ThreadLocalRandom.current().nextInt(2, maxY);
        return new Vector2(x, y);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        topDownCamera.centerToActorSmooth(player,.02f);

    }
}
