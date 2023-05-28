package com.jhonn.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.jhonn.game.actors.Collectable;
import com.jhonn.game.actors.Player;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.tilemap.TilemapHandle;



public final class MainGame extends BaseScreen {
    private Player player;
    private TilemapHandle tile;

    @Override
    public void show() {
        super.show();
        setBackgroundColor(Color.CLEAR);

        Collectable collectable = new Collectable(3, 5);
        Collectable collectable1 = new Collectable(8, 6);
        Collectable collectable2 = new Collectable(5, 8);


        player = new Player(5, 1);
        player.setColor(Color.BLUE);
        tile = new TilemapHandle("RAW/main.tmx");


        Stage stage = getStage();
        stage.addActor(tile);
        stage.addActor(collectable);
        stage.addActor(collectable1);
        stage.addActor(collectable2);

        stage.addActor(player);

        createBodies();
        createTileColliders(tile);
        addCollidersObservers();

        Label label = new Label("000", ResourceManager.getInstance().getDefaultSkin());
        Table table = new Table();
        table.setDebug(true);
        table.setFillParent(true);

        table.top().right();
        table.pad(10);
        table.add(label);

        Stage uiStage = getUiStage();
        uiStage.addActor(table);




    }

    @Override
    public void render(float delta) {
        super.render(delta);
        centerCameraActor(player, tile.getWidth(), tile.getHeight());

    }
}
