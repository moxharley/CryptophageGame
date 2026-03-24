package helper;

import com.badlogic.gdx.physics.box2d.*;
import objects.projectile.ProjectileShape;

import static helper.GameConstants.PPM;

public class ProjectileHelperService {

    // TODO: do i need this????

    public static Body createBullet(final float x, final float y, final int size, final World world,
                                  final ProjectileShape projectileShape) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(x / PPM, y / PPM);

        //TODO: should this be changed because of the bullet rotation
        bodyDef.fixedRotation = true; //prevent object from rotating
        Body body = world.createBody(bodyDef);

        // Remove the effect of gravity
        body.setGravityScale(0.0f);

        // creates a different polygon shape based on the projectile shape
        PolygonShape shape = getPolygonShape(size, projectileShape);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture((fixtureDef));
        shape.dispose();
        return body;
    }

    private static PolygonShape getPolygonShape(final int size, final ProjectileShape projectileShape) {
        PolygonShape shape = new PolygonShape();

        if (projectileShape == ProjectileShape.BULLET || projectileShape == ProjectileShape.LINE
            || projectileShape == ProjectileShape.PILL || projectileShape == ProjectileShape.RECTANGLE) {

            shape.setAsBox((float) size / 2 / PPM, (float) size / 4 / PPM);  // TODO: make default width changeable

        } else if (projectileShape == ProjectileShape.CIRCLE) {
            shape.setRadius((float) size / 2 / PPM); // TODO could cause issues with the size of projectiles

        } else if (projectileShape == ProjectileShape.ARC) {

            // use width as radius, ignore height.
            shape.setRadius((float) size / 2 / PPM);
        }
        return shape;
    }
}
