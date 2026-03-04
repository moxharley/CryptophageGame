package com.harlanfinn.cryptophage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;
import objects.player.Player;

import static helper.Constants.*;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    // game objects
    private Player player;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0.0f, -GRAVITY), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
    }

    private void update() {
        // TODO understand why 6 and 2 are used by tutorial
        world.step((float) 1 / FPS, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        // update the player
        player.update();

        // Closes game if ESC is pressed
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void cameraUpdate() {
        // set camera position to center on the player

        Vector3 position = camera.position; // get current camera position

        // get player position and convert it to world position (PPM), then multiply by 10,
        // then round and then divide by 10. The camera movement is now smoother
        position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
        position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);  // clears all colour making a black screen
        // TODO understand what this does (from tutorial)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // want to render map before rendering batch (game objects)
        orthogonalTiledMapRenderer.render();

        batch.begin();
        // render objects

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
