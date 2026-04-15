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
import com.badlogic.gdx.physics.box2d.Body;
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

    private ProjectileManager projectileManager;
    private EnemyManager enemyManager;

    private InputController inputController;

    private MyGameRoot game;

    private int difficulty;

//    private final static ScoreEntry scoreEntry;

    public GameScene(final MyGameRoot game) {
        this.batch = new SpriteBatch();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        inputController = new InputController();

        // create Box2D world
        this.world = new World(new Vector2(0.0f, -GRAVITY), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        projectileManager = new ProjectileManager(batch);
        enemyManager = new EnemyManager(world, batch);

        this.difficulty = 1;
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(getDifficulty());

        this.camera = new OrthographicCamera();
        camera.zoom -= 0.6f;

        this.game = game;
    }

    private void update() {
        spawnWaveIfAllEnemiesDead();
        int a;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            a = 0; // used to trigger breakpoint on key press
        };

        // TODO understand why 6 and 2 are used by tutorial
        getWorld().step((float) 1 / FPS, 6, 2);
        cameraUpdate();

        getBatch().setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        // update the player
        updatePlayer();
        updateEnemies();
        projectileManager.update();

        updateCollisions(getProjectileManager(), getEnemyManager(), getPlayer());

        // Closes game if ESC is pressed
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void updateCollisions(ProjectileManager projectileManager, EnemyManager enemyManager,
                                  Player player) {
        ArrayList<Projectile> projectiles = projectileManager.getProjectileList();
        ArrayList<Enemy> enemies = enemyManager.getEnemyList();
        CollisionManager collisionManager = new CollisionManager(projectiles, enemies, player);

        collisionManager.checkForCollisions();
    }


    private void cameraUpdate() {
        // set camera position to center on the player

        Vector3 position = camera.position; // get current camera position

//        // get player position, then multiply by 10,
//        // then round and then divide by 10. The camera movement is now smoother
        position.x = Math.round(getPlayer().getBody().getPosition().x* 10) / 10f;
        position.y = Math.round(getPlayer().getBody().getPosition().y* 10) / 10f;

//        position.x = 0f;
//        position.y = 0f;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0.094f, 0.078f, 0.145f, 1);  // clears all colour making a screen the colour of the tiles BG
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // we want to render map before rendering batch (game objects)
        orthogonalTiledMapRenderer.render();

        getBatch().begin();

        // render objects
        projectileManager.render();
        enemyManager.render();
        getPlayer().render(getBatch());

        getBatch().end();

        box2DDebugRenderer.render(world, camera.combined.scl(1)); // shows box2d objects
    }

    public World getWorld() {
        return world;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void updateEnemies() {
        for (Enemy enemy : enemyManager.getEnemyList()) {
            if (enemy.attackIfAllowed()) {
                createEnemyProjectile(enemy);
            }
        }
        enemyManager.update(getPlayer().getPosition());
    }

    private void updatePlayer() {
        inputController.sync();

        if (inputController.getPressedShoot()) {
            // trigger shot
            if (getPlayer().attackIfAllowed()) {
                createPlayerProjectile();
            }
        }
        //Movement stuff
        getPlayer().move(inputController.getHorizontalMovement(), inputController.getVerticalMovement());

        getPlayer().update();

        if (checkIfPlayerDead()) {
            gameOver(getEnemyManager().getEnemyDeathTotal());
        }
    }

    private boolean checkIfPlayerDead() {
        return getPlayer().isDead();
    }

    private void createPlayerProjectile() {
        ProjectileShape projectileShape = getPlayer().getProjectileShape();
        ProjectileColour projectileColour = getPlayer().getProjectileColour();
        ProjectileTeam projectileTeam = getPlayer().getProjectileTeam();
        float damage = getPlayer().getDamage() * getPlayer().getDamageModifier();
        float speed = getPlayer().getBulletSpeed() * getPlayer().getBulletSpeedModifier();
        int lifespan = getPlayer().getBulletLifespan();
        Vector2 bulletPosition = new Vector2(getPlayer().getX(), getPlayer().getY());
        Vector2 bulletDirection = inputController.getCursorVectorFromPlayer();
        int size = getPlayer().getProjectileSize();

        projectileManager.addProjectile(projectileShape, projectileColour,
                                        projectileTeam, damage, speed, lifespan,
                                        bulletPosition, bulletDirection, size);
    }


    private void createEnemyProjectile(final Enemy enemy) {
        ProjectileShape projectileShape = enemy.getProjectileShape();
        ProjectileColour projectileColour = enemy.getProjectileColour();
        ProjectileTeam projectileTeam = enemy.getProjectileTeam();
        float damage = enemy.getDamage() * enemy.getDamageModifier();
        float speed = enemy.getBulletSpeed() * enemy.getBulletSpeedModifier();
        int lifespan = enemy.getBulletLifespan();
        Vector2 bulletPosition = new Vector2(enemy.getX(), enemy.getY());
        int size = enemy.getProjectileSize();

        Vector2 bulletDirection = getVectorFromEnemyToPlayer(enemy, getPlayer());

        projectileManager.addProjectile(projectileShape, projectileColour,
            projectileTeam, damage, speed, lifespan,
            bulletPosition, bulletDirection, size);

    }


    private Vector2 getVectorFromEnemyToPlayer(final Enemy enemy, final Player player) {
        Vector2 enemyToPlayerVector = new Vector2(
            -((enemy.getVectorFromOrigin().x) - getPlayer().getX()),
            -(((enemy.getVectorFromOrigin().y) - getPlayer().getY())));
        return enemyToPlayerVector.sub(0, 0).nor();
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

    private Player getPlayer() {
        return player;
    }

    private EnemyManager getEnemyManager() {
        return enemyManager;
    }

    private ProjectileManager getProjectileManager() {
        return projectileManager;
    }

    public void addEnemy(final float width, final float height, final Body body) {
        getEnemyManager().addEnemy(width, height, body);
    }

    private void gameOver(final int score) {
        game.setScreen(new GameOverScreen(game, score));
    }

    private void spawnWaveIfAllEnemiesDead() {
        if (getEnemyManager().isEmpty()) {
            spawnNextWave();
        }
    }

    private void spawnNextWave() {
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(getDifficulty());
        setDifficulty(getDifficulty() + 1);
    }

    private int getDifficulty() {
        return difficulty;
    }

    private void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
