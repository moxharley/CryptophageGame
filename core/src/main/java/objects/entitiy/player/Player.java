package objects.entitiy.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import objects.entitiy.GameEntity;
import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_ATTACK_SPEED;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_ATTACK_SPEED_MODIFIER;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_BULLET_SPEED;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_BULLET_SPEED_MODIFIER;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_DAMAGE;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_DAMAGE_MODIFIER;
import static objects.entitiy.player.TechknightPlayerEntityConstants.DEFAULT_MAX_HEALTH_POINTS;
import static objects.entitiy.player.TechknightPlayerEntityConstants.PLAYER_TEXTURE;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_MOVE_SPEED;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_BULLET_LIFESPAN;
import static objects.entitiy.player.TechknightPlayerEntityConstants.DEFAULT_PROJECTILE_SIZE;
import static objects.entitiy.player.TechknightPlayerEntityConstants.DEFAULT_PROJECTILE_SHAPE;
import static objects.entitiy.player.TechknightPlayerEntityConstants.DEFAULT_PROJECTILE_COLOUR;
import static objects.entitiy.player.TechknightPlayerEntityConstants.DEFAULT_PROJECTILE_TEAM;

/**
 * Represents a Player in the game.
 * @author FinnWylie
 * @version 2026
 */
public class Player extends GameEntity {

    private final int attackSpeed;
    private final float attackSpeedModifier;
    private final float damage;
    private final float damageModifier;
    private final float bulletSpeed;
    private final float bulletSpeedModifier;
    private final int bulletLifespan;
    private final int projectileSize;
    private final ProjectileShape projectileShape;
    private final ProjectileColour projectileColour;
    private final ProjectileTeam projectileTeam;

    private int currentHealthPoints;
    private int timeSinceLastShot;

    /**
     * Constructs a new player with the default values and a custom width, height and body.
     * @param width width of the player as a float
     * @param height height of the player as a float
     * @param body the Box2D body of the player as a Body
     */
    public Player(final float width, final float height, final Body body) {
        super(width, height, body, PLAYER_TEXTURE);

        setSpeed(BASE_MOVE_SPEED);

        this.currentHealthPoints = DEFAULT_MAX_HEALTH_POINTS;

        this.attackSpeed = BASE_ATTACK_SPEED;
        this.attackSpeedModifier = BASE_ATTACK_SPEED_MODIFIER;
        this.damage = BASE_DAMAGE;
        this.damageModifier = BASE_DAMAGE_MODIFIER;
        this.bulletSpeed = BASE_BULLET_SPEED;
        this.bulletSpeedModifier = BASE_BULLET_SPEED_MODIFIER;
        this.bulletLifespan = BASE_BULLET_LIFESPAN;

        this.projectileSize = DEFAULT_PROJECTILE_SIZE;

        this.projectileColour = DEFAULT_PROJECTILE_COLOUR;
        this.projectileShape = DEFAULT_PROJECTILE_SHAPE;
        this.projectileTeam = DEFAULT_PROJECTILE_TEAM;

        timeSinceLastShot = 0;
    }

    /**
     * Updates this player object. It is designed to be called every frame.
     */
    @Override
    public void update() {
        // makes the body move based on velocity and speed
        getBody().setLinearVelocity(getVelX() * getSpeed(), getVelY() * getSpeed());

        // move x & y to the current body position
        // x & y will be in the centre of our body
        setX(getBody().getPosition().x);
        setY(getBody().getPosition().y);

        moveHitbox();

        setTimeSinceLastShot(getTimeSinceLastShot() + 1);

        if (getCurrentHealthPoints() <= 0) {
            setDead(true);
        }
    }

    /**
     * Renders the sprite associated with this Player at the location of it's X and Y coordinates.
     * @param batch the batch to render this GameEntity in as a SpriteBatch
     */
    @Override
    public void render(final SpriteBatch batch) {
        batch.draw(getSprite(), getX() - (getWidth() / 2),
            getY() - (getHeight() / 2), getWidth(), getHeight());
    }

    /**
     * Moves this player in a direction.
     * @param horizontalMovement the value to set this player's horizontal velocity by as an int
     * @param verticalMovement the value to set this player's vertical velocity by as an int
     */
    public void move(final int horizontalMovement, final int verticalMovement) {
        setVelX(horizontalMovement);
        setVelY(verticalMovement);
    }

    /**
     * If this player's attack is off cooldown the attack timer is reset and true is returned else
     * false.
     * @return if this player was able to attack as a boolean
     */
    public boolean attackIfAllowed() {
        if (getTimeSinceLastShot() >= getAttackSpeed() * getAttackSpeedModifier()) {
            setTimeSinceLastShot(0);
            return true;
        } else {
            return false;
        }
    }

    private int getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    private void setTimeSinceLastShot(final int timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }

    /**
     * Gets this player's projectileSize.
     * @return the projectileSize of this player as a int
     */
    public int getProjectileSize() {
        return projectileSize;
    }

    /**
     * Gets this player's projectileShape.
     * @return the projectileShape of this player as a ProjectileShape
     */
    public ProjectileShape getProjectileShape() {
        return projectileShape;
    }

    /**
     * Gets this player's projectileColour.
     * @return the projectileColour of this player as a ProjectileColour
     */
    public ProjectileColour getProjectileColour() {
        return projectileColour;
    }

    /**
     * Gets this player's projectileTeam.
     * @return the projectileTeam of this player as a ProjectileTeam
     */
    public ProjectileTeam getProjectileTeam() {
        return projectileTeam;
    }

    private int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    private int getAttackSpeed() {
        return attackSpeed;
    }

    private float getAttackSpeedModifier() {
        return attackSpeedModifier;
    }

    /**
     * Gets this player's base attack damage.
     * @return the attack damage of this player as a float
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Gets this player's damage modifier.
     * @return the damage modifier of this player as a float
     */
    public float getDamageModifier() {
        return damageModifier;
    }

    /**
     * Gets this player's bulletSpeed.
     * @return the bulletSpeed of this player as a float
     */
    public float getBulletSpeed() {
        return bulletSpeed;
    }

    /**
     * Gets this player's bulletSpeedModifier.
     * @return the bulletSpeedModifier of this player as a float
     */
    public float getBulletSpeedModifier() {
        return bulletSpeedModifier;
    }

    /**
     * Gets this player's bulletLifespan.
     * @return the bulletLifespan of this player as a int
     */
    public int getBulletLifespan() {
        return bulletLifespan;
    }

    /**
     * Deals damage to this player's current health.
     * @param damage the damage to deal to this player's health as a float.
     */
    public void takeDamage(final float damage) {
        if (damage >= 0) {
            setCurrentHealthPoints(-damage); // ensure damage will reduce health
        } else {
            setCurrentHealthPoints(damage);
        }
    }

    /**
     * Changes this player's health by a value.
     * @param valueToChangeHealthBy the value to be added to this player's health
     */
    private void setCurrentHealthPoints(final float valueToChangeHealthBy) {
        currentHealthPoints += Math.round(valueToChangeHealthBy);
    }
}
