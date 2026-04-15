package dungeon;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.utils.Array;

/**
 * A generator for the dungeon layout using BSP partitioning.
 *
 * @author Harlan Bullock
 * @version 2026
 */
public class DungeonGenerator {
    // The following are initialized in create() within MyGameRoot
    public static Array<DungeonRoomTemplate> ROOMS_S; // A list of all the room templates (16x16)
    public static Array<DungeonRoomTemplate> ROOMS_MV; // A list of all the room templates (16x24)
    public static Array<DungeonRoomTemplate> ROOMS_MH; // A list of all the room templates (24x16)
    public static Array<DungeonRoomTemplate> ROOMS_L; // A list of all the room templates (24x24)

    public static final int STANDARD_WIDTH = 96; // Tiles
    public static final int STANDARD_HEIGHT = 96; // Tiles
    public static final int STANDARD_ROOM_COUNT = 12; // Leaves
    public static final int TILE_SIZE = 16; // Pixels

    // Dungeon Configuration
    private final int mapWidth;
    private final int mapHeight;
    private final int roomCount;

    // Dungeon Randomizer
    private final Random randomizer;

    // Dungeon State
    private TiledMap dungeonMap;
    private DungeonLeaf rootLeaf;
    private final ArrayList<DungeonLeaf> allLeaves;
    private final ArrayList<DungeonLeaf> finalLeaves;
    private ArrayList<PlacedRoom> placedRooms;

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
        this.placedRooms = new ArrayList<>();
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
        this.placedRooms = new ArrayList<>();
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
     * @return the array list of all final dungeon leaves
     */
    public ArrayList<DungeonLeaf> getFinalLeaves() {
        return finalLeaves;
    }

    // -- THE JUICY STUFF --

    public void generate(final long seed) {
        randomizer.setSeed(seed);

        clearState();
        createEmptyMap();
        createRootLeaf();
        splitLeaves();
        placeRoomsInLeaves();
        bakeRoomsToMap();

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
        placedRooms.clear();
    }

    /**
     * Creates an empty tiled map that the dungeon will be drawn into.
     */
    private void createEmptyMap() {
        dungeonMap = new TiledMap();
        MapLayers layers = dungeonMap.getLayers();

        TiledMapTileLayer base = new TiledMapTileLayer(mapWidth, mapHeight, TILE_SIZE, TILE_SIZE);
        base.setName("base");
        layers.add(base);

        TiledMapTileLayer accessories = new TiledMapTileLayer(mapWidth, mapHeight, TILE_SIZE, TILE_SIZE);
        accessories.setName("accessories");
        layers.add(accessories);

        TiledMapTileLayer foreground = new TiledMapTileLayer(mapWidth, mapHeight, TILE_SIZE, TILE_SIZE);
        foreground.setName("foreground");
        layers.add(foreground);

        copyTileSetsFromTemplate();
    }

    private void bakeRoomsToMap() {
        final TiledMapTileLayer baseLayer = (TiledMapTileLayer) dungeonMap.getLayers().get("base");
        final TiledMapTileLayer accessoriesLayer = (TiledMapTileLayer) dungeonMap.getLayers().get("accessories");
        final TiledMapTileLayer foregroundLayer = (TiledMapTileLayer) dungeonMap.getLayers().get("foreground");

        fillLayerWithTile(baseLayer, 231);

        for (PlacedRoom placedRoom : placedRooms) {
            stampLayer(placedRoom, "base", baseLayer);
            stampLayer(placedRoom, "accessories", accessoriesLayer);
            stampLayer(placedRoom, "foreground", foregroundLayer);
        }
    }

