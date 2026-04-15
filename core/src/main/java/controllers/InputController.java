package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents an InputController for managing user input.
 * @author FinnWylie
 * @version 2026
 */
public class InputController {
    private int horizontalMovement;
    private int verticalMovement;
    private boolean pressedShoot;

    /**
     * Represents the key used to input left.
     */
    private static final int LEFT = Input.Keys.A;

    /**
     * Represents the key used to input right.
     */
    private static final int RIGHT = Input.Keys.D;

    /**
     * Represents the key used to input up.
     */
    private static final int UP = Input.Keys.W;

    /**
     * Represents the key used to input down.
     */
    private static final int DOWN = Input.Keys.S;

    /**
     * Represents the key used to input shoot.
     */
    private static final int SHOOT = Input.Buttons.LEFT;


    /**
     * Creates a new InputController with the values that will cause the game to preform no actions.
     */
    public InputController() {
        this.horizontalMovement = 0;
        this.verticalMovement = 0;
        this.pressedShoot = false;
    }

    /**
     * Updates this InputController to default values, or to use the new inputs the player provides.
     */
    public void sync() {
        setHorizontalMovement(0);
        setVerticalMovement(0);
        setPressedShoot(false);

        setHorizontalMovement(checkHorizontalMovement());
        setVerticalMovement(checkVerticalMovement());
        setPressedShoot(checkPressedShoot());
    }


    /**
     * Returns a normalized Vector2 of the angle from the player to the cursor.
     * When it is implemented such that the player moves around on the screen relative to the
     * centre, we can rewrite this code to actually use the player location,
     * instead of the fixed 0, 0.
     *
     * @return the direction of the cursor from the player as a Vector2
     */
    public Vector2 getCursorVectorFromPlayer() {
        /*
         get cursor location, modify the location as if the input origin was in the centre of
         the screen (this is where the player is located).
         Then the Y is inverted as the input Y axis direction is the opposite of
         the game coordinate Y axis direction.
         */
        Vector2 relativeCursorLocation = new Vector2(
            (Gdx.input.getX() - ((float) Gdx.graphics.getWidth() / 2)),
            -((Gdx.input.getY() - ((float) Gdx.graphics.getHeight() / 2))));
        return relativeCursorLocation.sub(0, 0).nor();
    }

    private int checkHorizontalMovement() {
        if (Gdx.input.isKeyPressed(LEFT) && !Gdx.input.isKeyPressed(RIGHT)) {
            return -1;
        } else if (Gdx.input.isKeyPressed(RIGHT) && !Gdx.input.isKeyPressed(LEFT)) {
            return 1;
        } else {
            return 0;
        }
    }

    private int checkVerticalMovement() {
        if (Gdx.input.isKeyPressed(UP) && !Gdx.input.isKeyPressed(DOWN)) {
            return 1;
        } else if (Gdx.input.isKeyPressed(DOWN) && !Gdx.input.isKeyPressed(UP)) {
            return -1;
        } else {
            return 0;
        }
    }

    /*
    Used by sync to check for specified input.
     */
    private boolean checkPressedShoot() {
        return Gdx.input.isButtonPressed(SHOOT);
    }

    /**
     * Returns the horizontal movement input of the user.
     * @return horizontalMovement as an int
     */
    public int getHorizontalMovement() {
        return horizontalMovement;
    }

    /**
     * Returns the vertical movement input of the user.
     * @return verticalMovement as an int
     */
    public int getVerticalMovement() {
        return verticalMovement;
    }

    /**
     * Returns if the user pressed shoot this tick.
     * @return if the user pressed shoot this tick as a boolean
     */
    public boolean getPressedShoot() {
        return pressedShoot;
    }

    private void setHorizontalMovement(int horizontalMovement) {
        this.horizontalMovement = horizontalMovement;
    }

    private void setVerticalMovement(int verticalMovement) {
        this.verticalMovement = verticalMovement;
    }

    private void setPressedShoot(boolean pressedShoot) {
        this.pressedShoot = pressedShoot;
    }
}
