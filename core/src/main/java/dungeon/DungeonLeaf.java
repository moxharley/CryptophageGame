package dungeon;

import java.util.Random;

/**
 * Represents a single node in a BSP dungeon tree.
 * A rectangular section of dungeon space and is
 * split into two child branches.
 *
 * @author Harlan Bullock
 * @version 2026
 */
public class DungeonLeaf {
    public static final int MINIMUM_CHILD_MEASUREMENT = 16;

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private DungeonLeaf leftChild;
    private DungeonLeaf rightChild;

    /**
     * Constructs a dungeon lead with the given bounds.
     *
     * @param x the x-coordinate of the leaf
     * @param y the y-coordinate of the leaf
     * @param width the width of the leaf
     * @param height the height of the leaf
     */
    public DungeonLeaf(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * Returns a string representation of this leaf for debugging.
     *
     * @return a formatted string showing bounds and child status
     */
    @Override
    public String toString() {
        return "DungeonLeaf{"
            + "x=" + x
            + ", y=" + y
            + ", width=" + width
            + ", height=" + height
            + ", hasLeftChild=" + (leftChild != null)
            + ", hasRightChild=" + (rightChild != null)
            + "}";
    }

    /**
     * Returns the x-coordinate of this leaf.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this leaf.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the width of this leaf.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of this leaf.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the left child of this leaf.
     *
     * @return the left child, or null if none exists
     */
    public DungeonLeaf getLeftChild() {
        return leftChild;
    }

    /**
     * Returns the right child of this leaf.
     *
     * @return the right child, or null if none exists
     */
    public DungeonLeaf getRightChild() {
        return rightChild;
    }

    /**
     * Sets the left child of this leaf.
     *
     * @param leftChild the new left child
     */
    public void setLeftChild(final DungeonLeaf leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Sets the right child of this leaf.
     *
     * @param rightChild the new right child
     */
    public void setRightChild(final DungeonLeaf rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Determines whether this node is currently a leaf.
     *
     * @return true if this node has no children, false otherwise
     */
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    /**
     * Creates a left and right child for the current leaf node.
     *
     * @return true if successful, false otherwise
     */
    public boolean split() {
        // 1. If already split, or the room isn't large enough, stop
        if (!isLeaf() || !isLargeEnoughToSplit()) {
            return false;
        }
        // 2. Decide split direction
        boolean splitDirection = splitDirection();
        // 3. Determine split location
        int splitPosition = splitPosition(splitDirection);
        // 4. Create child leaves
        if (splitDirection) {
            leftChild = new DungeonLeaf(x, y, splitPosition, height);
            rightChild = new DungeonLeaf((x + splitPosition), y, (width - splitPosition), height);
        } else {
            leftChild = new DungeonLeaf(x, y, width, splitPosition);
            rightChild = new DungeonLeaf(x, (y + splitPosition), width, (height - splitPosition));
        }
        return true;
    }

    /*
     * Determines if the leaf is large enough to split further or not.
     */
    private boolean isLargeEnoughToSplit() {
        return this.width >= 2 * MINIMUM_CHILD_MEASUREMENT || this.height >= 2 * MINIMUM_CHILD_MEASUREMENT;
    }

    /*
     * Determines the direction this leaf will split.
     */
    private boolean splitDirection() {
        if (this.width < 2 * MINIMUM_CHILD_MEASUREMENT) {
            return false;
        } else if (this.height < 2 * MINIMUM_CHILD_MEASUREMENT) {
            return true;
        } else {
            Random randomizer = new Random();
            return randomizer.nextBoolean();
        }
    }

    /*
     * Determines the position where the leaf is going to be split.
     */
    private int splitPosition(final boolean splitDirection) {
        Random randomizer = new Random();
        int minBound = MINIMUM_CHILD_MEASUREMENT;
        int maxBound;
        if (splitDirection) {
            maxBound = this.width - MINIMUM_CHILD_MEASUREMENT;
        } else {
            maxBound = this.height - MINIMUM_CHILD_MEASUREMENT;
        }
        if (minBound == maxBound) {
            return minBound;
        }
        return randomizer.nextInt(minBound, (maxBound + 1));
    }
}
