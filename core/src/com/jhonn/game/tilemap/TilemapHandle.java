package com.jhonn.game.tilemap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jhonn.game.constants.GameConst;

public class TilemapHandle extends Actor {
    private final TiledMap tiledMap;
    private  TiledMapRenderer tiledMapRenderer;

    public TilemapHandle(String mapPath) {
        tiledMap = new TmxMapLoader().load(mapPath);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(tiledMapRenderer == null){
            float  unitScale =  GameConst.toUnits(1f);

            for(TiledMapTileSet tileset: tiledMap.getTileSets()){
                for (TiledMapTile tile: tileset){
                    Texture texture = tile.getTextureRegion().getTexture();
                    texture.setFilter(Texture.TextureFilter.Nearest,Texture.TextureFilter.Nearest);
                }
            }

            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
        }
        tiledMapRenderer.setView((OrthographicCamera) getStage().getCamera());
        tiledMapRenderer.render();
    }

    @Override
    public float getWidth() {
        return tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class);
    }

    @Override
    public float getHeight() {
        return tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class);
    }


}
