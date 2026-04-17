package objects.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

/**
 * Represnts a projectile manager for this game.
 * @author finnwylie
 * @version 2026
 */
public class ProjectileManager {
    private ArrayList<Projectile> projectileList;
    private SpriteBatch batch;

    /**
     * Creates a new ProjectileManager with an empty array list.
     * @param batch the spriteBatch that the projectiles will be rendered in
     */
    public ProjectileManager(final SpriteBatch batch) {
        this.batch = batch;

        projectileList = new ArrayList<>();
    }

    /**
     * Creates a new projectile and adds it to this projectile manager.
     * @param projectileShape of the projectile being created as a ProjectileShape
     * @param projectileColour of the projectile being created as a ProjectileColour
     * @param projectileTeam of the projectile being created as a ProjectileTeam
     * @param damage of the projectile being created as a float
     * @param speed of the projectile being created as a float
     * @param lifespan of the projectile being created as an int
     * @param bulletPosition of the projectile being created as a Vector2
     * @param bulletDirection of the projectile being created as a Vector2
     * @param size of the projectile being created as an int
     */
    public void addProjectile(final ProjectileShape projectileShape,
                              final ProjectileColour projectileColour,
                              final ProjectileTeam projectileTeam, final float damage,
                              final float speed, final int lifespan, final Vector2 bulletPosition,
                              final Vector2 bulletDirection, final int size) {

        projectileList.add(new Projectile(projectileShape, projectileColour, projectileTeam,
                                          damage, speed, lifespan, bulletPosition, bulletDirection,
                                          size));

    }

    /**
     * Updates every projectile in this ProjectileManager. Designed to be called every frame.
     * Removes projectiles if they are to be removed.
     */
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

    /**
     * Renders every projectile in this projectile manager.
     */
    public void render() {
        for (Projectile projectile : projectileList) {
            projectile.render(batch);
        }
    }

    /**
     * Returns the list of projectiles associated with this projectile manager.
     * @return the projectiles associated with this projectile manager as an array list
     */
    public ArrayList<Projectile> getProjectileList() {
        return projectileList;
    }
}
