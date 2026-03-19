package controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class InputController {
    private int horizontalMovement;
    private boolean pressedJump;
    private boolean pressedShoot;
    private boolean pressedSkill;


    private static final int LEFT = Input.Keys.A;
    private static final int RIGHT = Input.Keys.D;
    private static final int JUMP = Input.Keys.SPACE;
    private static final int SHOOT = Input.Buttons.LEFT;
    private static final int SKILL = Input.Buttons.RIGHT;

    public InputController() {
        this.horizontalMovement = 0;
        this.pressedJump = false;
        this.pressedShoot = false;
        this.pressedSkill = false;
        sync();
    }

    public void sync() {
        horizontalMovement = 0;
        pressedJump = false;
        pressedShoot = false;
        pressedSkill = false;

        horizontalMovement = checkHorizontalMovement();
        pressedJump = checkPressedJump();
        pressedShoot = checkPressedShoot();
        pressedSkill = checkPressedSkill();
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

    /*
    Used by sync to check for specified input.
     */
    private boolean checkPressedJump() {
        return Gdx.input.isKeyJustPressed(JUMP);
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

    public boolean getPressedJump() {
        return pressedJump;
    }

    public boolean getPressedShoot() {
        return pressedShoot;
    }

    public boolean getPressedSkill() {
        return pressedSkill;
    }


    public float getCursorRadians(final float playerLocationX, final float playerLocationY) {
        Vector2 cursorLocation = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        // atan2((y2-y1)/(x2-x1))
        return (float) Math.atan2(cursorLocation.y - playerLocationY,
                                  cursorLocation.x - playerLocationX);
    }
}
