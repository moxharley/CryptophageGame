package objects.entitiy.enemy;

import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

public class Enemy1Constants {

    private Enemy1Constants() { }


/**
 * Default max health of rangedEnemy1.
 */
public static final int DEFAULT_MAX_HEALTH_POINTS = 8;

/**
 * Base amount for damage taken by to be reduced by.
 */
public static final float BASE_RESISTANCE = 0f;

//----------------------------------------------------------------------------------------------

/**
 * Base movement speed of rangedEnemy1.
 * The movement gained by pressing a directional key
 */
public static final float BASE_MOVE_SPEED = 6f;

/**
 * Base movement speed modifier of rangedEnemy1.
 */
public static final float BASE_MOVE_SPEED_MODIFIER = 1f;

/**
 * Base jump velocity of rangedEnemy1.
 */
public static final float BASE_JUMP_VELOCITY = 18f;

/**
 * Base jump velocity modifier of rangedEnemy1.
 */
public static final float BASE_JUMP_VELOCITY_MODIFIER = 1f;

//----------------------------------------------------------------------------------------------

/**
 * Base attack speed of rangedEnemy1 in frames.
 */
public static final int BASE_ATTACK_SPEED = 120;

/**
 * Base attack speed modifier of rangedEnemy1.
 */
public static final float BASE_ATTACK_SPEED_MODIFIER = 1f;

/**
 * Base damage done by rangedEnemy1.
 */
public static final int BASE_DAMAGE = 4;

/**
 * Modifier of base damage done by rangedEnemy1.
 */
public static final int BASE_DAMAGE_MODIFIER = 8;

/**
 * Base speed of the bullets shot by rangedEnemy1.
 */
public static final float BASE_BULLET_SPEED = 1f;

/**
 * Base modifier of the speed of the bullets shot by rangedEnemy1.
 */
public static final float BASE_BULLET_SPEED_MODIFIER = 1f;

/**
 * Base lifespan of the bullets shot by rangedEnemy1 in frames.
 */
public static final int BASE_BULLET_LIFESPAN = 200;

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
public static final ProjectileColour DEFAULT_PROJECTILE_COLOUR = ProjectileColour.RED;
public static final ProjectileTeam DEFAULT_PROJECTILE_TEAM = ProjectileTeam.ENEMY;
}

