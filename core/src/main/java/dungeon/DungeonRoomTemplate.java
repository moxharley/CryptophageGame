package dungeon;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;

/**
 * Represents a template for a dungeon room.
 *
 * @author Harlan Bullock
 * @version 2026
 */
public class DungeonRoomTemplate {
    private final TiledMap room;
    private final int width;
    private final int height;

    private final ArrayList<EntranceSpan> leftEntrances;
    private final ArrayList<EntranceSpan> rightEntrances;
    private final ArrayList<EntranceSpan> upEntrances;
    private final ArrayList<EntranceSpan> downEntrances;

    /**
     * Instantiates a dungeon room template & loads layers.
     *
     * @param room the tilemap room
     */
    public DungeonRoomTemplate(final TiledMap room) {
        this.room = room;
        TiledMapTileLayer layer = (TiledMapTileLayer) room.getLayers().get(0);
        this.width = layer.getWidth();
        this.height = layer.getHeight();

        this.leftEntrances = loadEntranceLayer("p-entrances-left");
        this.rightEntrances = loadEntranceLayer("p-entrances-right");
        this.upEntrances = loadEntranceLayer("p-entrances-up");
        this.downEntrances = loadEntranceLayer("p-entrances-down");
    }

    /**
     * Returns a string representation of this room template.
     *
     * @return debug information for this template
     */
    @Override
    public String toString() {
        return "DungeonRoomTemplate{"
            + "width=" + width
            + ", height=" + height
            + ", leftEntrances=" + leftEntrances.size()
            + ", rightEntrances=" + rightEntrances.size()
            + ", upEntrances=" + upEntrances.size()
            + ", downEntrances=" + downEntrances.size()
            + "}";
    }

    /**
     * Returns the room of the dungeon room template.
     *
     * @return the tile map of the room
     */
    public TiledMap getRoom() {
        return room;
    }

    /**
     * Returns the width of the dungeon room template.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the dungeon room template.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the left entrance spans of the dungeon room template.
     *
     * @return an array list of the left entrance spans
     */
    public ArrayList<EntranceSpan> getLeftEntrances() {
        return leftEntrances;
    }

    /**
     * Returns the right entrance spans of the dungeon room template.
     *
     * @return an array list of the right entrance spans
     */
    public ArrayList<EntranceSpan> getRightEntrances() {
        return rightEntrances;
    }

    /**
     * Returns the down entrance spans of the dungeon room template.
     *
     * @return an array list of the down entrance spans
     */
    public ArrayList<EntranceSpan> getDownEntrances() {
        return downEntrances;
    }

    /**
     * Returns the upper entrance spans of the dungeon room template.
     *
     * @return an array list of the upper entrance spans
     */
    public ArrayList<EntranceSpan> getUpEntrances() {
        return upEntrances;
    }

    /*
     * Loads all entrance spans from the named object layer.
     */
    private ArrayList<EntranceSpan> loadEntranceLayer(final String layerName) {
        final ArrayList<EntranceSpan> spans = new ArrayList<>();
        final MapLayer layer = room.getLayers().get(layerName);

        if (layer == null) {
            return spans;
        }

        final MapObjects objects = layer.getObjects();

        for (MapObject object : objects) {
            RectangleMapObject rectangle = (RectangleMapObject) object;
            spans.add(parseRectangleObject(rectangle));
        }

        return spans;
    }

    /*
     * Converts a rectangle map object into a tile-based entrance span.
     */
    private EntranceSpan parseRectangleObject(final RectangleMapObject object) {
        final float x = object.getRectangle().x;
        final float y = object.getRectangle().y;
        final float widthPixels = object.getRectangle().width;
        final float heightPixels = object.getRectangle().height;

        final int minX = pixelsToTiles(x);
        final int minY = pixelsToTiles(y);
        final int maxX = pixelsToTiles(x + widthPixels) - 1;
        final int maxY = pixelsToTiles(y + heightPixels) - 1;

        return new EntranceSpan(minX, minY, maxX, maxY);
    }

    /*
     * Converts a pixel coordinate into a tile coordinate.
     */
    private int pixelsToTiles(final float pixels) {
        return (int) (pixels / DungeonGenerator.TILE_SIZE);
    }

    /**
     * Determines whether template fits inside a dungeon leaf.
     *
     * @param leaf the leaf container
     * @return true if it can fit, otherwise false
     */
    public boolean fitsIn(final DungeonLeaf leaf) {
        return width <= leaf.getWidth()
            && height <= leaf.getHeight();
    }
}
