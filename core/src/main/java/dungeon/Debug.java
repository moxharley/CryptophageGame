package dungeon;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public final class Debug implements ApplicationListener {

    public static Debug INSTANCE;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private FitViewport viewport;
    private DungeonGenerator testDungeon;

    @Override
    public void create() {
        // Initializing the arrays for room templates
        DungeonGenerator.ROOMS_S = new Array<>();
        DungeonGenerator.ROOMS_MV = new Array<>();
        DungeonGenerator.ROOMS_MH = new Array<>();
        DungeonGenerator.ROOMS_L = new Array<>();

        // Loading the room templates
        TmxMapLoader loader = new TmxMapLoader();
        DungeonGenerator.ROOMS_S.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-s-1.tmx"))
        );
        DungeonGenerator.ROOMS_S.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-s-2.tmx"))
        );
        DungeonGenerator.ROOMS_S.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-s-3.tmx"))
        );
        DungeonGenerator.ROOMS_S.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-s-4.tmx"))
        );
        DungeonGenerator.ROOMS_MV.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-mv-1.tmx"))
        );
        DungeonGenerator.ROOMS_MV.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-mv-2.tmx"))
        );
        DungeonGenerator.ROOMS_MH.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-mh-1.tmx"))
        );
        DungeonGenerator.ROOMS_MH.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-mh-2.tmx"))
        );
        DungeonGenerator.ROOMS_L.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-l-1.tmx"))
        );
        DungeonGenerator.ROOMS_L.add(
            new DungeonRoomTemplate(loader.load("../assets/dungeon-rooms/dungeon-l-2.tmx"))
        );

        testDungeon = new DungeonGenerator();
        testDungeon.generate(System.currentTimeMillis());

        camera = new OrthographicCamera();

        viewport = new FitViewport(DungeonGenerator.STANDARD_WIDTH, DungeonGenerator.STANDARD_HEIGHT, camera);

        viewport.apply();

        renderer = new OrthogonalTiledMapRenderer(testDungeon.getDungeonMap(), 1f / DungeonGenerator.TILE_SIZE);

        System.out.println(testDungeon);
        System.out.println(testDungeon.debugDrawLeaves());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
