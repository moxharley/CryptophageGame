package helper;

public class Hitbox {

    private float originX;
    private float originY;

    private float width;
    private float height;

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

    public void move(final float x, final float y) {
        setOriginX(x);
        setOriginY(y);
    }

    public boolean checkForCollision (Hitbox other) {
        return getOriginX() < other.getOriginX() + other.getWidth()
            && getOriginY() < other.getOriginY() + other.getHeight()
            && getOriginX() + getWidth() > other.getOriginX()
            && getOriginY() + getHeight() > other.getOriginY();
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
