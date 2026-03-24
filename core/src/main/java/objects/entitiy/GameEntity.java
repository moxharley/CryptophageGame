package objects.entitiy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class GameEntity {

    protected float x;
    protected float y;
    protected float velX;
    protected float velY;
    protected float speed;

    protected float width;
    protected float height;

    protected Body body;

    // will take x & y from body so no need to pass the info twice
    public GameEntity(float width, float height, Body body) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;

        this.width = width;
        this.height = height;
        this.body = body;

        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }

    public void update() { }

    public void render(SpriteBatch batch) { }

    public float getX() {
        return x;
    }

//    public void setX(float x) {
//        this.x = x;
//    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Body getBody() {
        return body;
    }

}
