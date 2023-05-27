package com.jhonn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jhonn.game.actors.Player;
import com.jhonn.game.actors.physical.box2d.Box2dModel;
import com.jhonn.game.tilemap.TilemapHandle;

import static com.jhonn.game.constants.GameConst.toUnits;


public final class MainGame extends BaseScreen {
    private Player player;
    private TilemapHandle tile;

    @Override
    public void show() {
        super.show();
        setBackgroundColor(Color.CLEAR);

        player = new Player(5, 1);
        player.setColor(Color.BLUE);
        tile = new TilemapHandle("RAW/main.tmx");


        Stage stage = getStage();
        stage.addActor(player);
        stage.addActor(tile);

        createBodies();


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        centerCameraActor(player, tile.getWidth(), tile.getHeight());

    }
}
