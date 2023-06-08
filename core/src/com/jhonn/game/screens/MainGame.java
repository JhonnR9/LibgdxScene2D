package com.jhonn.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.jhonn.game.actors.Inventory;
import com.jhonn.game.actors.Player;
import com.jhonn.game.actors.items.Coins;
import com.jhonn.game.actors.items.Potion;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.actors.tilemap.TilemapHandle;
import com.jhonn.game.utils.HexColor;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.jhonn.game.box2d.Box2dWorld.toUnits;
import static java.lang.String.*;


public final class MainGame extends BaseScreen {
    private Player player;
    private TilemapHandle tile;
    private Label label;
    private Label labelP;

    @SuppressWarnings("DefaultLocale")
    @Override
    public void show() {
        super.show();

        player = new Player(5, 3);
        stage.addActor(player);

        tile = new TilemapHandle("graphics/tiled/main.tmx");

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

        Skin skin = ResourceManager.getInstance().getDefaultSkin();
        Drawable drawable = skin.getDrawable("background");

        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("font"), HexColor.create("000000"));
        labelStyle.background = drawable;


        label = new Label(format("Coins: %02d", 0), labelStyle);
        labelP = new Label(format("Potion: %02d", 0), labelStyle);

        Inventory inventory = new Inventory();
        inventory.center(uiStage);

        Table table = new Table();

        table.setDebug(true);
        table.setFillParent(true);

        table.top().right();
        table.pad(10);
        table.add(label).padRight(120);
        table.add(labelP);

        uiStage.addActor(table);
        uiStage.addActor(inventory);

        topDownCamera.setMapSize(tile.getWidth(), tile.getHeight());

    }

    private Vector2 getRandomPosition(int maxX, int maxY) {
        int x = ThreadLocalRandom.current().nextInt(2, maxX);
        int y = ThreadLocalRandom.current().nextInt(2, maxY);
        return new Vector2(x, y);
    }


    @SuppressWarnings("DefaultLocale")
    @Override
    public void render(float delta) {
        super.render(delta);
        label.setText(format("Coins: %03d", player.getInventory().getItemQuantity(1)));
        labelP.setText(format("Potions: %03d", player.getInventory().getItemQuantity(2)));
        topDownCamera.centerToActorSmooth(player, 0.5f);

    }
}
