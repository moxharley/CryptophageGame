package helper;

/**
 * Represents the game constants of this game.
 * @author finnwylie
 * @version 2026
 */
public class GameConstants {

    private GameConstants() { }

    /**
     * Represents the Frames Per Second used as a float
     */
    public static final int FPS = 60;

    /**
     * Represents the global Gravity as a float
     */
    public static final float GRAVITY = 0f;

    /**
     * The lifespan of the projectiles in this game as an int.
     * 900 ticks @ 60tps is 15s lifespan.
     */
    public static final int PROJECTILE_LIFESPAN = 900;
}
