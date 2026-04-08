package com.harlanfinn.cryptophage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import dungeon.DungeonGenerator;
import dungeon.DungeonRoomTemplate;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGameRoot extends Game {

    public static MyGameRoot INSTANCE; // ????

    public MyGameRoot() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}
