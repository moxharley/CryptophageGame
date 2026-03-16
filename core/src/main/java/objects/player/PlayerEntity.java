package objects.player;

import com.badlogic.gdx.physics.box2d.Body;
import objects.GameEntity;

import static objects.player.TemplatePlayerEntityConstants.*;

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
}
