package objects.entitiy.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import objects.entitiy.GameEntity;
import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

public class Enemy extends GameEntity {

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

    public Enemy(final float width, final float height, final Body body, final Texture texture) {
        super(width, height, body, texture);

        timeSinceLastShot = 0;
    }

    public void move(final Vector2 playerPosition) {}

    protected Vector2 scaleMovement(final Vector2 unscaledMovement) {
        return new Vector2(unscaledMovement.x * getMoveSpeed() * getMoveSpeedModifier(),
                           unscaledMovement.y * getMoveSpeed() * getMoveSpeedModifier());
    }

    public boolean attackIfAllowed() {
        return false;
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

    public Vector2 getVectorFromOrigin() {
        return new Vector2(getX(), getY());
    }
}
