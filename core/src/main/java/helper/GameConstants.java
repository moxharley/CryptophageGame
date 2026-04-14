package helper;

public class GameConstants {

    private GameConstants() { }

    /**
     * Represents the Frames Per Second used as a float
     */
    public static final int FPS = 60;

    /**
     * Represents the global Gravity in meters per second as a float
     */
    public static final float GRAVITY = 0f; // Unit is m/s. May need to be the negative for certain functions

    public static final int PROJECTILE_LIFESPAN = 900; //900 ticks @ 60tps is 15s lifespan
}
