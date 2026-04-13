package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.harlanfinn.cryptophage.GameScene;
import objects.entitiy.enemy.EnemyType;
import objects.entitiy.player.Player;

import static helper.GameConstants.PPM;

public class TileMapHelper {
    private TiledMap tiledMap;
    private GameScene gameScreen;

    public TileMapHelper(GameScene gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("../assets/placeholders/maps/demo.tmx");
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects()); // "objects" is the name of the objects layer in the map in tiled
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {

            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }

            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                // set a name so we can select the correct one
                String rectangleName = mapObject.getName();

                if (rectangleName.equals("player")) {
                    Body body = MapBodyHelperService.createBody(
                        rectangle.getX() + rectangle.getWidth() / 2f, // we want the center of the rectangle
                        rectangle.getY() + rectangle.getHeight() / 2f,
                        rectangle.getWidth(), rectangle.getHeight(), false, // non-static object (can move)
                        gameScreen.getWorld()
                    );

                    gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body));
                }

                if (rectangleName.equals("flyingEnemy1")) {
                    Body body = MapBodyHelperService.createBody(
                        rectangle.getX() + rectangle.getWidth() / 2f, // we want the center of the rectangle
                        rectangle.getY() + rectangle.getHeight() / 2f,
                        rectangle.getWidth(), rectangle.getHeight(), false, // non-static object (can move)
                        gameScreen.getWorld()
                    );

                    body.setGravityScale(0.0f); // make fly

                    gameScreen.addEnemy(rectangle.getWidth(), rectangle.getHeight(), body, EnemyType.FLYING_1);
                }
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices(); // each point has 2 vertex coordinates
        Vector2[] worldVertices = new Vector2[vertices.length / 2]; // each V2 obj has 1 point which is 2 vertex coordinates in above array

        for (int i = 0; i < vertices.length / 2; ++i) { // always take pairs of vertex coordinate as a tuple so only iterate over half
            // do this PPM transformation so it matches our Box2D world
            Vector2 current = new Vector2(
                vertices[i * 2] / PPM,
                vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}
