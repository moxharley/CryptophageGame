package helper;

import objects.entitiy.enemy.Enemy;
import objects.entitiy.player.Player;
import objects.projectile.Projectile;
import objects.projectile.ProjectileTeam;
import java.util.ArrayList;
import static helper.GameConstants.PROJECTILE_LIFESPAN;

/**
 * Represents a collision manager of a game.
 * @author finnwylie
 * @version 2026
 */
public class CollisionManager {

    private ArrayList<Projectile> projectileArrayList;
    private ArrayList<Enemy> enemyArrayList;
    private Player player;

    /**
     * Creates a new CollisionManager.
     * @param projectileArrayList the ArrayList of type Projectile used by the collision manager
     * @param enemyArrayList the ArrayList of type Enemy used by the collision manager
     * @param player the player used by the collision manager as type player
     */
    public CollisionManager(final ArrayList<Projectile> projectileArrayList,
                            final ArrayList<Enemy> enemyArrayList, final Player player) {

        this.projectileArrayList = projectileArrayList;
        this.enemyArrayList = enemyArrayList;
        this.player = player;
    }

    /**
     * Checks for collisions between projectiles and enemies or the player.
     */
    public void checkForCollisions() {
        for (Projectile projectile : getProjectileArrayList()) {

            // increase projectile age and set remove flag if above lifespan
            increaseAge(projectile.getHitbox());
            if (checkOlderThanLifespan(projectile.getHitbox())) {
                projectile.setRemove(true);
                continue;
            }

            ProjectileTeam projectileTeam = projectile.getProjectileTeam();
            if (projectileTeam == ProjectileTeam.PLAYER) {
                for (Enemy enemy : getEnemyArrayList()) {
                    if (projectile.checkForCollision(enemy.getHitbox())) {
                        enemy.takeDamage(projectile.getDamage());
                        projectile.setRemove(true);
                    };
                }
            } else { // ENEMY team projectile
                if (projectile.checkForCollision(getPlayer().getHitbox())) {
                    getPlayer().takeDamage(projectile.getDamage());
                    projectile.setRemove(true);
                }
            }
        }
    }

    private void increaseAge(Hitbox hitbox) {
        hitbox.setAge(hitbox.getAge() + 1);
    }

    private boolean checkOlderThanLifespan(Hitbox hitbox) {
        return hitbox.getAge() > PROJECTILE_LIFESPAN;
    }

    private ArrayList<Projectile> getProjectileArrayList() {
        return projectileArrayList;
    }

    private ArrayList<Enemy> getEnemyArrayList() {
        return enemyArrayList;
    }

    private Player getPlayer() {
        return player;
    }
}
