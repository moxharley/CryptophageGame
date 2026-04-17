package helper;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Represents a static MapBodyHelperService.
 * @author finnwylie
 * @version 2026
 */
public class MapBodyHelperService {

    /**
     * Static helper method to create a new body.
     * @param x coordinate for this body as a float
     * @param y coordinate for this body as a float
     * @param width of this body as a float
     * @param height of this body as a float
     * @param isStatic if this is a static body as a boolean
     * @param world the Box2D world this body belongs to as a float
     * @return the created Box2D Body as a Body
     */
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true; //prevent object from rotating
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture((fixtureDef));
        shape.dispose();
        return body;
    }
}
