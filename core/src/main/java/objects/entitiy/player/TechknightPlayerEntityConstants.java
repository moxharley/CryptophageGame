package objects.entitiy.player;

import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

import java.sql.Time;
import java.util.Timer;

/**
 * Represents the constants used by the PlayerEntity Class.
 *
 * @author FinnWylie
 * @version 2026
 */

public class TechknightPlayerEntityConstants {

    // TODO: floats are bonus percents.
    // add all defaults as these aren't instance vars and need something to be reset to
    //      these are what a new obj is constructed with.



    private TechknightPlayerEntityConstants() { }



    /**
     * Default max health of the player.
     */
    public static final int DEFAULT_MAX_HEALTH_POINTS = 24;

    /**
     * Base amount for damage taken by to be reduced by.
     */
    public static final float BASE_RESISTANCE = 0f;

    //----------------------------------------------------------------------------------------------

    /**
     * Base movement speed of the player.
     * The movement gained by pressing a directional key
     */
    public static final float BASE_MOVE_SPEED = 8f;

    /**
     * Base movement speed modifier of the player.
     */
    public static final float BASE_MOVE_SPEED_MODIFIER = 1f;

    /**
     * Base jump velocity of the player.
     */
    public static final float BASE_JUMP_VELOCITY = 18f;

    /**
     * Base jump velocity modifier of the player.
     */
    public static final float BASE_JUMP_VELOCITY_MODIFIER = 1f;

    //----------------------------------------------------------------------------------------------

    /**
     * Base attack speed of the player in frames.
     */
    public static final int BASE_ATTACK_SPEED = 20;

    /**
     * Base attack speed modifier of the player.
     */
    public static final float BASE_ATTACK_SPEED_MODIFIER = 1f;

    /**
     * Base damage done by the player.
     */
    public static final int BASE_DAMAGE = 8;

    /**
     * Modifier of base damage done by the player.
     */
    public static final int BASE_DAMAGE_MODIFIER = 8;

    /**
     * Base speed of the bullets shot by the player.
     */
    public static final float BASE_BULLET_SPEED = 3f;

    /**
     * Base modifier of the speed of the bullets shot by the player.
     */
    public static final float BASE_BULLET_SPEED_MODIFIER = 1f;

    /**
     * Base lifespan of the bullets shot by the player in frames.
     */
    public static final int BASE_BULLET_LIFESPAN = 120;

    //----------------------------------------------------------------------------------------------

    /**
     * The percent chance of dealing a critical hit, dealing bonus damage
     */
    public static final float BASE_CRIT_CHANCE = 0f;

    /**
     * On crit, bonus damage dealt is increased by this percent.
     */
    public static final float BASE_CRIT_DAMAGE = 0f;

    //----------------------------------------------------------------------------------------------

    /**
     * Size of the projectile, determined by the longest in the length or width in pixels.
     */
    public static final int DEFAULT_PROJECTILE_SIZE = 16;

    //----------------------------------------------------------------------------------------------

    public final static ProjectileShape DEFAULT_PROJECTILE_SHAPE = ProjectileShape.CIRCLE;
    public static final ProjectileColour DEFAULT_PROJECTILE_COLOUR = ProjectileColour.BLUE;
    public static final ProjectileTeam DEFAULT_PROJECTILE_TEAM = ProjectileTeam.PLAYER;
}
