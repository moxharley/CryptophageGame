package objects.entitiy.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

import java.util.Random;

import static objects.entitiy.enemy.Enemy1Constants.*;

public class Enemy1 extends Enemy {

    protected int maxHealthPoints;
    protected int currentHealthPoints;
    protected float resistance;

    protected float moveSpeed;
    protected float moveSpeedModifier;

    protected float jumpVelocity;
    protected float jumpVelocityModifier;

    // TODO: ATTACK speed modifier is better low as opposed to others, fix logic
    protected int attackSpeed;
    protected float attackSpeedModifier;
    protected float damage;
    protected float damageModifier;
    protected float bulletSpeed;
    protected float bulletSpeedModifier;
    protected int bulletLifespan;
    protected float critChance;
    protected float critDamage;
    private int projectileSize;

    protected ProjectileShape projectileShape;
    protected ProjectileColour projectileColour;
    protected ProjectileTeam projectileTeam;

    private int timeSinceLastShot;
    private int timeSinceLastMove;
    private int timeBetweenMoves;
    private float timeBetweenMovesModifier;

    public Enemy1(float width, float height, Body body) {
        super(width, height, body);

        // TODO: all constants are currently player constants, change to enemy ones

        this.speed = BASE_MOVE_SPEED;

        this.maxHealthPoints = DEFAULT_MAX_HEALTH_POINTS;
        this.currentHealthPoints = DEFAULT_MAX_HEALTH_POINTS;
        this.resistance = BASE_RESISTANCE;

        this.moveSpeed = BASE_MOVE_SPEED;
        this.moveSpeedModifier = BASE_MOVE_SPEED_MODIFIER;
        this.jumpVelocity = BASE_JUMP_VELOCITY;
        this.jumpVelocityModifier = BASE_JUMP_VELOCITY_MODIFIER;

        this.attackSpeed = BASE_ATTACK_SPEED;
        this.attackSpeedModifier = BASE_ATTACK_SPEED_MODIFIER;
        this.damage = BASE_DAMAGE;
        this.damageModifier = BASE_DAMAGE_MODIFIER;
        this.bulletSpeed = BASE_BULLET_SPEED;
        this.bulletSpeedModifier = BASE_BULLET_SPEED_MODIFIER;
        this.bulletLifespan = BASE_BULLET_LIFESPAN;

        this.critChance = BASE_CRIT_CHANCE;
        this.critDamage = BASE_CRIT_DAMAGE;

        this.projectileSize = DEFAULT_PROJECTILE_SIZE;

        this.projectileColour = DEFAULT_PROJECTILE_COLOUR;
        this.projectileShape = DEFAULT_PROJECTILE_SHAPE;
        this.projectileTeam = DEFAULT_PROJECTILE_TEAM;

        this.timeBetweenMoves = 200;
        this.timeBetweenMovesModifier = 1.0f;

        timeSinceLastShot = 9;
        timeSinceLastMove = 199;
    }

    @Override
    public void update() {
        //
        body.setLinearVelocity(body.getLinearVelocity().x / 1.03f, body.getLinearVelocity().y / 1.03f);

        // move x & y to the current body position
        // x & y will be in the centre of our body
        x = (body.getPosition().x) / 2;
        y = (body.getPosition().y) / 2;

        timeSinceLastShot += 1;
        timeSinceLastMove += 1;
    }

    // TODO this is incomplete
    public void render(SpriteBatch batch) { }


