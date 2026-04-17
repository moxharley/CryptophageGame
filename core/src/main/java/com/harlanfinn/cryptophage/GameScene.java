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

import static helper.GameConstants.GRAVITY;
import static helper.GameConstants.FPS;

/**
 * Represents a GameScene, the main game class of this application.
 * @author FinnWylie
 * @version 2026
 */
public class GameScene extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private int width;
    private int height;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Player player;
    private ProjectileManager projectileManager;
    private EnemyManager enemyManager;
    private InputController inputController;
    private MyGameRoot game;

    private int difficulty;

//    private final static ScoreEntry scoreEntry;

    /**
     * Creates a new GameScene.
     * @param game the game root this game screen will connect to as a MyGameRoot
     */
    public GameScene(final MyGameRoot game) {
        this.batch = new SpriteBatch();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.inputController = new InputController();

        // create Box2D world
        this.world = new World(new Vector2(0.0f, -GRAVITY), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.projectileManager = new ProjectileManager(batch);
        this.enemyManager = new EnemyManager(world, batch);

        // Create map from map file
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(getDifficulty());

        this.camera = new OrthographicCamera();
        this.camera.zoom -= 0.6f;

        this.difficulty = 1;
        this.game = game;
    }

    /*
    The main update loop of the game, runs once a frame.
     */
    private void update() {
        spawnWaveIfAllEnemiesDead();

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            int a = 0; // used to trigger breakpoint on key press for debugging
        };

        getWorld().step((float) 1 / FPS, 6, 2);
        cameraUpdate();

        getBatch().setProjectionMatrix(getCamera().combined);
        getOrthogonalTiledMapRenderer().setView(getCamera());

        // update the player
        updatePlayer();
        updateEnemies();
        getProjectileManager().update();

        updateCollisions(getProjectileManager(), getEnemyManager(), getPlayer());
    }

    /*
     * This method is a fantastic example of a horrific implementation SOLID principals and bad
     * design.
     */
    private void updateCollisions(ProjectileManager projectileManager, EnemyManager enemyManager,
                                  Player player) {
        ArrayList<Projectile> projectiles = projectileManager.getProjectileList();
        ArrayList<Enemy> enemies = enemyManager.getEnemyList();
        CollisionManager collisionManager = new CollisionManager(projectiles, enemies, player);

        collisionManager.checkForCollisions();
    }


    private void cameraUpdate() {
        Vector3 position = getCamera().position; // get current camera position

        // get player position, then multiply by 10,
        // then round and then divide by 10. The camera movement is now smoother
        position.x = Math.round(getPlayer().getBody().getPosition().x * 10) / 10f;
        position.y = Math.round(getPlayer().getBody().getPosition().y * 10) / 10f;

        getCamera().position.set(position);
        getCamera().update();
    }

    /**
     * Renders the game screen every frame.
     * @param delta The time in seconds since the last render
     */
    @Override
    public void render(float delta) {
        this.update();

        // clears all colour making a screen the colour of the tiles BG
        Gdx.gl.glClearColor(0.094f, 0.078f, 0.145f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // we want to render map before rendering batch (game objects)
        getOrthogonalTiledMapRenderer().render();

        getBatch().begin();
        getProjectileManager().render();
        getEnemyManager().render();
        getPlayer().render(getBatch());
        getBatch().end();

//        box2DDebugRenderer.render(world, camera.combined.scl(1)); // shows Box2D objects to debug
    }

    /**
     * Gets the world used in this GameScene.
     * @return the world of this GameScene as a World.
     */
    public World getWorld() {
        return world;
    }

    private SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Sets the player to a new player.
     * @param player the new player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    private void updateEnemies() {
        for (Enemy enemy : getEnemyManager().getEnemyList()) {
            if (enemy.attackIfAllowed()) {
                createEnemyProjectile(enemy);
            }
        }
        getEnemyManager().update();
    }

    private void updatePlayer() {
        getInputController().sync();

        if (getInputController().getPressedShoot()) {
            // trigger shot
            if (getPlayer().attackIfAllowed()) {
                createPlayerProjectile();
            }
        }
        //Movement stuff
        getPlayer().move(getInputController().getHorizontalMovement(), getInputController().getVerticalMovement());

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
        Vector2 bulletDirection = getInputController().getCursorVectorFromPlayer();
        int size = getPlayer().getProjectileSize();

        getProjectileManager().addProjectile(projectileShape, projectileColour,
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

        getProjectileManager().addProjectile(projectileShape, projectileColour,
            projectileTeam, damage, speed, lifespan,
            bulletPosition, bulletDirection, size);

    }


    private Vector2 getVectorFromEnemyToPlayer(final Enemy enemy, final Player player) {
        Vector2 enemyToPlayerVector = new Vector2(
            -((enemy.getVectorFromOrigin().x) - getPlayer().getX()),
            -(((enemy.getVectorFromOrigin().y) - getPlayer().getY())));
        return enemyToPlayerVector.sub(0, 0).nor();
    }

    /**
     * The logic for if the window is resized.
     * @param width the new width of the resized window as an int
     * @param height the new height of the resized window as an int
     */
    @Override
    public void resize(final int width, final int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        setWidth(width);
        setHeight(height);

        if (getCamera() == null) {
            setCamera(new OrthographicCamera(width, height));
        } else {
            getCamera().setToOrtho(false, width, height);
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

    /**
     * Adds a new enemy to this game.
     * @param width the width of the new enemy to add as a float
     * @param height the height of the new enemy to add as a float
     * @param body the body of the new enemy to add as a Body
     */
    public void addEnemy(final float width, final float height, final Body body) {
        getEnemyManager().addEnemy(width, height, body);
    }

    private void gameOver(final int score) {
        getGame().setScreen(new GameOverScreen(getGame(), score));
    }

    private void spawnWaveIfAllEnemiesDead() {
        if (getEnemyManager().isEmpty()) {
            spawnNextWave();
        }
    }

    private void spawnNextWave() {
        setOrthogonalTiledMapRenderer(getTileMapHelper().setupMap(getDifficulty()));
        setDifficulty(getDifficulty() + 1);
    }

    private int getDifficulty() {
        return difficulty;
    }

    private void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    private OrthographicCamera getCamera() {
        return camera;
    }

    private void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    private OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
        return orthogonalTiledMapRenderer;
    }

    private void setOrthogonalTiledMapRenderer(OrthogonalTiledMapRenderer orthogonalTiledMapRenderer) {
        this.orthogonalTiledMapRenderer = orthogonalTiledMapRenderer;
    }

    private TileMapHelper getTileMapHelper() {
        return tileMapHelper;
    }

    private InputController getInputController() {
        return inputController;
    }

    private MyGameRoot getGame() {
        return game;
    }
}
