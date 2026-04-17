package objects.entitiy.enemy;

import com.badlogic.gdx.graphics.Texture;
import objects.projectile.ProjectileColour;
import objects.projectile.ProjectileShape;
import objects.projectile.ProjectileTeam;

public class EnemyConstants {

    private EnemyConstants() { }

    /**
     * The enemy1 texture.
     */
    public static final Texture ENEMY_1_TEXTURE = new Texture("../assets/entities/enemy1.png");

    /**
     * Default max health of rangedEnemy1.
     */
    public static final int DEFAULT_MAX_HEALTH_POINTS = 8;

    /**
     * Base movement speed of rangedEnemy1.
     * The movement gained by pressing a directional key
     */
    public static final float BASE_MOVE_SPEED = 64f;

    /**
     * Base movement speed modifier of rangedEnemy1.
     */
    public static final float BASE_MOVE_SPEED_MODIFIER = 1f;

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

    /**
     * Size of the projectile, determined by the longest in the length or width in pixels.
     */
    public static final int DEFAULT_PROJECTILE_SIZE = 16;

    /**
     * The ProjectileShape associated with the Enemy class as a ProjectileShape.
     */
    public final static ProjectileShape DEFAULT_PROJECTILE_SHAPE = ProjectileShape.CIRCLE;

    /**
     * The ProjectileColour associated with the Enemy class as a ProjectileColour.
     */
    public static final ProjectileColour DEFAULT_PROJECTILE_COLOUR = ProjectileColour.RED;

    /**
     * The ProjectileTeam associated with the Enemy class as a ProjectileTeam.
     */
    public static final ProjectileTeam DEFAULT_PROJECTILE_TEAM = ProjectileTeam.ENEMY;
}

