package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen implements Screen {
    private MainGame game;

    private Stage stage;
    private Texture backgroundTexture;
    private Texture textureButtonReplay;
    private Texture textureButtonReplayClicked;

    private int finalRound;

    public GameOverScreen(MainGame game, int finalRound) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(480,  270));

        creerFond();
        creerInterface();

        Gdx.input.setInputProcessor(stage);
    }

    private void creerFond() {
        backgroundTexture = new Texture(Gdx.files.internal("backgroundGameOver.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void creerInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        textureButtonReplay = new Texture(Gdx.files.internal("boutonRejouer.png"));
        textureButtonReplayClicked = new Texture(Gdx.files.internal("boutonRejouerClicked.png"));

        TextureRegionDrawable dessinButtonReplay = new TextureRegionDrawable(textureButtonReplay);
        TextureRegionDrawable dessinButtonReplayClicked = new TextureRegionDrawable(textureButtonReplayClicked);

        ImageButton buttonReplay = new ImageButton(dessinButtonReplay, dessinButtonReplayClicked);

        table.setPosition(0,-40);

        buttonReplay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new FirstScreen(game));
            }
        });

        table.add(buttonReplay);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
