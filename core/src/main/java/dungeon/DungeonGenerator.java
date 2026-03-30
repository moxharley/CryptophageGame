package dungeon;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * A generator for the dungeon layout.
 *
 * @author Harlan Bullock
 * @version 2026
 */
public class DungeonGenerator {
    // The following are initialized in create() within MyGameRoot
    public static Array<TiledMap> ROOMS_START; // A list of all the room templates (start)
    public static Array<TiledMap> ROOMS_NORMAL; // A list of all the room templates (normal)
    public static Array<TiledMap> ROOMS_PUZZLE; // A list of all the room templates (puzzle)
    public static Array<TiledMap> ROOMS_TREASURE; // A list of all the room templates (treasure)
    public static Array<TiledMap> ROOMS_SHOP; // A list of all the room templates (shop)
    public static Array<TiledMap> ROOMS_BOSS; // A list of all the room templates (boss)

    public static final int STANDARD_WIDTH = 120;
    public static final int STANDARD_HEIGHT = 96;
    public static final int STANDARD_ROOM_COUNT = 12;
    public static final int TILE_SIZE = 16;

    // Dungeon Configuration
    private final int mapWidth;
    private final int mapHeight;
    private final int roomCount;

    // Dungeon State
    private TiledMap dungeonMap;
    private boolean[][] occupancy;
    private Random randomizer;

    /**
     * Constructs a new dungeon generator with standard configuration.
     */
    public DungeonGenerator() {
        this.mapWidth = STANDARD_WIDTH;
        this.mapHeight = STANDARD_HEIGHT;
        this.roomCount = STANDARD_ROOM_COUNT;
        this.randomizer = new Random();
        this.occupancy = new boolean[STANDARD_WIDTH][STANDARD_HEIGHT];
    }

    /**
     * Constructs a new dungeon generator with custom configuration.
     *
     * @param mapWidth how many tiles wide the dungeon can be
     * @param mapHeight how many tiles tall the dungeon can be
     * @param maxRooms how many rooms should be generated within the dungeon
     */
    public DungeonGenerator(int mapWidth, int mapHeight, int maxRooms) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.roomCount = maxRooms;
        this.randomizer = new Random();
        this.occupancy = new boolean[mapWidth][mapHeight];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- DungeonGenerator Debug ---\n");

        sb.append("Templates Loaded:\n");
        sb.append("  - START: ").append(ROOMS_START != null ? ROOMS_START.size : "NULL").append("\n");
        sb.append("  - NORMAL: ").append(ROOMS_NORMAL != null ? ROOMS_NORMAL.size : "NULL").append("\n");
        sb.append("  - PUZZLE: ").append(ROOMS_PUZZLE != null ? ROOMS_PUZZLE.size : "NULL").append("\n");
        sb.append("  - TREASURE: ").append(ROOMS_TREASURE != null ? ROOMS_TREASURE.size : "NULL").append("\n");
        sb.append("  - SHOP: ").append(ROOMS_SHOP != null ? ROOMS_SHOP.size : "NULL").append("\n");
        sb.append("  - BOSS: ").append(ROOMS_BOSS != null ? ROOMS_BOSS.size : "NULL").append("\n");

