package objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import helper.ProjectileBodyHelperService;

import static helper.GameConstants.PPM;

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

    private final float damage;
    private final float speed;

    private final float radians;

    private float positionX;
    private float positionY;

    private int lifespan;

    private boolean remove;

    protected Body body;

    private World world;

    private int size;

    public Projectile(ProjectileShape projectileShape, ProjectileColour projectileColour,
                      ProjectileTeam projectileTeam, float damage, float speed,
                      int lifespan, float positionX, float positionY, float radians, final World world, final int size) {

        //TODO: check direction and position, once projectile direction system is figured out
        this.radians = radians;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;

        this.world = world;

        body = ProjectileBodyHelperService.createBody(positionX, positionY, size, world, projectileShape);
        setStartingVelocity(getBody(), getSpeed(), getRadians());
        body.setBullet(true);

        if (projectileShape == null) {
            throw new IllegalArgumentException("projectileShape must be a valid ProjectileShape");
        } else {
            this.projectileShape = projectileShape;
        }
        switch (projectileShape) {
            case CIRCLE:
                this.texture = new Texture("../assets/projectiles/circle_bullet.png");
                break;
            default:
                this.texture = new Texture("../assets/projectiles/rectradians_bullet.png");
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

        if (projectileColour == null) {
            throw new IllegalArgumentException("projectileColour must be a valid ProjectileColour");
        } else {
            this.projectileColour = projectileColour;
        }
        if (projectileTeam == null) {
            throw new IllegalArgumentException("projectileTeam must be a valid ProjectileTeam");
        } else {
            this.projectileTeam = projectileTeam;
        }

        if (damage < 0) {
            throw new IllegalArgumentException("damage must not be negative");
        } else {
            this.damage = damage;
        }

        if (lifespan < 0) {
            throw new IllegalArgumentException("lifespan must not be negative");
        } else {
            this.lifespan = lifespan;
        }

//        Entity entity = engine.createEntity();

        remove = false;
    }

    private Body getBody() {
        return body;
    }

    private void setStartingVelocity(final Body body, final float speed, final float radians) {
        Vector2 startingVelocity = new Vector2(speed, speed);
        startingVelocity.rotateRad(radians + 180); // TODO: why 45?
        body.setLinearVelocity(startingVelocity);
    }

    // TODO: does this need the param: final float deltaTime
    // TODO: add collision checks here (for walls) and if it should be destroyed
    public void update() {
//        // move x & y to the current body position
//        // x & y will be in the centre of our body
        setPositionX(body.getPosition().x + (PPM * (getSpeed() + 0.1f)));
        setPositionY(body.getPosition().y + (PPM * (getSpeed() + 0.1f)));
//        setPosition(getPosition() + (getSpeed() * getAngle()) * deltaTime);
//        setPositionX(getPositionX() + (getSpeed() * getAngle().x) * deltaTime);
//        setPositionY(getPositionY() + (getSpeed() * getAngle().y) * deltaTime);
//        if (getPositionY() > Gdx.graphics.getHeight()) {
//            remove = true;
//        }
    }

    public void render(final SpriteBatch batch) {
        batch.draw(texture, getPositionX(), getPositionY());
    }

    public float getSpeed() {
        return speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getRadians() {
        return radians;
    }
}
