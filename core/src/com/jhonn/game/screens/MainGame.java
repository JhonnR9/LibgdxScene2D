package com.jhonn.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.jhonn.game.actors.Collectable;
import com.jhonn.game.actors.Player;
import com.jhonn.game.managers.ResourceManager;
import com.jhonn.game.actors.tilemap.TilemapHandle;
import com.jhonn.game.utils.HexColor;


public final class MainGame extends BaseScreen {
    private Player player;
    private TilemapHandle tile;

    @Override
    public void show() {
        super.show();

        Collectable collectable = new Collectable(3, 5);
        Collectable collectable1 = new Collectable(8, 6);
        Collectable collectable2 = new Collectable(5, 8);


        player = new Player(5, 3);
        player.setColor(Color.BLUE);
        tile = new TilemapHandle("graphics/Tiled/main.tmx");

        stage.addActor(tile);
        stage.addActor(collectable);
        stage.addActor(collectable1);
        stage.addActor(collectable2);

        stage.addActor(player);

        box2dModel.createBodies(stage);
        box2dModel.createTileColliders(tile);

        addCollidersObservers();

        Skin skin = ResourceManager.getInstance().getDefaultSkin();
        Drawable drawable = skin.getDrawable("background");

        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("font"), HexColor.create("000000"));
        labelStyle.background = drawable;

        Label label = new Label("Collected: 00", labelStyle);

        Table table = new Table();
        table.setDebug(true);
        table.setFillParent(true);

        table.top().right();
        table.pad(10);
        table.add(label);

        uiStage.addActor(table);

        topDownCamera.setMapSize(tile.getWidth(), tile.getHeight());


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        topDownCamera.centerToActorSmooth(player, 0.5f);

    }
}
