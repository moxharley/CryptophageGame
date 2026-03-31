package dungeon;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

/**
 * A generator for the dungeon layout using BSP partitioning.
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

    public static final int STANDARD_WIDTH = 96; // Tiles
    public static final int STANDARD_HEIGHT = 96; // Tiles
    public static final int STANDARD_ROOM_COUNT = 12; // Leaves
    public static final int TILE_SIZE = 16; // Pixels

    // Dungeon Configuration
    private final int mapWidth;
    private final int mapHeight;
    private final int roomCount;

    // Dungeon Randomzier
    private final Random randomizer;

    // Dungeon State
    private TiledMap dungeonMap;
    private DungeonLeaf rootLeaf;
    private ArrayList<DungeonLeaf> allLeaves;
    private ArrayList<DungeonLeaf> finalLeaves;

    /**
     * Constructs a new dungeon generator with standard configuration.
     */
    public DungeonGenerator() {
        this.mapWidth = STANDARD_WIDTH;
        this.mapHeight = STANDARD_HEIGHT;
        this.roomCount = STANDARD_ROOM_COUNT;
        this.randomizer = new Random();
        this.allLeaves = new ArrayList<>();
        this.finalLeaves = new ArrayList<>();
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
        this.allLeaves = new ArrayList<>();
        this.finalLeaves = new ArrayList<>();
    }

    /**
     * Returns a string representation of the generator for debugging.
     *
     * @return generator debug information
     */
    @Override
    public String toString() {
        return "DungeonGenerator{"
            + "mapWidth=" + mapWidth
            + ", mapHeight=" + mapHeight
            + ", roomCount=" + roomCount
            + ", totalLeaves=" + allLeaves.size()
            + ", finalLeaves=" + finalLeaves.size()
            + ", hasMap=" + (dungeonMap != null)
            + "}";
    }

    /**
     * Returns the root leaf of this tree.
     *
     * @return the root leaf
     */
    public DungeonLeaf getRootLeaf() {
        return rootLeaf;
    }

    /**
     * Returns the master dungeon map.
     *
     * @return the TiledMap generated
     */
    public TiledMap getDungeonMap() {
        return dungeonMap;
    }

    /**
     * Returns the room count.
     *
     * @return the room count
     */
    public int getRoomCount() {
        return roomCount;
    }

    /**
     * Returns the dungeon map height.
     *
     * @return the map height
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Returns the dungeon map width.
     *
     * @return the map width
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Returns all leaves in the dungeon.
     *
     * @return the array list of all dungeon leaves
     */
    public ArrayList<DungeonLeaf> getAllLeaves() {
        return allLeaves;
    }

    /**
     * Returns all final leaves in the dungeon.
     *
     * @return the array of all final dungeon leaves
     */
    public ArrayList<DungeonLeaf> getFinalLeaves() {
        return finalLeaves;
    }

    // -- THE JUICY STUFF --

    public TiledMap generate(final long seed) {
        randomizer.setSeed(seed);

        clearState();
        createEmptyMap();
        createRootLeaf();
        splitLeaves();
        debugDrawLeaves();

        return dungeonMap;
    }

    /**
     * Clears generator state before creating a new dungeon.
     */
    private void clearState() {
        if (dungeonMap != null) {
            dungeonMap.dispose();
        }
        dungeonMap = null;
        rootLeaf = null;
        allLeaves.clear();
        finalLeaves.clear();
    }

    /**
     * Creates an empty tiled map that the dungeon will be drawn into.
     */
    private void createEmptyMap() {
        dungeonMap = new TiledMap();
        MapLayers layers = dungeonMap.getLayers();

        TiledMapTileLayer walls = new TiledMapTileLayer(mapWidth, mapHeight, TILE_SIZE, TILE_SIZE);
        walls.setName("walls");
        layers.add(walls);

        TiledMapTileLayer debug = new TiledMapTileLayer(mapWidth, mapHeight, TILE_SIZE, TILE_SIZE);
        debug.setName("debug");
        layers.add(debug);
    }

    /**
     * Creates the root BSP leaf covering the whole dungeon area.
     */
    private void createRootLeaf() {
        rootLeaf = new DungeonLeaf(0, 0, mapWidth, mapHeight);
        allLeaves.add(rootLeaf);
    }

    private void splitLeaves() {
        boolean splitOccurred = true;
        while (countCurrentLeaves() < getRoomCount() && splitOccurred) {
            splitOccurred = false;

            ArrayList<DungeonLeaf> newLeaves = new ArrayList<>();

            for (DungeonLeaf leaf : getAllLeaves()) {
                if (leaf.isLeaf() && leaf.split()) {
                    newLeaves.add(leaf.getLeftChild());
                    newLeaves.add(leaf.getRightChild());
                    splitOccurred = true;
                }
            }
            getAllLeaves().addAll(newLeaves);
        }
        collectFinalLeaves();
    }

    private int countCurrentLeaves() {
        int count = 0;
        for (DungeonLeaf leaf : allLeaves) {
            if (leaf.isLeaf()) {
                count++;
            }
        }
        return count;
    }

    private void collectFinalLeaves() {
        finalLeaves.clear();
        for (DungeonLeaf leaf : allLeaves) {
            if (leaf.isLeaf()) {
                finalLeaves.add(leaf);
            }
        }
    }

    /**
     * Creates a printable text representation of the final BSP leaves.
     * Each tile is represented by a character:
     * '.' = empty space
     * '#' = boundary of a final leaf
     *
     * @return a string showing the dungeon leaf layout
     */
    public String debugDrawLeaves() {
        char[][] grid = new char[mapHeight][mapWidth];

        // Fill everything with empty space
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                grid[y][x] = '.';
            }
        }

        // Make sure finalLeaves is accurate
        finalLeaves.clear();
        for (DungeonLeaf leaf : allLeaves) {
            if (leaf.isLeaf()) {
                finalLeaves.add(leaf);
            }
        }

        // Draw each final leaf as a rectangle outline
        for (DungeonLeaf leaf : finalLeaves) {
            int startX = leaf.getX();
            int startY = leaf.getY();
            int endX = startX + leaf.getWidth() - 1;
            int endY = startY + leaf.getHeight() - 1;

            // Top and bottom borders
            for (int x = startX; x <= endX; x++) {
                if (isInBounds(x, startY)) {
                    grid[startY][x] = '#';
                }
                if (isInBounds(x, endY)) {
                    grid[endY][x] = '#';
                }
            }

            // Left and right borders
            for (int y = startY; y <= endY; y++) {
                if (isInBounds(startX, y)) {
                    grid[y][startX] = '#';
                }
                if (isInBounds(endX, y)) {
                    grid[y][endX] = '#';
                }
            }
        }

        // Convert grid to a printable string
        StringBuilder output = new StringBuilder();
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                output.append(grid[y][x]);
            }
            output.append('\n');
        }

        return output.toString();
    }

    /**
     * Checks whether a coordinate is inside the map bounds.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the coordinate is valid, false otherwise
     */
    private boolean isInBounds(final int x, final int y) {
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
    }

    /**
     * Places actual rooms inside the final BSP leaves.
     */
    private void placeRoomsInLeaves() {
        // TODO:
        // Later:
        // 1. Pick a room rectangle inside each final leaf.
        // 2. Either draw placeholder rooms or copy TMX templates.
    }

    /**
     * Connects rooms with corridors.
     */
    private void connectRooms() {
        // TODO:
        // Later:
        // Use BSP relationships or room centers to carve corridors.
    }

    /**
     * Assigns special room types such as start, boss, or treasure.
     */
    private void assignSpecialRooms() {
        // TODO:
        // Later:
        // decide which final leaves become special rooms
    }
}
