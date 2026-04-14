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
    private final Vector2 bulletPosition;
    private int lifespan;
    private boolean remove;
    private int size;
    private Hitbox hitbox;

    public Projectile(ProjectileShape projectileShape, ProjectileColour projectileColour,
                      ProjectileTeam projectileTeam, float damage, float speed,
                      int lifespan, Vector2 bulletPosition, Vector2 bulletDirection,
                      final int size) {


        // deletion flag
        remove = false;

        // shape
        //TODO: check direction and position, once projectile direction system is figured out
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
        switch (projectileShape) {
            case CIRCLE:
                if (projectileColour == ProjectileColour.RED) {
                    this.texture = new Texture("../assets/projectiles/circle_bullet_red.png");
                } else if (projectileColour == ProjectileColour.BLUE) {
                    this.texture = new Texture("../assets/projectiles/circle_bullet_blue.png");
                } else {
                    this.texture = new Texture("../assets/projectiles/rectangleRadians_bullet.png");
                }
                break;
            default:
                this.texture = new Texture("../assets/projectiles/rectangleRadians_bullet.png");
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

    public boolean checkForCollision(final Hitbox otherHitbox) {
        return hitbox.checkForCollision(otherHitbox);
    }

    public void update() {
        bulletPosition.add(new Vector2(getBulletDirection().x * speed,
                                       getBulletDirection().y * speed));
        hitbox.move(getBulletPosition().x - ((float) getSize() / 2), // adjust by 1/2 of size to compensate for differing origin points
                    getBulletPosition().y - ((float) getSize() / 2));
    }

    public void render(final SpriteBatch batch) {
        batch.draw(texture, getBulletPosition().x - ((float) getSize() / 2),
                            getBulletPosition().y - ((float) getSize() / 2), getSize(), getSize());
    }

    public Vector2 getBulletPosition() {
        return bulletPosition;
    }

    public Vector2 getBulletDirection() {
        return bulletDirection;
    }

    public int getSize() {
        return size;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public ProjectileTeam getProjectileTeam() {
        return projectileTeam;
    }

    public float getDamage() {
        return damage;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isRemove() {
        return remove;
    }
}
