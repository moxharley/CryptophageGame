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
import objects.entitiy.player.Player;

import java.util.Random;

/**
 * Represents a TileMapHelper for this game.
 * @author finnwylie
 * @version 2026
 */
public class TileMapHelper {
    private TiledMap tiledMap;
    private GameScene gameScreen;
    private boolean firstRound = true;

    /**
     * Creates a new TileMapHelper.
     * @param gameScreen the game screen that this tileMapHelper will create the map for.
     */
    public TileMapHelper(final GameScene gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Sets up this TileMapHelper from a .tmx file.
     * @param difficulty the difficulty of this game as an int
     * @return the OrthogonalTiledMapRenderer that is created after parsing all map objects
     */
    public OrthogonalTiledMapRenderer setupMap(final int difficulty) {
        tiledMap = new TmxMapLoader().load("../assets/placeholders/maps/demo.tmx");

        // "objects" is the name of the objects layer in the map in tiled
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects(), difficulty, firstRound);
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(final MapObjects mapObjects, final int difficulty, final boolean firstRound) {
        int enemyCount = 0;
        Random random = new Random();
        for (MapObject mapObject : mapObjects) {

            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }

            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

                // set a name so we can select the correct one
                String rectangleName = mapObject.getName();

                if (rectangleName.equals("player") && firstRound) {
                    createPlayer(rectangle);
                }

                if (rectangleName.equals("enemy") && random.nextBoolean() && enemyCount < difficulty) {
                    createEnemy(rectangle);
                    enemyCount ++;
                }
            }
        }
    }

    private void createPlayer(final Rectangle rectangle) {
        Body body = MapBodyHelperService.createBody(
            rectangle.getX() + rectangle.getWidth() / 2f, // we want the centre of the rectangle
            rectangle.getY() + rectangle.getHeight() / 2f,
            rectangle.getWidth(), rectangle.getHeight(), false, // non-static object (can move)
            gameScreen.getWorld()
        );

        gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body));
        this.firstRound = false;
    }

    private void createEnemy(final Rectangle rectangle) {
        Body body = MapBodyHelperService.createBody(
            rectangle.getX() + rectangle.getWidth() / 2f, // we want the centre of the rectangle
            rectangle.getY() + rectangle.getHeight() / 2f,
            rectangle.getWidth(), rectangle.getHeight(), false, // non-static object (can move)
            gameScreen.getWorld()
        );

        gameScreen.addEnemy(rectangle.getWidth(), rectangle.getHeight(), body);
    }

    private void createStaticBody(final PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(final PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices(); // each point has 2 vertex coordinates
        Vector2[] worldVertices = new Vector2[vertices.length / 2]; // each V2 obj has 1 point which is 2 vertex coordinates in above array

        for (int i = 0; i < vertices.length / 2; ++i) { // always take pairs of vertex coordinate as a tuple so only iterate over half
            Vector2 current = new Vector2(
                vertices[i * 2],
                vertices[i * 2 + 1]);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}
