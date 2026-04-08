package com.harlanfinn.cryptophage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dungeon.DungeonGenerator;
import dungeon.DungeonRoomTemplate;

public class DungeonScene extends ScreenAdapter {
    private final MyGameRoot game;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private FitViewport viewport;
    private DungeonGenerator testDungeon;

    public DungeonScene(final MyGameRoot game) {
        this.game = game;
    }

    @Override
    public void show() {
        loadRoomTemplates();

        testDungeon = new DungeonGenerator();
        testDungeon.generate(System.currentTimeMillis());

        camera = new OrthographicCamera();
        viewport = new FitViewport(
            DungeonGenerator.STANDARD_WIDTH,
            DungeonGenerator.STANDARD_HEIGHT,
            camera
        );
        viewport.apply();

        renderer = new OrthogonalTiledMapRenderer(
            testDungeon.getDungeonMap(),
            1f / DungeonGenerator.TILE_SIZE
        );

        System.out.println(testDungeon);
        System.out.println(testDungeon.debugDrawLeaves());
    }

    private void loadRoomTemplates() {
        if (DungeonGenerator.ROOMS_S != null) {
            return;
        }

        DungeonGenerator.ROOMS_S = new Array<>();
        DungeonGenerator.ROOMS_MV = new Array<>();
        DungeonGenerator.ROOMS_MH = new Array<>();
        DungeonGenerator.ROOMS_L = new Array<>();

        final TmxMapLoader loader = new TmxMapLoader();

        DungeonGenerator.ROOMS_S.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-s-1.tmx"))
        );
        DungeonGenerator.ROOMS_S.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-s-2.tmx"))
        );
        DungeonGenerator.ROOMS_S.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-s-3.tmx"))
        );
        DungeonGenerator.ROOMS_S.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-s-4.tmx"))
        );

        DungeonGenerator.ROOMS_MV.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-mv-1.tmx"))
        );
        DungeonGenerator.ROOMS_MV.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-mv-2.tmx"))
        );

        DungeonGenerator.ROOMS_MH.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-mh-1.tmx"))
        );
        DungeonGenerator.ROOMS_MH.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-mh-2.tmx"))
        );

        DungeonGenerator.ROOMS_L.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-l-1.tmx"))
        );
        DungeonGenerator.ROOMS_L.add(
            new DungeonRoomTemplate(loader.load("dungeon-rooms/dungeon-l-2.tmx"))
        );
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(final float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            testDungeon.generate(System.currentTimeMillis());
            renderer.dispose();
            renderer = new OrthogonalTiledMapRenderer(
                testDungeon.getDungeonMap(),
                1f / DungeonGenerator.TILE_SIZE
            );
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void dispose() {
        if (renderer != null) {
            renderer.dispose();
        }
    }
}
