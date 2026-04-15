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

    // will take x & y from body so no need to pass the info twice
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
        moveSprite();

        this.hitbox = new Hitbox(getX(), getY(), getWidth(), getHeight());
        moveHitbox();
    }

    public void update() { }

    public void render(final SpriteBatch batch) { }

    public Texture getTexture() {
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

    public void moveHitbox() {
        hitbox.move(getX() - (getWidth() / 2), getY() - (getHeight() / 2));
    }

    public void moveSprite() {
        sprite.setPosition(getX() - (getWidth() / 2), getY() - (getHeight() / 2));
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

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
