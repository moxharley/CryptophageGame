package objects.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import objects.entitiy.enemy.Enemy;
import objects.entitiy.player.Player;

import java.util.ArrayList;

public class ProjectileManager {
    private ArrayList<Projectile> projectileList;
    private World world;
    private SpriteBatch batch;

    public ProjectileManager(final World world, final SpriteBatch batch) {
        this.world = world;
        this.batch = batch;

        projectileList = new ArrayList<>();
    }

    public void addProjectile(final ProjectileShape projectileShape,
                              final ProjectileColour projectileColour,
                              final ProjectileTeam projectileTeam, final float damage,
                              final float speed, final int lifespan, final Vector2 bulletPosition,
                              final Vector2 bulletDirection, final int size) {

        projectileList.add(new Projectile(projectileShape, projectileColour, projectileTeam,
                                          damage, speed, lifespan, bulletPosition, bulletDirection,
                                          getWorld(), size));

    }

    private World getWorld() {
        return world;
    }

    public void update() {
        for (Projectile projectile : projectileList) {
            projectile.update();
        }
    }

    public void render() {
        for (Projectile projectile : projectileList) {
            projectile.render(batch);
        }
    }

    public void checkForCollisions(final ArrayList<Enemy> enemyArrayList, final Player player) {
        for (Projectile projectile : projectileList) {
            ProjectileTeam projectileTeam = projectile.getProjectileTeam();

            if (projectileTeam == ProjectileTeam.PLAYER) {
                for (Enemy enemy : enemyArrayList) {
                    if (projectile.checkForCollision(enemy.getHitbox())) {
                        enemy.takeDamage(projectile.getDamage());
                    };
                }

            } else if (projectileTeam == ProjectileTeam.ENEMY) {
                if (projectile.checkForCollision(player.getHitbox())) {
                    player.takeDamage(projectile.getDamage());
                }

            } else { // projectileTeam is NEUTRAL
                // check enemies
                for (Enemy enemy : enemyArrayList) {
                    if (projectile.checkForCollision(enemy.getHitbox())) {
                        enemy.takeDamage(projectile.getDamage());
                    };
                }
                // check player
                if (projectile.checkForCollision(player.getHitbox())) {
                    player.takeDamage(projectile.getDamage());
                }

            }
        }
    }

    public ArrayList<Projectile> getProjectileList() {
        return projectileList;
    }
}
