package objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import helper.Hitbox;

/**
 * Represents a projectile created by a player or enemy.
 *
 * @author FinnWylie
 * @version 2026
 */

public class NewProjectile {

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

    protected Body body;

    private World world;

    private int size;

    private Hitbox hitbox;

    public NewProjectile(ProjectileShape projectileShape, ProjectileColour projectileColour,
                         ProjectileTeam projectileTeam, float damage, float speed,
                         int lifespan, Vector2 bulletPosition, Vector2 bulletDirection,
                         final World world, final int size) {


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

        // world
        this.world = world;

        // size
        this.size = size;

        // texture
        switch (projectileShape) {
            case CIRCLE:
                this.texture = new Texture("../assets/projectiles/circle_bullet.png");
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
        sprite.setPosition(getBulletPosition().x, getBulletPosition().y);

        this.hitbox = new Hitbox(getBulletPosition().x,
            getBulletPosition().y, getSize(), getSize());
    }

    public boolean checkForCollision(final Hitbox otherHitbox) {
        return hitbox.checkForCollision(otherHitbox);
    }

    public void update() {
        bulletPosition.add(bulletDirection);
        hitbox.move(getBulletPosition().x, getBulletPosition().y);
    }

    public void render(final SpriteBatch batch) {
        batch.draw(texture, getBulletPosition().x, getBulletPosition().y, getSize(), getSize());
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
}
