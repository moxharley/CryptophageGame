package objects.player;

import com.badlogic.gdx.physics.box2d.Body;
import objects.GameEntity;

import static objects.player.TechknightPlayerEntityConstants.*;

public abstract class PlayerEntity extends GameEntity {

    protected int maxHealthPoints;
    protected int currentHealthPoints;
    protected double resistance;


    protected double moveSpeed;
    protected double moveSpeedModifier;


    protected double jumpVelocity;
    protected double jumpVelocityModifier;


    protected double attackSpeed;
    protected double attackSpeedModifier;

    protected double damage;
    protected double damageModifier;

    protected double bulletSpeed;
    protected double bulletSpeedModifier;


    protected double critChance;
    protected double critDamage;

    /**
     * Constructs a PlayerEntity with the default values for the stats.
     * The width and height are values taken from the player object in the map made in Tiled
     *
     * @param width the width of this PlayerEntity as a float
     * @param height the height of this PlayerEntity as a float
     * @param body the Box2D body of the player as a Body
     */
    public PlayerEntity(final float width, final float height, final Body body) {
        super(width, height, body);

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

        this.critChance = BASE_CRIT_CHANCE;
        this.critDamage = BASE_CRIT_DAMAGE;
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

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public double getMoveSpeedModifier() {
        return moveSpeedModifier;
    }

    public void setMoveSpeedModifier(double moveSpeedModifier) {
        this.moveSpeedModifier = moveSpeedModifier;
    }

    public double getJumpVelocity() {
        return jumpVelocity;
    }

    public void setJumpVelocity(double jumpVelocity) {
        this.jumpVelocity = jumpVelocity;
    }

    public double getJumpVelocityModifier() {
        return jumpVelocityModifier;
    }

    public void setJumpVelocityModifier(double jumpVelocityModifier) {
        this.jumpVelocityModifier = jumpVelocityModifier;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public double getAttackSpeedModifier() {
        return attackSpeedModifier;
    }

    public void setAttackSpeedModifier(double attackSpeedModifier) {
        this.attackSpeedModifier = attackSpeedModifier;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(double damageModifier) {
        this.damageModifier = damageModifier;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public double getBulletSpeedModifier() {
        return bulletSpeedModifier;
    }

    public void setBulletSpeedModifier(double bulletSpeedModifier) {
        this.bulletSpeedModifier = bulletSpeedModifier;
    }

    public double getCritChance() {
        return critChance;
    }

    public void setCritChance(double critChance) {
        this.critChance = critChance;
    }

    public double getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(double critDamage) {
        this.critDamage = critDamage;
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

        setAttackSpeed(getAttackSpeed() * statMultiplier);
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

}
