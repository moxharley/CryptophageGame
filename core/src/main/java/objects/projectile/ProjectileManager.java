package objects.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class ProjectileManager {
    protected ArrayList<Projectile> projectileList;
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
                              final float speed, final int lifespan, final float positionX, final float positionY,
                              final float angle, final int size) {

        projectileList.add(new Projectile(projectileShape, projectileColour, projectileTeam,
                                          damage, speed, lifespan, positionX, positionY, angle,
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
}
