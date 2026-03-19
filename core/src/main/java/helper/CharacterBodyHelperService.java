package helper;

import com.badlogic.gdx.physics.box2d.*;

import static helper.GameConstants.PPM;

public class CharacterBodyHelperService {

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true; //prevent object from rotating
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture((fixtureDef));
        shape.dispose();
        return body;
    }
}
