package com.harlanfinn.cryptophage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import save.HighScoreManager;
import save.ScoreEntry;

public class GameOverScreen extends ScreenAdapter {

    private final MyGameRoot game;
    private Stage stage;
    private Skin skin;
    private int score;

    public GameOverScreen(final MyGameRoot game, final int score) {
        this.game = game;
        this.score = score;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        final Label title = new Label("You Died", skin);
        final Label scoreDisplay = new Label("Score: " + score, skin);
        final TextButton backButton = new TextButton("Submit Score", skin);
        final TextField nameEntry = new TextField("", skin);
        nameEntry.setMessageText("Click here to type your name...");

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                String input = nameEntry.getText();
                if (input.isEmpty()) {
                    input = "Player";
                }
                ScoreEntry newScore = new ScoreEntry(score, input);
                HighScoreManager.addScore(newScore);
                game.setScreen(new MainMenuScreen(game));
            }
        });

        final Table table = new Table();
        table.setFillParent(true);
        table.add(title);
        table.row();
        table.add(scoreDisplay).padBottom(10);
        table.row();
        table.add(nameEntry).width(200).pad(30);
        table.row();
        table.add(backButton).width(200).pad(10);

        stage.addActor(table);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

