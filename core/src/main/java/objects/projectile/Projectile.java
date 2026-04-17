package objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import helper.Hitbox;

/**
 * Represents a projectile created by a player or enemy.
 *
 * @author FinnWylie
 * @version 2026
 */
public class Projectile {

    private final ProjectileShape projectileShape;
    private final ProjectileColour projectileColour;
    private final ProjectileTeam projectileTeam;
    private final Texture texture;
    private final Sprite sprite;
    private final float damage;
    private final float speed;
    private final Vector2 bulletDirection;
    private Vector2 bulletPosition;
    private final int lifespan;
    private boolean remove;
    private final int size;
    private Hitbox hitbox;

    /**
     * Creates a new projectile.
     * @param projectileShape of the projectile being created as a ProjectileShape
     * @param projectileColour of the projectile being created as a ProjectileColour
     * @param projectileTeam of the projectile being created as a ProjectileTeam
     * @param damage of the projectile being created as a float
     * @param speed of the projectile being created as a float
     * @param lifespan of the projectile being created as an int
     * @param bulletPosition of the projectile being created as a Vector2
     * @param bulletDirection of the projectile being created as a Vector2
     * @param size of the projectile being created as an int
     */
    public Projectile(ProjectileShape projectileShape, ProjectileColour projectileColour,
                      ProjectileTeam projectileTeam, float damage, float speed,
                      int lifespan, Vector2 bulletPosition, Vector2 bulletDirection,
                      final int size) {

        // deletion flag
        remove = false;

        // shape
        if (projectileShape == null) {
            throw new IllegalArgumentException("projectileShape must be a valid ProjectileShape");
        } else {
            this.projectileShape = projectileShape;
        }

        // colour
        if (projectileColour == null) {
            throw new IllegalArgumentException("projectileColour must be a valid ProjectileColour");
        } else {
            this.projectileColour = projectileColour;
        }

        // team
        if (projectileTeam == null) {
            throw new IllegalArgumentException("projectileTeam must be a valid ProjectileTeam");
        } else {
            this.projectileTeam = projectileTeam;
        }

        // damage
        if (damage < 0) {
            throw new IllegalArgumentException("damage must not be negative");
        } else {
            this.damage = damage;
        }

        // speed
        this.speed = speed;

        // lifespan
        if (lifespan < 0) {
            throw new IllegalArgumentException("lifespan must not be negative");
        } else {
            this.lifespan = lifespan;
        }

        // position Vector2
        this.bulletPosition = bulletPosition;

        // direction
        this.bulletDirection = bulletDirection;

        // size
        this.size = size;

        // texture
        if (projectileShape == ProjectileShape.CIRCLE) {
            if (projectileColour == ProjectileColour.RED) {
                this.texture = new Texture("../assets/projectiles/circle_bullet_red.png");
            } else if (projectileColour == ProjectileColour.BLUE) {
                this.texture = new Texture("../assets/projectiles/circle_bullet_blue.png");
            } else {
                this.texture = new Texture("../assets/projectiles/rectangle_bullet.png");
            }
        } else {
            this.texture = new Texture("../assets/projectiles/rectangle_bullet.png");
//            case LINE:
//                texture = b;
//                break;
//            case PILL:
//                texture = c;
//                break;
//            case BULLET:
//                texture = d;
//                break;
//            case CIRCLE:
//                texture = e;
//                break;
//            case RECTANGLE:
//                texture = f;
//                break;
        }

        this.sprite = new Sprite(texture);

        sprite.setPosition(getBulletPosition().x - ((float) getSize() / 2),
                           getBulletPosition().y - ((float) getSize() / 2));

        this.hitbox = new Hitbox(getBulletPosition().x - ((float) getSize() / 2),
                                 getBulletPosition().y - ((float) getSize() / 2),
                                 getSize(), getSize());
    }

    /**
     * Checks if this bullet's hitbox is colliding with another hitbox.
     * @param otherHitbox the hitbox to check if this is colliding with
     * @return if the two Hitboxes are colliding or not as a boolean
     */
    public boolean checkForCollision(final Hitbox otherHitbox) {
        return hitbox.checkForCollision(otherHitbox);
    }

    /**
     * Updates the position of this projectile and it's hitbox. Designed to be called each frame.
     */
    public void update() {
        setBulletPosition(getBulletPosition().add(new Vector2(getBulletDirection().x * speed,
                                                              getBulletDirection().y * speed)));

        hitbox.move(getBulletPosition().x - ((float) getSize() / 2), // adjust by 1/2 of size to compensate for differing origin points
                       getBulletPosition().y - ((float) getSize() / 2));
    }

    /**
     * Renders this projectile's texture at its location.
     * @param batch the SpriteBatch to render this texture in as a SpriteBatch
     */
    public void render(final SpriteBatch batch) {
        batch.draw(texture, getBulletPosition().x - ((float) getSize() / 2),
                            getBulletPosition().y - ((float) getSize() / 2), getSize(), getSize());
    }

    private Vector2 getBulletPosition() {
        return bulletPosition;
    }

    private Vector2 getBulletDirection() {
        return bulletDirection;
    }

    private void setBulletPosition(Vector2 bulletPosition) {
        this.bulletPosition = bulletPosition;
    }

    private int getSize() {
        return size;
    }

    /**
     * Gets this Projectile's Hitbox.
     * @return this Projectile's Hitbox as a Hitbox
     */
    public Hitbox getHitbox() {
        return hitbox;
    }

    /**
     * Gets this Projectile's projectileTeam.
     * @return this Projectile's projectileTeam as a ProjectileTeam
     */
    public ProjectileTeam getProjectileTeam() {
        return projectileTeam;
    }

    /**
     * Gets this Projectile's damage value.
     * @return this Projectile's damage value as a float
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Gets this Projectile's remove flag.
     * @param remove this Projectile's new remove flag as a boolean
     */
    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    /**
     * Gets this Projectile's remove flag.
     * @return this Projectile's remove flag as a boolean
     */
    public boolean isRemove() {
        return remove;
    }
}