    private void fillLayerWithTile(final TiledMapTileLayer layer, final int tileId) {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(findTileById(tileId));
                layer.setCell(x, y, cell);
            }
        }
    }

    private TiledMapTile findTileById(final int tileId) {
        for (TiledMapTileSet tileSet : dungeonMap.getTileSets()) {
            TiledMapTile tile = tileSet.getTile(tileId);
            if (tile != null) {
                return tile;
            }
        }
        throw new IllegalArgumentException("Tile id not found: " + tileId);
    }

    private void stampLayer(final PlacedRoom placedRoom, final String layerName, final TiledMapTileLayer targetLayer) {
        final TiledMap roomMap = placedRoom.getTemplate().getRoom();
        final TiledMapTileLayer sourceLayer = (TiledMapTileLayer) roomMap.getLayers().get(layerName);
        if (sourceLayer == null) {
            throw new IllegalArgumentException("Placed room not found:" + placedRoom);
        }
        final int offsetX = placedRoom.getX();
        final int offsetY = placedRoom.getY();

        for (int y = 0; y < sourceLayer.getHeight(); y++) {
            for (int x = 0; x < sourceLayer.getWidth(); x++) {
                final TiledMapTileLayer.Cell sourceCell = sourceLayer.getCell(x, y);
                if (sourceCell == null || sourceCell.getTile() == null) {
                    continue;
                }
                final TiledMapTileLayer.Cell copiedCell = new TiledMapTileLayer.Cell();
                copiedCell.setTile(sourceCell.getTile());
                copiedCell.setFlipHorizontally(sourceCell.getFlipHorizontally());
                copiedCell.setFlipVertically(sourceCell.getFlipVertically());
                copiedCell.setRotation(sourceCell.getRotation());
                targetLayer.setCell(offsetX + x, offsetY + y, copiedCell);
            }
        }
    }

    private void copyTileSetsFromTemplate() {
        DungeonRoomTemplate sourceTemplate = null;

        if (ROOMS_S != null && ROOMS_S.size > 0) {
            sourceTemplate = ROOMS_S.first();
        } else if (ROOMS_MV != null && ROOMS_MV.size > 0) {
            sourceTemplate = ROOMS_MV.first();
        } else if (ROOMS_MH != null && ROOMS_MH.size > 0) {
            sourceTemplate = ROOMS_MH.first();
        } else if (ROOMS_L != null && ROOMS_L.size > 0) {
            sourceTemplate = ROOMS_L.first();
        }

        if (sourceTemplate == null) {
            throw new IllegalStateException("No room templates loaded");
        }

        dungeonMap.getTileSets().addTileSet(
            sourceTemplate.getRoom().getTileSets().getTileSet(0)
        );
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
                grid[y][x] = ' ';
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
                    grid[startY][x] = '-';
                }
                if (isInBounds(x, endY)) {
                    grid[endY][x] = '-';
                }
            }

            // Left and right borders
            for (int y = startY; y <= endY; y++) {
                if (isInBounds(startX, y)) {
                    grid[y][startX] = '|';
                }
                if (isInBounds(endX, y)) {
                    grid[y][endX] = '|';
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
        placedRooms.clear();

        for (DungeonLeaf leaf : finalLeaves) {
            DungeonRoomTemplate template = chooseTemplateForLeaf(leaf);

            if (template == null) {
                System.out.println("No template fits this leaf:" + leaf);
                continue;
            }

            int roomX = randomPlacementX(leaf, template);
            int roomY = randomPlacementY(leaf, template);

            PlacedRoom placedRoom = new PlacedRoom(template, leaf, roomX, roomY);
            placedRooms.add(placedRoom);

            System.out.println("Placed room: " + placedRoom);
        }
    }

    /**
     * Chooses a random template based on leaf size.
     *
     * @param leaf the leaf container
     * @return the room template chosen or null
     */
    private DungeonRoomTemplate chooseTemplateForLeaf(DungeonLeaf leaf) {
        if (canFitAny(ROOMS_L, leaf)) {
            return randomTemplateFrom(ROOMS_L);
        }
        if (canFitAny(ROOMS_MV, leaf)) {
            return randomTemplateFrom(ROOMS_MV);
        }
        if (canFitAny(ROOMS_MH, leaf)) {
            return randomTemplateFrom(ROOMS_MH);
        }
        if (canFitAny(ROOMS_S, leaf)) {
            return randomTemplateFrom(ROOMS_S);
        }
        return null;
    }

    private boolean canFitAny(final Array<DungeonRoomTemplate> templates,
                              final DungeonLeaf leaf) {
        if (templates == null || templates.size == 0) {
            return false;
        }

        for (DungeonRoomTemplate template : templates) {
            if (template.fitsIn(leaf)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Randomly selects a template from a list of templates.
     *
     * @param templates list of templates
     * @return the randomly selected template, or null
     */
    private DungeonRoomTemplate randomTemplateFrom(final Array<DungeonRoomTemplate> templates) {
        if (templates == null || templates.size == 0) {
            return null;
        }
        return templates.get(randomizer.nextInt(templates.size));
    }

    /**
     * Randomly chooses room x placement within the leaf.
     *
     * @param leaf the leaf container
     * @param template the room template
     * @return the x placement
     */
    private int randomPlacementX(final DungeonLeaf leaf, final DungeonRoomTemplate template) {
        int minX = leaf.getX();
        int maxX = leaf.getX() + leaf.getWidth() - template.getWidth();

        if (minX == maxX) {
            return minX;
        }

        return randomizer.nextInt(minX, maxX + 1);
    }

    /**
     * Randomly chooses room y placement within the leaf.
     *
     * @param leaf the leaf container
     * @param template the room template
     * @return the y placement
     */
    private int randomPlacementY(final DungeonLeaf leaf, final DungeonRoomTemplate template) {
        int minY = leaf.getY();
        int maxY = leaf.getY() + leaf.getHeight() - template.getHeight();

        if (minY == maxY) {
            return minY;
        }

        return randomizer.nextInt(minY, maxY + 1);
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
