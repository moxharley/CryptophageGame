package dungeon;

import com.badlogic.gdx.ApplicationListener;

public final class Debug implements ApplicationListener {

    public static Debug INSTANCE;

    @Override
    public void create() {
        DungeonGenerator testDungeon = new DungeonGenerator();
        testDungeon.generate(System.currentTimeMillis());
        System.out.println(testDungeon);
        System.out.println(testDungeon.debugDrawLeaves());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
