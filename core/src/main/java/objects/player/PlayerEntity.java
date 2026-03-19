//package objects.player;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.physics.box2d.Body;
//import objects.GameEntity;
//import objects.projectile.Projectile;
//
//import static objects.player.TechknightPlayerEntityConstants.*;
//
//public abstract class PlayerEntity extends GameEntity {
//
//    protected int maxHealthPoints;
//    protected int currentHealthPoints;
//    protected float resistance;
//
//
//    protected float moveSpeed;
//    protected float moveSpeedModifier;
//
//
//    protected float jumpVelocity;
//    protected float jumpVelocityModifier;
//
//
//    protected float attackSpeed;
//    protected float attackSpeedModifier;
//
//    protected float damage;
//    protected float damageModifier;
//
//    protected float bulletSpeed;
//    protected float bulletSpeedModifier;
//
//    protected int bulletLifespan;
//
//    protected float critChance;
//    protected float critDamage;
//
//    /**
//     * Constructs a PlayerEntity with the default values for the stats.
//     * The width and height are values taken from the player object in the map made in Tiled
//     *
//     * @param width the width of this PlayerEntity as a float
//     * @param height the height of this PlayerEntity as a float
//     * @param body the Box2D body of the player as a Body
//     */
//    public PlayerEntity(final float width, final float height, final Body body) {
//        super(width, height, body);
//
//        this.maxHealthPoints = DEFAULT_MAX_HEALTH_POINTS;
//        this.currentHealthPoints = DEFAULT_MAX_HEALTH_POINTS;
//        this.resistance = BASE_RESISTANCE;
//
//        this.moveSpeed = BASE_MOVE_SPEED;
//        this.moveSpeedModifier = BASE_MOVE_SPEED_MODIFIER;
//        this.jumpVelocity = BASE_JUMP_VELOCITY;
//        this.jumpVelocityModifier = BASE_JUMP_VELOCITY_MODIFIER;
//
//        this.attackSpeed = BASE_ATTACK_SPEED;
//        this.attackSpeedModifier = BASE_ATTACK_SPEED_MODIFIER;
//        this.damage = BASE_DAMAGE;
//        this.damageModifier = BASE_DAMAGE_MODIFIER;
//        this.bulletSpeed = BASE_BULLET_SPEED;
//        this.bulletSpeedModifier = BASE_BULLET_SPEED_MODIFIER;
//
//        this.critChance = BASE_CRIT_CHANCE;
//        this.critDamage = BASE_CRIT_DAMAGE;
//    }
//
//    public int getMaxHealthPoints() {
//        return maxHealthPoints;
//    }
//
//    public void setMaxHealthPoints(int maxHealthPoints) {
//        this.maxHealthPoints = maxHealthPoints;
//    }
//
//    public int getCurrentHealthPoints() {
//        return currentHealthPoints;
//    }
//
//    public void setCurrentHealthPoints(int currentHealthPoints) {
//        this.currentHealthPoints = currentHealthPoints;
//    }
//
//    public float getResistance() {
//        return resistance;
//    }
//
//    public void setResistance(float resistance) {
//        this.resistance = resistance;
//    }
//
//    public float getMoveSpeed() {
//        return moveSpeed;
//    }
//
//    public void setMoveSpeed(float moveSpeed) {
//        this.moveSpeed = moveSpeed;
//    }
//
//    public float getMoveSpeedModifier() {
//        return moveSpeedModifier;
//    }
//
//    public void setMoveSpeedModifier(float moveSpeedModifier) {
//        this.moveSpeedModifier = moveSpeedModifier;
//    }
//
//    public float getJumpVelocity() {
//        return jumpVelocity;
//    }
//
//    public void setJumpVelocity(float jumpVelocity) {
//        this.jumpVelocity = jumpVelocity;
//    }
//
//    public float getJumpVelocityModifier() {
//        return jumpVelocityModifier;
//    }
//
//    public void setJumpVelocityModifier(float jumpVelocityModifier) {
//        this.jumpVelocityModifier = jumpVelocityModifier;
//    }
//
//    public float getAttackSpeed() {
//        return attackSpeed;
//    }
//
//    public void setAttackSpeed(float attackSpeed) {
//        this.attackSpeed = attackSpeed;
//    }
//
//    public float getAttackSpeedModifier() {
//        return attackSpeedModifier;
//    }
//
//    public void setAttackSpeedModifier(float attackSpeedModifier) {
//        this.attackSpeedModifier = attackSpeedModifier;
//    }
//
//    public float getDamage() {
//        return damage;
//    }
//
//    public void setDamage(float damage) {
//        this.damage = damage;
//    }
//
//    public float getDamageModifier() {
//        return damageModifier;
//    }
//
//    public void setDamageModifier(float damageModifier) {
//        this.damageModifier = damageModifier;
//    }
//
//    public float getBulletSpeed() {
//        return bulletSpeed;
//    }
//
//    public void setBulletSpeed(float bulletSpeed) {
//        this.bulletSpeed = bulletSpeed;
//    }
//
//    public float getBulletSpeedModifier() {
//        return bulletSpeedModifier;
//    }
//
//    public void setBulletSpeedModifier(float bulletSpeedModifier) {
//        this.bulletSpeedModifier = bulletSpeedModifier;
//    }
//
//    public float getCritChance() {
//        return critChance;
//    }
//
//    public void setCritChance(float critChance) {
//        this.critChance = critChance;
//    }
//
//    public float getCritDamage() {
//        return critDamage;
//    }
//
//    public void setCritDamage(float critDamage) {
//        this.critDamage = critDamage;
//    }
//
//    public int getBulletLifespan() {
//        return bulletLifespan;
//    }
//
//    public void setBulletLifespan(int bullet_lifespan) {
//        this.bulletLifespan = bullet_lifespan;
//    }
//
//    /**
//     * Resets all stats of this PlayerEntity to their defaults.
//     * Keeps the player's health as is, capping it to the new max health.
//     */
//    public void resetAllStats() {
//        this.maxHealthPoints = DEFAULT_MAX_HEALTH_POINTS;
//        this.currentHealthPoints = Math.min(currentHealthPoints, DEFAULT_MAX_HEALTH_POINTS);
//        this.resistance = BASE_RESISTANCE;
//
//        this.moveSpeed = BASE_MOVE_SPEED;
//        this.moveSpeedModifier = BASE_MOVE_SPEED_MODIFIER;
//        this.jumpVelocity = BASE_JUMP_VELOCITY;
//        this.jumpVelocityModifier = BASE_JUMP_VELOCITY_MODIFIER;
//
//        this.attackSpeed = BASE_ATTACK_SPEED;
//        this.attackSpeedModifier = BASE_ATTACK_SPEED_MODIFIER;
//        this.damage = BASE_DAMAGE;
//        this.damageModifier = BASE_DAMAGE_MODIFIER;
//        this.bulletSpeed = BASE_BULLET_SPEED;
//        this.bulletSpeedModifier = BASE_BULLET_SPEED_MODIFIER;
//
//        this.critChance = BASE_CRIT_CHANCE;
//        this.critDamage = BASE_CRIT_DAMAGE;
//    }
//
//    /**
//     * Multiplies all stats by a value.
//     *
//     * @param statMultiplier the amount to multiply all stats by as a float
//     */
//    public void changeAllStatsMultiplicative(final float statMultiplier) {
//        setMaxHealthPoints((int) (getMaxHealthPoints() * statMultiplier));
//        setCurrentHealthPoints((int) (getCurrentHealthPoints() * statMultiplier));
//        setResistance(getResistance() * statMultiplier);
//
//        setMoveSpeed(getMoveSpeed() * statMultiplier);
//        setMoveSpeedModifier(getMoveSpeedModifier() * statMultiplier);
//        setJumpVelocity(getJumpVelocity() * statMultiplier);
//        setJumpVelocityModifier(getJumpVelocityModifier() * statMultiplier);
//
//        setAttackSpeed(getAttackSpeed() * statMultiplier);
//        setAttackSpeedModifier(getAttackSpeedModifier() * statMultiplier);
//        setDamage(getDamage() * statMultiplier);
//        setDamageModifier(getDamageModifier() * statMultiplier);
//        setBulletSpeed(getBulletSpeed() * statMultiplier);
//        setBulletSpeedModifier(getBulletSpeedModifier() * statMultiplier);
//
//        setCritChance(getCritChance() * statMultiplier);
//        setCritDamage(getCritDamage() * statMultiplier);
//    }
//
//    /**
//     * Adds a bonus to all stats.
//     *
//     * @param statBonus the flat bonus to add to all stats as an int
//     */
//    public void changeAllStatsAdditive(final int statBonus) {
//        setMaxHealthPoints(getMaxHealthPoints() + statBonus);
//        setCurrentHealthPoints(getCurrentHealthPoints() + statBonus);
//        setResistance(getResistance() + statBonus);
//
//        setMoveSpeed(getMoveSpeed() + statBonus);
//        setMoveSpeedModifier(getMoveSpeedModifier() + statBonus);
//        setJumpVelocity(getJumpVelocity() + statBonus);
//        setJumpVelocityModifier(getJumpVelocityModifier() + statBonus);
//
//        setAttackSpeed(getAttackSpeed() + statBonus);
//        setAttackSpeedModifier(getAttackSpeedModifier() + statBonus);
//        setDamage(getDamage() + statBonus);
//        setDamageModifier(getDamageModifier() + statBonus);
//        setBulletSpeed(getBulletSpeed() + statBonus);
//        setBulletSpeedModifier(getBulletSpeedModifier() + statBonus);
//
//        setCritChance(getCritChance() + statBonus);
//        setCritDamage(getCritDamage() + statBonus);
//    }
//
//    // TODO FIX, this is really really bad and doesn't follow any good practices
//    public Projectile attack(final int mouseX, final int mouseY) {
//        return new Projectile(DEFAULT_PROJECTILE_SHAPE, DEFAULT_PROJECTILE_COLOUR,
//                                               DEFAULT_PROJECTILE_TEAM, getDamage(),
//            getBulletSpeed(), mouseX, mouseY, getX(), getY(), getBulletLifespan());
//    }
//}
