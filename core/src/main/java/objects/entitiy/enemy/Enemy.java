package objects.entitiy.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import objects.entitiy.GameEntity;
import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

import java.util.Random;

import static objects.entitiy.enemy.EnemyConstants.*;
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

public class Enemy extends GameEntity {

    private int maxHealthPoints;
    private int currentHealthPoints;

    private float moveSpeed;
    private float moveSpeedModifier;

    private int attackSpeed;
    private float attackSpeedModifier;
    private float damage;
    private float damageModifier;
    private float bulletSpeed;
    private float bulletSpeedModifier;
    private int bulletLifespan;
    private int projectileSize;

    private ProjectileShape projectileShape;
    private ProjectileColour projectileColour;
    private ProjectileTeam projectileTeam;

    private int timeSinceLastShot;
    private int timeSinceLastMove;
    private int timeBetweenMoves;
    private float timeBetweenMovesModifier;

    public Enemy(final float width, final float height, final Body body) {
        super(width, height, body, ENEMY_1_TEXTURE);

        setSpeed(BASE_MOVE_SPEED);

        this.maxHealthPoints = DEFAULT_MAX_HEALTH_POINTS;
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

    @Override
    public void update() {
        //
        getBody().setLinearVelocity(getBody().getLinearVelocity().x / 1.03f, getBody().getLinearVelocity().y / 1.03f);

        // move x & y to the current body position
        // x & y will be in the centre of our body
        setX(getBody().getPosition().x);
        setY(getBody().getPosition().y);

        moveHitbox();
        moveSprite();

        timeSinceLastShot += 1;
        timeSinceLastMove += 1;

        if (getCurrentHealthPoints() <= 0) {
            setDead(true);
        }
    }

    @Override
    public void render(final SpriteBatch batch) {
        batch.draw(getSprite(), getX() - (getWidth() / 2),
            getY() - (getHeight() / 2), getWidth(), getHeight());
    }

    public void move(final Vector2 playerPosition) {
        if (checkAllowedToMove()) {
            Vector2 movement = getVectorFromEnemyToPlayer(playerPosition);
            getBody().applyLinearImpulse(scaleMovement(movement), getBody().getPosition(), true);
            timeSinceLastMove = 0;
        }
    }

    private Vector2 getVectorFromEnemyToPlayer(final Vector2 player) {
        Random random = new Random();
        Vector2 enemyToPlayerVector = new Vector2(
            random.nextFloat(-1, 1),
            random.nextFloat(-1, 1));
        return enemyToPlayerVector.sub(0, 0).nor();
    }

    public boolean attackIfAllowed() {
        if (timeSinceLastShot >= getAttackSpeed() * getAttackSpeedModifier()) {
            timeSinceLastShot = 0;
            return true;
        } else {
            return false;
        }
    }

    private boolean checkAllowedToMove() {
        return getTimeSinceLastMove() >= getTimeBetweenMoves() * getTimeBetweenMovesModifier();
    }

    public int getTimeSinceLastMove() {
        return timeSinceLastMove;
    }

    public int getTimeBetweenMoves() {
        return timeBetweenMoves;
    }

    public float getTimeBetweenMovesModifier() {
        return timeBetweenMovesModifier;
    }

    public void takeDamage(final float damage) {
        if (damage >= 0) {
            setHealth(-damage); // ensure damage will reduce health
        } else {
            setHealth(damage);
        }
    }

    private void setHealth(final float valueToChangeHealthBy) {
        currentHealthPoints += Math.round(valueToChangeHealthBy);
    }

    protected Vector2 scaleMovement(final Vector2 unscaledMovement) {
        return new Vector2(unscaledMovement.x * getMoveSpeed() * getMoveSpeedModifier(),
                           unscaledMovement.y * getMoveSpeed() * getMoveSpeedModifier());
    }

    public int getProjectileSize() {
        return projectileSize;
    }

    public ProjectileShape getProjectileShape() {
        return projectileShape;
    }

    public ProjectileColour getProjectileColour() {
        return projectileColour;
    }

    public ProjectileTeam getProjectileTeam() {
        return projectileTeam;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getMoveSpeedModifier() {
        return moveSpeedModifier;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public float getAttackSpeedModifier() {
        return attackSpeedModifier;
    }

    public float getDamage() {
        return damage;
    }

    public float getDamageModifier() {
        return damageModifier;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public float getBulletSpeedModifier() {
        return bulletSpeedModifier;
    }

    public int getBulletLifespan() {
        return bulletLifespan;
    }

    public Vector2 getVectorFromOrigin() {
        return new Vector2(getX(), getY());
    }
}
