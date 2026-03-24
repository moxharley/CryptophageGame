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
import controllers.InputController;
import helper.CollisionManager;
import helper.TileMapHelper;
import objects.entitiy.enemy.Enemy;
import objects.entitiy.enemy.EnemyManager;
import objects.entitiy.player.Player;
import objects.projectile.*;

import java.util.ArrayList;

import static helper.GameConstants.*;

public class GameScene extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private int width;
    private int height;

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    // game objects
    private Player player;

    private NewProjectileManager projectileManager;
    private EnemyManager enemyManager;

    private InputController inputController;

    public GameScene() {
        this.batch = new SpriteBatch();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        inputController = new InputController();

        // create Box2D world
        this.world = new World(new Vector2(0.0f, -GRAVITY), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        projectileManager = new NewProjectileManager(world, batch);
        enemyManager = new EnemyManager(world, batch);

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
        updatePlayer();
        enemyManager.update();
        projectileManager.update();

        updateCollisions(getProjectileManager(), getEnemyManager(), getPlayer());

        // Closes game if ESC is pressed
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void updateCollisions(NewProjectileManager projectileManager, EnemyManager enemyManager,
                                  Player player) {
        ArrayList<NewProjectile> projectiles = projectileManager.getProjectileList();
        ArrayList<Enemy> enemies = enemyManager.getEnemyList();
        CollisionManager collisionManager = new CollisionManager(projectiles, enemies, player);

        collisionManager.checkForCollisions();
    }


    private void cameraUpdate() {
        // set camera position to center on the player

        Vector3 position = camera.position; // get current camera position

        //TODO how is ths vs vector 2
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

//        batch.draw(img, 0, y, img.getWidth(), img.getHeight(), 0, 0, img.getWidth(), img.getHeight(), false, true);

        projectileManager.render();

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    private void updatePlayer() {
        inputController.sync();

        //Check for inputs
        if (inputController.getPressedSkill()) {
            // trigger skill
        }
        if (inputController.getPressedShoot()) {
            // trigger shot
            if (player.attackIfAllowed()) {
                createProjectile(true);
            }
        }
        // Check for jump
        if (inputController.getPressedJump()) {
            player.jump();
        }
        //Movement stuff
        player.moveHorizontal(inputController.getHorizontalMovement());

        player.update();

//        // TODO FIX, this is really really bad and doesn't follow any good practices
//        Projectile projectile = player.updateProjectiles();
//        if(projectile != null) {
//            projectileArrayList.add(projectile);
//        }
    }

    private void createProjectile(final boolean playerAttack) {

        ProjectileShape projectileShape;
        ProjectileColour projectileColour;
        ProjectileTeam projectileTeam;
        float damage;
        float speed;
        int lifespan;
        Vector2 bulletPosition;
        Vector2 bulletDirection;
        int size;

        if (playerAttack) {

            projectileShape = player.getProjectileShape();
            projectileColour = player.getProjectileColour();
            projectileTeam = player.getProjectileTeam();
            damage = player.getDamage() * player.getDamageModifier();
            speed = player.getBulletSpeed() * player.getBulletSpeedModifier();
            lifespan = player.getBulletLifespan();
            bulletPosition = new Vector2(player.getX(), player.getY());
            bulletDirection = inputController.getCursorVectorFromPlayer();
            size = player.getProjectileSize();

            projectileManager.addProjectile(projectileShape, projectileColour,
                                            projectileTeam, damage, speed, lifespan,
                                            bulletPosition, bulletDirection, size);
        } else {
            // enemy attack
        }
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
        this.width = width;
        this.height = height;

        if (camera == null) {
            camera = new OrthographicCamera(width, height);
        } else {
            camera.setToOrtho(false, width, height);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public NewProjectileManager getProjectileManager() {
        return projectileManager;
    }
}
