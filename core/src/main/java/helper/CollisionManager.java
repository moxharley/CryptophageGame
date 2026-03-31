package helper;

import objects.entitiy.enemy.Enemy;
import objects.entitiy.player.Player;
import objects.projectile.Projectile;
import objects.projectile.ProjectileTeam;

import java.util.ArrayList;

public class CollisionManager {

    private ArrayList<Projectile> projectileArrayList;
    private ArrayList<Enemy> enemyArrayList;
    private Player player;

    //TODO: get polygons from map and add to collision list

    public CollisionManager(final ArrayList<Projectile> projectileArrayList,
                            final ArrayList<Enemy> enemyArrayList, final Player player) {

        this.projectileArrayList = projectileArrayList;
        this.enemyArrayList = enemyArrayList;
        this.player = player;
    }

    public void checkForCollisions() {
        for (Projectile projectile : projectileArrayList) {
            ProjectileTeam projectileTeam = projectile.getProjectileTeam();
            if (projectileTeam == ProjectileTeam.PLAYER) {
                for (Enemy enemy : enemyArrayList) {
                    if (projectile.checkForCollision(enemy.getHitbox())) {
                        enemy.takeDamage(projectile.getDamage());
                        projectile.setRemove(true);
                    };
                }
            } else { // ENEMY team
                if (projectile.checkForCollision(player.getHitbox())) {
                    player.takeDamage(projectile.getDamage());
                    projectile.setRemove(true);
                }
            }
        }
    }

}
