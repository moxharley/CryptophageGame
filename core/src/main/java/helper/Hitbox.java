package helper;

public class Hitbox {

    private float x;
    private float y;

    private float width;
    private float height;

    public Hitbox(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(final float x, final float y) {
        setX(x);
        setY(y);
    }

    public boolean checkForCollision (Hitbox other) {
        return getX() < other.getX() + other.getWidth()
            && getY() < other.getY() + other.getHeight()
            && getX() + getWidth() > other.getX()
            && getY() + getHeight() > other.getY();
    }









    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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
}
