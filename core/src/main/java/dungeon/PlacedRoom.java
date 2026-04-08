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

    public PlacedRoom(final DungeonRoomTemplate template, final DungeonLeaf leaf, final int x, final int y) {
        this.template = template;
        this.leaf = leaf;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "PlacedRoom{"
            + "x=" + x
            + ", y=" + y
            + ", width=" + getWidth()
            + ", height=" + getHeight()
            + "}";
    }

    public DungeonRoomTemplate getTemplate() {
        return template;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public DungeonLeaf getLeaf() {
        return leaf;
    }

    public int getWidth() {
        return template.getWidth();
    }

    public int getHeight() {
        return template.getHeight();
    }
}
