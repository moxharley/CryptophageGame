package objects.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import objects.entitiy.enemy.Enemy;
import objects.entitiy.player.Player;

import java.util.ArrayList;

public class ProjectileManager {
    private ArrayList<Projectile> projectileList;
    private SpriteBatch batch;

    public ProjectileManager(final SpriteBatch batch) {
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
                                          size));

    }

    public void update() {
        ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile : projectileList) {
            projectile.update();
            if (projectile.isRemove()) {
                projectilesToRemove.add(projectile);
            }
        }
        projectileList.removeAll(projectilesToRemove);
    }

    public void render() {
        for (Projectile projectile : projectileList) {
            projectile.render(batch);
        }
    }

    public ArrayList<Projectile> getProjectileList() {
        return projectileList;
    }
}
