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

    /**
     * Returns this GameEntity's X coordinate.
     * @return this GameEntity's X coordinate as a float
     */
    public float getX() {
        return x;
    }

    /**
     * Sets this GameEntity's X Coordinate to a new X Coordinate.
     * @param x the new X coordinate as a float
     */
    public void setX(final float x) {
        this.x = x;
    }

    /**
     * Returns this GameEntity's Y coordinate.
     * @return this GameEntity's Y coordinate as a float
     */
    public float getY() {
        return y;
    }

    /**
     * Sets this GameEntity's Y Coordinate to a new Y Coordinate.
     * @param y the new Y coordinate as a float
     */
    public void setY(final float y) {
        this.y = y;
    }

    /**
     * Returns this GameEntity's Body.
     * @return this GameEntity's Body as a Body
     */
    public Body getBody() {
        return body;
    }

    /**
     * Returns this GameEntity's width.
     * @return this GameEntity's width as a float
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns this GameEntity's height.
     * @return this GameEntity's height as a float
     */
    public float getHeight() {
        return height;
    }

    /**
     * Returns this GameEntity's hitbox.
     * @return this GameEntity's hitbox as a Hitbox
     */
    public Hitbox getHitbox() {
        return hitbox;
    }

    /**
     * Moves this GameEntity's hitbox to it's body's location.
     */
    public void moveHitbox() {
        hitbox.move(getX() - (getWidth() / 2), getY() - (getHeight() / 2));
    }

    /**
     * Returns this GameEntity's X velocity.
     * @return this GameEntity's X velocity as a float
     */
    public float getVelX() {
        return velX;
    }

    /**
     * Sets this GameEntity's X velocity to a new X velocity.
     * @param velX the new X velocity as a float
     */
    public void setVelX(final float velX) {
        this.velX = velX;
    }

    /**
     * Returns this GameEntity's Y velocity.
     * @return this GameEntity's Y velocity as a float
     */
    public float getVelY() {
        return velY;
    }

    /**
     * Sets this GameEntity's Y velocity to a new Y velocity.
     * @param velY the new Y velocity as a float
     */
    public void setVelY(final float velY) {
        this.velY = velY;
    }

    /**
     * Returns this GameEntity's speed.
     * @return this GameEntity's speed as a float
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the max speed of this GameEntity.
     * @param speed the max speed of this game entity as a float.
     */
    public void setSpeed(final float speed) {
        this.speed = speed;
    }

    /**
     * Returns if this entity is dead.
     * @return if this entity is dead as a boolean
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Sets this GameEntity's death state to a new death state.
     * @param dead if this GameEntity is dead or not as a boolean
     */
    public void setDead(final boolean dead) {
        isDead = dead;
    }

    /**
     * Returns this GameEntity's sprite.
     * @return this GameEntity's sprite as a Sprite
     */
    public Sprite getSprite() {
        return sprite;
    }
}
