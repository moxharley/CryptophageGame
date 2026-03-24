package helper;

import java.util.ArrayList;

public class WorldHitboxManager {
    private ArrayList<Hitbox> hitboxList;

    // TODO: finish after world generation
    public WorldHitboxManager() {
        hitboxList = new ArrayList<>();
    }

    public void addTerrainHitbox(final float originX, final float originY,
                              final float width, final float height) {

        hitboxList.add(new Hitbox(originX, originY, width, height));

    }

//    public void update() {
//        ArrayList<Hitbox> hitboxesToRemove = new ArrayList<>();
//        for (Hitbox hitbox : hitboxList) {
//            if (hitbox.isRemove()) {
//                hitboxesToRemove.add(hitbox);
//            }
//        }
//        hitboxList.removeAll(hitboxesToRemove);
//    }

    public ArrayList<Hitbox> getHitboxList() {
        return hitboxList;
    }
}
