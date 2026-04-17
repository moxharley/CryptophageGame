package objects.entitiy.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import objects.entitiy.GameEntity;
import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

import java.util.Random;

import static objects.entitiy.enemy.EnemyConstants.BASE_ATTACK_SPEED;
import static objects.entitiy.enemy.EnemyConstants.BASE_ATTACK_SPEED_MODIFIER;
import static objects.entitiy.enemy.EnemyConstants.BASE_BULLET_LIFESPAN;
import static objects.entitiy.enemy.EnemyConstants.BASE_BULLET_SPEED;
import static objects.entitiy.enemy.EnemyConstants.BASE_BULLET_SPEED_MODIFIER;
import static objects.entitiy.enemy.EnemyConstants.BASE_DAMAGE;
import static objects.entitiy.enemy.EnemyConstants.BASE_DAMAGE_MODIFIER;
import static objects.entitiy.enemy.EnemyConstants.BASE_MOVE_SPEED;
import static objects.entitiy.enemy.EnemyConstants.BASE_MOVE_SPEED_MODIFIER;
import static objects.entitiy.enemy.EnemyConstants.DEFAULT_MAX_HEALTH_POINTS;
import static objects.entitiy.enemy.EnemyConstants.DEFAULT_PROJECTILE_COLOUR;
import static objects.entitiy.enemy.EnemyConstants.DEFAULT_PROJECTILE_SHAPE;
import static objects.entitiy.enemy.EnemyConstants.DEFAULT_PROJECTILE_SIZE;
import static objects.entitiy.enemy.EnemyConstants.DEFAULT_PROJECTILE_TEAM;
import static objects.entitiy.enemy.EnemyConstants.ENEMY_1_TEXTURE;


/**
 * Represents an enemy is this game.
 * @author finnwylie
 * @version 2026
 */
public class Enemy extends GameEntity {

    private int currentHealthPoints;

    private final float moveSpeed;
    private final float moveSpeedModifier;

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

    private int timeSinceLastShot;
    private int timeSinceLastMove;
    private final int timeBetweenMoves;
    private final float timeBetweenMovesModifier;

    /**
     * Constructs a new Enemy with the default values and a custom width, height and body.
     * @param width width of the enemy as a float
     * @param height height of the enemy as a float
     * @param body the Box2D body of the enemy as a Body
     */
    public Enemy(final float width, final float height, final Body body) {
        super(width, height, body, ENEMY_1_TEXTURE);

        setSpeed(BASE_MOVE_SPEED);

        this.currentHealthPoints = DEFAULT_MAX_HEALTH_POINTS;
        this.moveSpeed = BASE_MOVE_SPEED;
        this.moveSpeedModifier = BASE_MOVE_SPEED_MODIFIER;
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

        this.timeBetweenMoves = 200;
        this.timeBetweenMovesModifier = 1.0f;

        this.timeSinceLastShot = 9;
        this.timeSinceLastMove = 199;
    }

    /**
     * Updates this Enemy object. It is designed to be called every frame.
     */
    @Override
    public void update() {
        //
        getBody().setLinearVelocity(getBody().getLinearVelocity().x / 1.03f, getBody().getLinearVelocity().y / 1.03f);

        // move x & y to the current body position
        // x & y will be in the centre of our body
        setX(getBody().getPosition().x);
        setY(getBody().getPosition().y);

        moveHitbox();

        setTimeSinceLastShot(getTimeSinceLastShot() + 1);
        setTimeSinceLastMove(getTimeSinceLastMove() + 1);

        if (getCurrentHealthPoints() <= 0) {
            setDead(true);
        }
    }

    public int getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    /**
     * Renders the sprite associated with this Enemy at the location of it's X and Y coordinates.
     * @param batch the batch to render this GameEntity in as a SpriteBatch
     */
    @Override
    public void render(final SpriteBatch batch) {
        batch.draw(getSprite(), getX() - (getWidth() / 2),
            getY() - (getHeight() / 2), getWidth(), getHeight());
    }

    /**
     * Moves this Enemy is a random direction when it's movement is off cooldown.
     */
    public void move() {
        if (checkAllowedToMove()) {
            Random random = new Random();
            Vector2 movement = new Vector2(random.nextFloat(-1, 1), random.nextFloat(-1, 1));
            movement.nor();

            getBody().applyLinearImpulse(scaleMovement(movement), getBody().getPosition(), true);
            setTimeSinceLastMove(0);
        }
    }

