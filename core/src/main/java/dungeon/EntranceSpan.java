package dungeon;

public class EntranceSpan {
    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;

    public EntranceSpan(final int minX, final int minY, final int maxX, final int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Returns the width of this entrance span in tiles.
     *
     * @return the width in tiles
     */
    public int getWidth() {
        return maxX - minX + 1;
    }

    /**
     * Returns the height of this entrance span in tiles.
     *
     * @return the height in tiles
     */
    public int getHeight() {
        return maxY - minY + 1;
    }

    /**
     * Returns the amount of overlap with another span on the x-axis.
     *
     * @param that the entrance span to compare
     * @return the width of the horizontal overlap
     */
    public int getHorizontalOverlap(final EntranceSpan that) {
        final int overlapMin = Math.max(this.minX, that.minX);
        final int overlapMax = Math.min(this.maxX, that.maxX);
        return Math.max(0, overlapMax - overlapMin + 1);
    }

    /**
     * Returns the amount of overlap with another span on the y-axis.
     *
     * @param that the entrance span to compare
     * @return the width of vertical overlap
     */
    public int getVerticalOverlap(final EntranceSpan that) {
        final int overlapMin = Math.max(this.minY, that.minY);
        final int overlapMax = Math.min(this.maxY, that.maxY);
        return Math.max(0, overlapMax - overlapMin + 1);
    }
}
