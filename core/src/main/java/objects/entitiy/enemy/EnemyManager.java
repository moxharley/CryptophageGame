package objects.entitiy.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Represents a EnemyManager.
 * @author FinnWylie
 * @version 2026
 */
public class EnemyManager {
    private ArrayList<Enemy> enemyList;
    private final World world;
    private SpriteBatch batch;
    private int enemyDeathTotal;

    /**
     * Creates a new enemy manager.
     * @param world the Box2D world the enemies will exist in as a World
     * @param batch the sprite batch the enemies will be rendered by as a Batch
     */
    public EnemyManager(final World world, final SpriteBatch batch) {
        this.world = world;
        this.batch = batch;

        this.enemyDeathTotal = 0;

        enemyList = new ArrayList<>();
    }

    /**
     * Adds a new enemy to this EnemyManager.
     * @param width the width of the enemy to add as a float
     * @param height the width of the enemy to add as a float
     * @param body the body of the enemy to add as a Body
     */
    public void addEnemy(final float width, final float height, final Body body) {
        enemyList.add(new Enemy(width, height, body));

    }

    private World getWorld() {
        return world;
    }

    /**
     * Updates all enemies in this EnemyManager.
     * @param playerPosition the player's position to be used as a target as a Vector2
     */
    public void update(final Vector2 playerPosition) {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : enemyList) {
            enemy.move(playerPosition);
            enemy.update();
            if (enemy.isDead()) {
                enemiesToRemove.add(enemy);
                setEnemyDeathTotal(getEnemyDeathTotal() + 1);
                getWorld().destroyBody(enemy.getBody());
            }
        }
        enemyList.removeAll(enemiesToRemove);
    }

    /**
     * Renders each enemy.
     */
    public void render() {
        for (Enemy enemy : enemyList) {
            enemy.render(batch);
        }
    }

    /**
     * Returns an arrayList of all enemies.
     * @return an arrayList containing all enemies
     * TODO: FIX THIS BAD PRACTICE
     */
    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    /**
     * Returns the enemy death total.
     * @return enemyDeathTotal as an int.
     */
    public int getEnemyDeathTotal() {
        return enemyDeathTotal;
    }

    /**
     * Sets enemyDeathTotal.
     * @param enemyDeathTotal the new enemyDeathTotal
     */
    public void setEnemyDeathTotal(final int enemyDeathTotal) {
        this.enemyDeathTotal = enemyDeathTotal;
    }

    /**
     * Returns if this enemyManager has no enemies in it.
     * @return if any enemies are in this enemyManager as a boolean
     */
    public boolean isEmpty() {
        return getEnemyList().isEmpty();
    }
}
