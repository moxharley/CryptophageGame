package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import static helper.GameConstants.PPM;

public class InputController {
    private int horizontalMovement;
    private int verticalMovement;
    private boolean pressedShoot;
    private boolean pressedSkill;


    private static final int LEFT = Input.Keys.A;
    private static final int RIGHT = Input.Keys.D;
    private static final int UP = Input.Keys.W;
    private static final int DOWN = Input.Keys.S;
    private static final int SHOOT = Input.Buttons.LEFT;
    private static final int SKILL = Input.Buttons.RIGHT;

    public InputController() {
        this.horizontalMovement = 0;
        this.verticalMovement = 0;
        this.pressedShoot = false;
        this.pressedSkill = false;
    }

    public void sync() {
        horizontalMovement = 0;
        verticalMovement = 0;
        pressedShoot = false;
        pressedSkill = false;

        horizontalMovement = checkHorizontalMovement();
        verticalMovement = checkVerticalMovement();
        pressedShoot = checkPressedShoot();
        pressedSkill = checkPressedSkill();
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
         the screen (this is where the player is located). then scale it by PPM (same as our
          character). Then the Y is inverted as the input Y axis direction is the opposite of
          the game coordinate Y axis direction.
         */
        Vector2 relativeCursorLocation = new Vector2(
            (Gdx.input.getX() - ((float) Gdx.graphics.getWidth() / 2)) / PPM,
            -((Gdx.input.getY() - ((float) Gdx.graphics.getHeight() / 2)) / PPM));
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
        return Gdx.input.isButtonJustPressed(SHOOT);
    }

    /*
    Used by sync to check for specified input.
     */
    private boolean checkPressedSkill() {
        return Gdx.input.isButtonJustPressed(SKILL);
    }

    public int getHorizontalMovement() {
        return horizontalMovement;
    }
    public int getVerticalMovement() {
        return verticalMovement;
    }

    public boolean getPressedShoot() {
        return pressedShoot;
    }

    public boolean getPressedSkill() {
        return pressedSkill;
    }
}
