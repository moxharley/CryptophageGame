package objects.entitiy.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class EnemyManager {
    //  todo: change to enemy
    private ArrayList<Enemy> enemyList;
    private World world;
    private SpriteBatch batch;

    public EnemyManager(final World world, final SpriteBatch batch) {
        this.world = world;
        this.batch = batch;

        enemyList = new ArrayList<>();
    }

    public void addEnemy(final float width, final float height, final Body body, final EnemyType enemyType) {

        switch (enemyType) {
            case FLYING_1:
                enemyList.add(new Enemy1(width, height, body));
                break;
            default:
                enemyList.add(new Enemy(width, height, body));
                break;
        }

    }

    private World getWorld() {
        return world;
    }

    public void update(final Vector2 playerPosition) {
        for (Enemy enemy : enemyList) {
            enemy.move(playerPosition);
            enemy.update();
        }
    }

    public void render() {
        for (Enemy enemy : enemyList) {
            enemy.render(batch);
        }
    }

    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }
}
