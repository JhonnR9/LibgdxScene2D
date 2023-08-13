package com.jhonn.game.entities.tilemap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import static com.jhonn.game.box2d.Box2dWorld.toUnits;


public class TilemapHandle extends Actor {
    private final TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private String layerColliderName = "colliders";

    public TilemapHandle(String mapPath) {
        tiledMap = new TmxMapLoader().load(mapPath);
        setOrigin(Align.center);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (tiledMapRenderer == null) {
            float unitScale = toUnits(1f);

            for (TiledMapTileSet tileset : tiledMap.getTileSets()) {
                for (TiledMapTile tile : tileset) {
                    Texture texture = tile.getTextureRegion().getTexture();
                    texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                }
            }

            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
        }
        tiledMapRenderer.setView((OrthographicCamera) getStage().getViewport().getCamera());

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

    @Null
    public Array.ArrayIterator<Rectangle> getRectanglesColliders() {
        MapObjects mapObjects;
        Array<Rectangle> rectangles = new Array<>();

        try {
            mapObjects = tiledMap.getLayers().get(layerColliderName).getObjects();
            for (MapObject mapObject : mapObjects) {
                if (ClassReflection.isAssignableFrom(RectangleMapObject.class, mapObject.getClass())) {
                    RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                    Rectangle rect = rectangleMapObject.getRectangle();
                    float x = toUnits(rect.x + (rect.width / 2));
                    float y = toUnits(rect.y + (rect.height / 2));
                    float width = toUnits(rect.width) / 2;
                    float height = toUnits(rect.height) / 2;

                    rectangles.add(new Rectangle(x, y, width, height));
                }
            }
        }catch (NullPointerException e){
            return null;
        }

        if (rectangles.size == 0) {
            return null;
        } else {
            return new Array.ArrayIterator<>(rectangles);
        }
    }


}
