package com.harlanfinn.cryptophage;

import com.badlogic.gdx.Game;

/**
 * The game root of this game.
 *
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 *
 * @author Harlan Bullock, FinnWylie
 * @version 2026
 */
public class MyGameRoot extends Game {

    private static MyGameRoot instance;

    /**
     * Creates a new myGameRoot.
     */
    public MyGameRoot() {
        instance = this;
    }

    /**
     * Sets the screen of this game root to the main menu. Only triggered at launch.
     */
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}
