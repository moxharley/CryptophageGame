package objects.entitiy.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    private TechknightPlayerEntityConstants() { }

    /**
     * The techknight player texture.
     */
    public static final Texture PLAYER_TEXTURE = new Texture("../assets/entities/hero.png");

    /**
     * Default max health of the player.
     */
    public static final int DEFAULT_MAX_HEALTH_POINTS = 24;

    /**
     * Base movement speed of the player.
     * The movement gained by pressing a directional key
     */
    public static final float BASE_MOVE_SPEED = 64f;

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

    /**
     * Size of the projectile, determined by the longest in the length or width in pixels.
     */
    public static final int DEFAULT_PROJECTILE_SIZE = 16;


    /**
     * The ProjectileShape associated with the TechKnightPlayer class as a ProjectileShape.
     */
    public final static ProjectileShape DEFAULT_PROJECTILE_SHAPE = ProjectileShape.CIRCLE;

    /**
     * The ProjectileColour associated with the TechKnightPlayer class as a ProjectileColour.
     */
    public static final ProjectileColour DEFAULT_PROJECTILE_COLOUR = ProjectileColour.BLUE;

    /**
     * The ProjectileTeam associated with the TechKnightPlayer class as a ProjectileTeam.
     */
    public static final ProjectileTeam DEFAULT_PROJECTILE_TEAM = ProjectileTeam.PLAYER;
}
