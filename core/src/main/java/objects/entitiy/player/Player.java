package objects.entitiy.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import objects.entitiy.GameEntity;
import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

import java.util.Random;

import static helper.GameConstants.*;
import static objects.entitiy.player.TechknightPlayerEntityConstants.*;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_ATTACK_SPEED;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_ATTACK_SPEED_MODIFIER;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_BULLET_SPEED;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_BULLET_SPEED_MODIFIER;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_CRIT_CHANCE;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_CRIT_DAMAGE;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_DAMAGE;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_DAMAGE_MODIFIER;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_JUMP_VELOCITY_MODIFIER;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_MOVE_SPEED_MODIFIER;
import static objects.entitiy.player.TechknightPlayerEntityConstants.BASE_RESISTANCE;
import static objects.entitiy.player.TechknightPlayerEntityConstants.DEFAULT_MAX_HEALTH_POINTS;

public class Player extends GameEntity {

    private int currentHealthPoints;
    private int kills;

    // TODO: ATTACK speed modifier is better low as opposed to others, fix logic
    private int attackSpeed;
    private float attackSpeedModifier;
    private float damage;
    private float damageModifier;
    private float bulletSpeed;
    private float bulletSpeedModifier;
    private int bulletLifespan;
    private float critChance;
    private float critDamage;
    private int projectileSize;

    private ProjectileShape projectileShape;
    private ProjectileColour projectileColour;
    private ProjectileTeam projectileTeam;

    private int timeSinceLastShot;

    public Player(final float width, final float height, final Body body) {
        super(width, height, body, PLAYER_TEXTURE);

        this.speed = BASE_MOVE_SPEED;

        this.currentHealthPoints = DEFAULT_MAX_HEALTH_POINTS;

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

        timeSinceLastShot = 0;
        this.kills = 0;
    }

    public void update() {
        // makes the body move based on velocity and speed
        body.setLinearVelocity(velX * speed, velY * speed);

        // move x & y to the current body position
        // x & y will be in the centre of our body
        x = body.getPosition().x;
        y = body.getPosition().y;

        moveHitbox();
        moveSprite();

        setTimeSinceLastShot(getTimeSinceLastShot() + 1);
//        setTimeSinceLastSkill(getTimeSinceLastSkill() + 1);

        if (getCurrentHealthPoints() <= 0) {
            setIsDead(true);
        }
    }

    @Override
    public void render(final SpriteBatch batch) {
        batch.draw(sprite, getX() - (getWidth() / 2),
            getY() - (getHeight() / 2), getWidth(), getHeight());
    }

    public void move(final int horizontalMovement, final int verticalMovement) {
        velX = horizontalMovement;
        velY = verticalMovement;
    }

    public boolean attackIfAllowed() {
        if (timeSinceLastShot >= getAttackSpeed() * getAttackSpeedModifier()) {
            timeSinceLastShot = 0;
            return true;
        } else {
            return false;
        }
    }

    public int getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    public void setTimeSinceLastShot(int timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }

    private void setIsDead(final Boolean isDead) {
        this.isDead = isDead;
    }

    public boolean getIsDead() {
        return isDead;
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

    public void takeDamage(final float damage) {
        if (damage >= 0) {
            setCurrentHealthPoints(-damage); // ensure damage will reduce health
        } else {
            setCurrentHealthPoints(damage);
        }
    }

    private void setCurrentHealthPoints(final float valueToChangeHealthBy) {
        currentHealthPoints += Math.round(valueToChangeHealthBy);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public int getKills() {
        return kills;
    }
}
