package helper;

/**
 * Represents a hitbox used in this game.
 * @author finnwylie
 * @version 2026
 */
public class Hitbox {

    private float originX;
    private float originY;

    private final float width;
    private final float height;

    private int age;

    /**
     * Creates a new rectangular hitbox with the origin in the bottom left corner.
     *
     * @param originX origin point of the hitbox in the X axis as a float
     * @param originY origin point of the hitbox in the Y axis as a float
     * @param width width of the hitbox (in the X axis) as a float
     * @param height height of the hitbox (in the Y axis) as a float
     */
    public Hitbox(final float originX, final float originY, final float width, final float height) {
        this.originX = originX;
        this.originY = originY;
        this.width = width;
        this.height = height;
        this.age = 0;
    }

    /**
     * Moves the hitbox to a new location.
     * @param x the X coordinate to move this hitbox to as a float.
     * @param y the Y coordinate to move this hitbox to as a float.
     */
    public void move(final float x, final float y) {
        setOriginX(x);
        setOriginY(y);
    }

    /**
     * Compares two hitboxes locations and dimensions to see if they have overlap.
     * @param other the other hitbox to compare to as a Hitbox
     * @return if the hitbox overlaps this Hitbox as a boolean
     */
    public boolean checkForCollision(final Hitbox other) {
        return getOriginX() < other.getOriginX() + other.getWidth()
            && getOriginY() < other.getOriginY() + other.getHeight()
            && getOriginX() + getWidth() > other.getOriginX()
            && getOriginY() + getHeight() > other.getOriginY();
    }


    /**
     * Gets this hitbox's X origin coordinate.
     * @return the X origin coordinate of this hitbox as a float
     */
    public float getOriginX() {
        return originX;
    }

    /**
     * Gets this hitbox's Y origin coordinate.
     * @return the Y origin coordinate of this hitbox as a float
     */
    public float getOriginY() {
        return originY;
    }

    /**
     * Gets this hitbox's width.
     * @return the width of this hitbox as a float
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets this hitbox's height.
     * @return the height of this hitbox as a float
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets this hitbox's age.
     * @return the age of this hitbox as an int
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets this Hitbox's age to a new age.
     * @param age the new age of this hitbox as an int
     */
    public void setAge(final int age) {
        this.age = age;
    }

    private void setOriginX(final float originX) {
        this.originX = originX;
    }

    private void setOriginY(final float originY) {
        this.originY = originY;
    }
}
