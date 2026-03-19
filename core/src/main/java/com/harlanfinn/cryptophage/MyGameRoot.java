package com.harlanfinn.cryptophage;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGameRoot extends Game {

    public static MyGameRoot INSTANCE; // ????

    public void MyGame() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        setScreen(new GameScene());
    }
}