    /**
     * If this enemy's attack is off cooldown the attack timer is reset and true is returned else
     * false.
     * @return if this enemy was able to attack as a boolean
     */
    public boolean attackIfAllowed() {
        if (getTimeSinceLastShot() >= getAttackSpeed() * getAttackSpeedModifier()) {
            setTimeSinceLastShot(0);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkAllowedToMove() {
        return getTimeSinceLastMove() >= getTimeBetweenMoves() * getTimeBetweenMovesModifier();
    }

    private void setCurrentHealthPoints(final int currentHealthPoints) {
        this.currentHealthPoints = currentHealthPoints;
    }

    private void setTimeSinceLastShot(final int timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }

    private void setTimeSinceLastMove(final int timeSinceLastMove) {
        this.timeSinceLastMove = timeSinceLastMove;
    }

    private int getTimeSinceLastMove() {
        return timeSinceLastMove;
    }

    private int getTimeBetweenMoves() {
        return timeBetweenMoves;
    }

    private float getTimeBetweenMovesModifier() {
        return timeBetweenMovesModifier;
    }

    /**
     * Deals damage to this enemy's current health.
     * @param damage the damage to deal to this enemy's health as a float.
     */
    public void takeDamage(final float damage) {
        if (damage >= 0) {
            setHealth(-damage); // ensure damage will reduce health
        } else {
            setHealth(damage);
        }
    }

    /**
     * Changes this enemy's health by a value.
     * @param valueToChangeHealthBy the value to be added to this enemy's health
     */
    private void setHealth(final float valueToChangeHealthBy) {
        setCurrentHealthPoints(getCurrentHealthPoints() + Math.round(valueToChangeHealthBy));
    }

    /**
     * Scales this enemy's movement based off of its move speed and modifier.
     * @param unscaledMovement the unscaled movement of this enemy as a Vector2
     * @return the scaled movement of this enemy as a Vector2
     */
    protected Vector2 scaleMovement(final Vector2 unscaledMovement) {
        return new Vector2(unscaledMovement.x * getMoveSpeed() * getMoveSpeedModifier(),
                           unscaledMovement.y * getMoveSpeed() * getMoveSpeedModifier());
    }

    /**
     * Returns the size of the enemy's projectile.
     * @return this size of this enemy's projectile as an int
     */
    public int getProjectileSize() {
        return projectileSize;
    }

    /**
     * Returns the shape of this enemy's projectile.
     * @return the shape of this enemy's projectile as a ProjectileShape
     */
    public ProjectileShape getProjectileShape() {
        return projectileShape;
    }

    /**
     * Returns the colour of this enemy's projectile.
     * @return the colour of this enemy's projectile as a ProjectileColour
     */
    public ProjectileColour getProjectileColour() {
        return projectileColour;
    }

    /**
     * Returns the team of this enemy's projectile.
     * @return the team of this enemy's projectile as a ProjectileTeam
     */
    public ProjectileTeam getProjectileTeam() {
        return projectileTeam;
    }

    private int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    private float getMoveSpeed() {
        return moveSpeed;
    }

    private float getMoveSpeedModifier() {
        return moveSpeedModifier;
    }

    private int getAttackSpeed() {
        return attackSpeed;
    }

    private float getAttackSpeedModifier() {
        return attackSpeedModifier;
    }

    /**
     * Get this Enemy's attack damage.
     * @return this Enemy's attack damage as a float
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Get this Enemy's attack damage modifier.
     * @return this Enemy's attack damage modifier as a float
     */
    public float getDamageModifier() {
        return damageModifier;
    }

    /**
     * Get this Enemy's bullet speed.
     * @return this Enemy's bullet speed as a float
     */
    public float getBulletSpeed() {
        return bulletSpeed;
    }

    /**
     * Get this Enemy's bullet speed modifier.
     * @return this Enemy's bullet speed modifier as a float
     */
    public float getBulletSpeedModifier() {
        return bulletSpeedModifier;
    }

    /**
     * Get this Enemy's bullet lifespan.
     * @return this Enemy's bullet lifespan as an int
     */
    public int getBulletLifespan() {
        return bulletLifespan;
    }

    /**
     * Get this Enemy's vector from the origin.
     * @return this Enemy's vector from the origin as a Vector2
     */
    public Vector2 getVectorFromOrigin() {
        return new Vector2(getX(), getY());
    }
}
