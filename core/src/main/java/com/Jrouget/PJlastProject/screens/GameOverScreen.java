package com.Jrouget.PJlastProject.screens;

import com.Jrouget.PJlastProject.MainGame;
import com.Jrouget.PJlastProject.network.SupabaseServices;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;

public class GameOverScreen implements Screen {
    private MainGame game;
    private SupabaseServices supabaseServices;

    private Stage stage;
    private Texture backgroundTexture;
    private Texture textureButtonReplay;
    private Texture textureButtonReplayClicked;

    private int finalRound;
    private int bestRound;
    private String username;
    private Label errorMessage;

    public GameOverScreen(MainGame game, int finalRound) {
        this.game = game;
        this.supabaseServices = new SupabaseServices(this.game);
        this.finalRound = finalRound;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(480, 270));

        makeBackground();
        makeUi();

        supabaseServices.getBestRound(finalRound);

        Gdx.input.setInputProcessor(stage);
    }

    private void makeBackground() {
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/backgroundGameOver.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void makeUi() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        textureButtonReplay = new Texture(Gdx.files.internal("buttons/boutonRejouer.png"));
        textureButtonReplayClicked = new Texture(Gdx.files.internal("buttons/boutonRejouerClicked.png"));

        TextureRegionDrawable buttonReplayDraw = new TextureRegionDrawable(textureButtonReplay);
        TextureRegionDrawable buttonReplayClickedDraw = new TextureRegionDrawable(textureButtonReplayClicked);

        ImageButton buttonReplay = new ImageButton(buttonReplayDraw, buttonReplayClickedDraw);

        table.setPosition(0, -40);

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
