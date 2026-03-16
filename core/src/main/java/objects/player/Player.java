package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static helper.GameConstants.*;
import static objects.player.TemplatePlayerEntityConstants.BASE_JUMP_VELOCITY;
import static objects.player.TemplatePlayerEntityConstants.BASE_MOVE_SPEED;

public class Player extends PlayerEntity {

    private int jumpCounter;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = BASE_MOVE_SPEED;

        this.jumpCounter = 0;
    }

    @Override
    public void update() {
        // move x & y to the current body position
        // x & y will be in the centre of our body
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        checkUserInput();
    }

    // TODO this is incomplete
    @Override
    public void render(SpriteBatch batch) {

    }

    private void checkUserInput() {
        velX = 0; // always reset to zero else we cannot stop the movement
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter < 2) {
            float force = body.getMass() * BASE_JUMP_VELOCITY;

            body.setLinearVelocity(body.getLinearVelocity().x, 0); // set the fall speed to 0 so we can jump again

            // apply force to body in y direction (0 in x)
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCounter++;
        }

        // reset jump counter
        if (body.getLinearVelocity().y == 0) { // could this cause a bug allowing a jump reset at the apex?
            jumpCounter = 0;
        }

        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : GRAVITY);
    }

}