    @Override
    public void move(final Vector2 playerPosition) {
        if (checkAllowedToMove()) {
            Vector2 movement = getVectorFromEnemyToPlayer(playerPosition);
            getBody().applyLinearImpulse(scaleMovement(movement), body.getPosition(), true);
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


    @Override
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

    /**
     * Resets all stats of this PlayerEntity to their defaults.
     * Keeps the player's health as is, capping it to the new max health.
     */
    public void resetAllStats() {
        this.maxHealthPoints = DEFAULT_MAX_HEALTH_POINTS;
        this.currentHealthPoints = Math.min(currentHealthPoints, DEFAULT_MAX_HEALTH_POINTS);
        this.resistance = BASE_RESISTANCE;

        this.moveSpeed = BASE_MOVE_SPEED;
        this.moveSpeedModifier = BASE_MOVE_SPEED_MODIFIER;
        this.jumpVelocity = BASE_JUMP_VELOCITY;
        this.jumpVelocityModifier = BASE_JUMP_VELOCITY_MODIFIER;

        this.attackSpeed = BASE_ATTACK_SPEED;
        this.attackSpeedModifier = BASE_ATTACK_SPEED_MODIFIER;
        this.damage = BASE_DAMAGE;
        this.damageModifier = BASE_DAMAGE_MODIFIER;
        this.bulletSpeed = BASE_BULLET_SPEED;
        this.bulletSpeedModifier = BASE_BULLET_SPEED_MODIFIER;

        this.critChance = BASE_CRIT_CHANCE;
        this.critDamage = BASE_CRIT_DAMAGE;
    }

    /**
     * Multiplies all stats by a value.
     *
     * @param statMultiplier the amount to multiply all stats by as a float
     */
    public void changeAllStatsMultiplicative(final float statMultiplier) {
        setMaxHealthPoints((int) (getMaxHealthPoints() * statMultiplier));
        setCurrentHealthPoints((int) (getCurrentHealthPoints() * statMultiplier));
        setResistance(getResistance() * statMultiplier);

        setMoveSpeed(getMoveSpeed() * statMultiplier);
        setMoveSpeedModifier(getMoveSpeedModifier() * statMultiplier);
        setJumpVelocity(getJumpVelocity() * statMultiplier);
        setJumpVelocityModifier(getJumpVelocityModifier() * statMultiplier);

        setAttackSpeed((int) (getAttackSpeed() * statMultiplier));
        setAttackSpeedModifier(getAttackSpeedModifier() * statMultiplier);
        setDamage(getDamage() * statMultiplier);
        setDamageModifier(getDamageModifier() * statMultiplier);
        setBulletSpeed(getBulletSpeed() * statMultiplier);
        setBulletSpeedModifier(getBulletSpeedModifier() * statMultiplier);

        setCritChance(getCritChance() * statMultiplier);
        setCritDamage(getCritDamage() * statMultiplier);
    }

    /**
     * Adds a bonus to all stats.
     *
     * @param statBonus the flat bonus to add to all stats as an int
     */
    public void changeAllStatsAdditive(final int statBonus) {
        setMaxHealthPoints(getMaxHealthPoints() + statBonus);
        setCurrentHealthPoints(getCurrentHealthPoints() + statBonus);
        setResistance(getResistance() + statBonus);

        setMoveSpeed(getMoveSpeed() + statBonus);
        setMoveSpeedModifier(getMoveSpeedModifier() + statBonus);
        setJumpVelocity(getJumpVelocity() + statBonus);
        setJumpVelocityModifier(getJumpVelocityModifier() + statBonus);

        setAttackSpeed(getAttackSpeed() + statBonus);
        setAttackSpeedModifier(getAttackSpeedModifier() + statBonus);
        setDamage(getDamage() + statBonus);
        setDamageModifier(getDamageModifier() + statBonus);
        setBulletSpeed(getBulletSpeed() + statBonus);
        setBulletSpeedModifier(getBulletSpeedModifier() + statBonus);

        setCritChance(getCritChance() + statBonus);
        setCritDamage(getCritDamage() + statBonus);
    }


    public int getProjectileSize() {
        return projectileSize;
    }

    public ProjectileShape getProjectileShape() {
        return projectileShape;
    }

    public void setProjectileShape(ProjectileShape projectileShape) {
        this.projectileShape = projectileShape;
    }

    public ProjectileColour getProjectileColour() {
        return projectileColour;
    }

    public void setProjectileColour(ProjectileColour projectileColour) {
        this.projectileColour = projectileColour;
    }

    public ProjectileTeam getProjectileTeam() {
        return projectileTeam;
    }

    public void setProjectileTeam(ProjectileTeam projectileTeam) {
        this.projectileTeam = projectileTeam;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public void setCurrentHealthPoints(int currentHealthPoints) {
        this.currentHealthPoints = currentHealthPoints;
    }

    public float getResistance() {
        return resistance;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getMoveSpeedModifier() {
        return moveSpeedModifier;
    }

    public void setMoveSpeedModifier(float moveSpeedModifier) {
        this.moveSpeedModifier = moveSpeedModifier;
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public void setJumpVelocity(float jumpVelocity) {
        this.jumpVelocity = jumpVelocity;
    }

    public float getJumpVelocityModifier() {
        return jumpVelocityModifier;
    }

    public void setJumpVelocityModifier(float jumpVelocityModifier) {
        this.jumpVelocityModifier = jumpVelocityModifier;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getAttackSpeedModifier() {
        return attackSpeedModifier;
    }

    public void setAttackSpeedModifier(float attackSpeedModifier) {
        this.attackSpeedModifier = attackSpeedModifier;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(float damageModifier) {
        this.damageModifier = damageModifier;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public float getBulletSpeedModifier() {
        return bulletSpeedModifier;
    }

    public void setBulletSpeedModifier(float bulletSpeedModifier) {
        this.bulletSpeedModifier = bulletSpeedModifier;
    }

    public int getBulletLifespan() {
        return bulletLifespan;
    }

    public void setBulletLifespan(int bulletLifespan) {
        this.bulletLifespan = bulletLifespan;
    }

    public float getCritChance() {
        return critChance;
    }

    public void setCritChance(float critChance) {
        this.critChance = critChance;
    }

    public float getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(float critDamage) {
        this.critDamage = critDamage;
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
}
