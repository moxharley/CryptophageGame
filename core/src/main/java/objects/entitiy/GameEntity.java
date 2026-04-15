package objects.entitiy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import helper.Hitbox;

/**
 * Represents a GameEntity.
 * @author FinnWylie
 * @version 2026
 */
public class GameEntity {

    private float x;
    private float y;
    private float velX;
    private float velY;
    private float speed;

    private float width;
    private float height;

    private boolean isDead;

    private Body body;
    private Hitbox hitbox;
    private Texture texture;
    private Sprite sprite;

    /**
     * Creates a new gameEntity. The X and Y coordinates will come from the body's X and Y values
     * @param width the width of the entity as a float
     * @param height the height of the entity as a float
     * @param body the body of the entity as a body
     * @param texture the texture of the entity as a texture
     */
    public GameEntity(final float width, final float height, final Body body, final Texture texture) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;

        this.width = width;
        this.height = height;
        this.body = body;

        this.isDead = false;

        this.velX = 0;
        this.velY = 0;
        this.speed = 0;

        this.texture = texture;
        this.sprite = new Sprite(getTexture());

        this.hitbox = new Hitbox(getX(), getY(), getWidth(), getHeight());
        moveHitbox();
    }

    /**
     * Updates this GameEntity.
     */
    public void update() { }

    /**
     * Renders this GameEntity.
     * @param batch the batch to render this GameEntity in as a SpriteBatch
     */
    public void render(final SpriteBatch batch) { }

    private Texture getTexture() {
        return texture;
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

    public Body getBody() {
        return body;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    /**
     * Moves this GameEntity's hitbox to it's body's location.
     */
    public void moveHitbox() {
        hitbox.move(getX() - (getWidth() / 2), getY() - (getHeight() / 2));
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the max speed of this GameEntity.
     * @param speed the max speed of this game entity as a float.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
