package dungeon;

/**
 * Represents a placed room in the dungeon.
 *
 * @author Harlan Bullock
 * @version 2026
 */
public class PlacedRoom {
    private final DungeonRoomTemplate template;
    private final DungeonLeaf leaf;
    private final int x;
    private final int y;

    /**
     * Instantiates a PlacedRoom with the given parameters.
     *
     * @param template the room template
     * @param leaf the leaf where the room is placed
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public PlacedRoom(final DungeonRoomTemplate template, final DungeonLeaf leaf, final int x, final int y) {
        this.template = template;
        this.leaf = leaf;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string representation of the placed room for debugging.
     *
     * @return placed room debug information
     */
    @Override
    public String toString() {
        return "PlacedRoom{"
            + "x=" + x
            + ", y=" + y
            + ", width=" + getWidth()
            + ", height=" + getHeight()
            + "}";
    }

    /**
     * Returns the room template of the placed room.
     *
     * @return the room template
     */
    public DungeonRoomTemplate getTemplate() {
        return template;
    }

    /**
     * Returns the y coordinate of the placed room.
     *
     * @return the y value
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the x coordinate of the placed room.
     *
     * @return the x value
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the leaf of the placed room.
     *
     * @return the dungeon leaf
     */
    public DungeonLeaf getLeaf() {
        return leaf;
    }

    /**
     * Returns the width of the placed room.
     *
     * @return the width
     */
    public int getWidth() {
        return template.getWidth();
    }

    /**
     * Returns the height of the placed room.
     *
     * @return the height
     */
    public int getHeight() {
        return template.getHeight();
    }
}
