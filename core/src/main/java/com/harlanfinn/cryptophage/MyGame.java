package com.harlanfinn.cryptophage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGame extends Game {

    public static MyGame INSTANCE;
    private int widthScreen;
    private int heightScreen;
    private OrthographicCamera orthographicCamera;

    public void MyGame() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);

        setScreen(new GameScreen(orthographicCamera));
    }
}
