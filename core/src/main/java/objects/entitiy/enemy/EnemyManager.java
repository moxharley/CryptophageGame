package objects.entitiy.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import objects.projectile.Projectile;

import java.util.ArrayList;

public class EnemyManager {
    //  todo: change to enemy
    private ArrayList<Enemy> enemyList;
    private World world;
    private SpriteBatch batch;
    private int enemyDeathTotal;

    public EnemyManager(final World world, final SpriteBatch batch) {
        this.world = world;
        this.batch = batch;

        this.enemyDeathTotal = 0;

        enemyList = new ArrayList<>();
    }

    public void addEnemy(final float width, final float height, final Body body, final EnemyType enemyType) {

        switch (enemyType) {
            case FLYING_1:
                enemyList.add(new Enemy1(width, height, body));
                break;
            default:
                enemyList.add(new Enemy1(width, height, body));
                break;
        }

    }

    private World getWorld() {
        return world;
    }

    public void update(final Vector2 playerPosition) {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : enemyList) {
            enemy.move(playerPosition);
            enemy.update();
            if (enemy.getIsDead()) {
                enemiesToRemove.add(enemy);
                setEnemyDeathTotal(getEnemyDeathTotal() + 1);
            }
        }
        enemyList.removeAll(enemiesToRemove);
    }

    public void render() {
        for (Enemy enemy : enemyList) {
            enemy.render(batch);
        }
    }

    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    public int getEnemyDeathTotal() {
        return enemyDeathTotal;
    }

    public void setEnemyDeathTotal(final int enemyDeathTotal) {
        this.enemyDeathTotal = enemyDeathTotal;
    }
}