        if (dungeonMap == null) {
            sb.append("Status: NO MAP GENERATED\n");
        } else {
            sb.append("Status: Map Generated!\n");
            sb.append("Dimensions: ").append(mapWidth).append("x").append(mapHeight).append("\n");

            sb.append("Layers in Master Map: ");
            for (int i = 0; i < dungeonMap.getLayers().getCount(); i++) {
                sb.append("[").append(dungeonMap.getLayers().get(i).getName()).append("] ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Random getRandomizer() {
        return randomizer;
    }

    public boolean[][] getOccupancy() {
        return occupancy;
    }

    public TiledMap getDungeonMap() {
        return dungeonMap;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    // -- THE JUICY STUFF --

    /**
     * Clears existing master dungeonMap to start fresh and new.
     */
    private void clearMap() {
        // Clears any existing map
        if (this.dungeonMap != null) {
            this.dungeonMap.dispose();
        }

        // Creates the empty map
        this.dungeonMap = new TiledMap();
        MapLayers layers = this.dungeonMap.getLayers();

        // Adds the actual layers of the tilemaps (matches the names in templates)
        // 1. BACKGROUND
        TiledMapTileLayer background = new TiledMapTileLayer(mapWidth, mapHeight, TILE_SIZE, TILE_SIZE);
        background.setName("background");
        layers.add(background);

        // 2. OBJECTS
        TiledMapTileLayer objects = new TiledMapTileLayer(mapWidth, mapHeight, TILE_SIZE, TILE_SIZE);
        objects.setName("objects");
        layers.add(objects);

        // 3. DOORS
        TiledMapTileLayer doors = new TiledMapTileLayer(mapWidth, mapHeight, TILE_SIZE, TILE_SIZE);
        doors.setName("doors");
        layers.add(doors);

        // Resets occupancy grid and door logic
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                occupancy[x][y] = false;
            }
        }
    }

    public TiledMap generate(long seed) {
        randomizer.setSeed(seed);
        clearMap();

        placeStartRoom();

        return dungeonMap;
    }

    private void placeStartRoom() {
        // Picks a random template from the static list.
        TiledMap template = ROOMS_START.random();

        // Gets the dimensions of the room.
        TiledMapTileLayer layer = (TiledMapTileLayer) template.getLayers().get(0);
        int roomWidth = layer.getWidth();
        int roomHeight = layer.getHeight();

        // Calculates the center and where the room should be placed (starting in the bottom-left corner).
        int startX = (mapWidth / 2) - (roomWidth / 2);
        int startY = (mapHeight / 2) - (roomHeight / 2);

        // Copies tiles from the template to the map
        copyTiles(template, startX, startY);

        // Updates occupancy
        updateOccupancy(startX, startY, roomWidth, roomHeight);
    }

    /**
     * Updates occupancy upon adding a room.
     *
     * @param startX x-coordinate of the room starting point
     * @param startY y-coordinate of the room starting point
     * @param roomW width of the room added
     * @param roomH height of the room added
     */
    private void updateOccupancy(final int startX, final int startY, final int roomW, final int roomH) {
        for (int x = startX; x < startX + roomW; x++) {
            for (int y = startY; y < startY + roomH; y++) {
                occupancy[x][y] = true;
            }
        }
    }

    private void copyTiles(TiledMap source, int offsetX, int offsetY) {
        // A list of all the layer names we are going to copy over.
        String[] layerNames = {"background", "objects"};

        // For each layer...
        for (String name : layerNames) {
            TiledMapTileLayer sourceLayer = (TiledMapTileLayer) source.getLayers().get(name);
            TiledMapTileLayer targetLayer = (TiledMapTileLayer) dungeonMap.getLayers().get(name);
            // Null check.

            if (sourceLayer == null) {
                System.out.println("DEBUG: Source layer '" + name + "' not found in template!");
                continue;
            }
            if (targetLayer == null) {
                System.out.println("DEBUG: Target layer '" + name + "' not found in template!");
                continue;
            }
            if (sourceLayer != null && targetLayer != null) {
                for (int x = 0; x < sourceLayer.getWidth(); x++) {
                    for (int y = 0; y < sourceLayer.getHeight(); y++) {
                        // Makes a new cell for safety.
                        TiledMapTileLayer.Cell sourceCell = sourceLayer.getCell(x, y);
                        // Null check.
                        if (sourceCell != null) {
                            TiledMapTileLayer.Cell newCell = new TiledMapTileLayer.Cell();
                            newCell.setTile(sourceCell.getTile());
                            // Adds the cell to the layer.
                            targetLayer.setCell(offsetX + x, offsetY + y, newCell);
                            System.out.println("Copied tile to: " + (offsetX + x) + ", " + (offsetY + y));
                        }
                    }
                }
            }
        }
    }
}
