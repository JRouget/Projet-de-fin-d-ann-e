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

public class ErrorScreen implements Screen {

    private MainGame game;

    private Stage stage;

    private Texture backgroundTexture;
    private Texture submitButtonTexture;
    private Texture submitButtonClickedTexture;
    private Label errorMessage;

    public ErrorScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }

        stage = new com.badlogic.gdx.scenes.scene2d.Stage(new FitViewport(480,  270));

        makeBackground();
        makeUi();
    }

    private void makeBackground() {
        backgroundTexture = new Texture(Gdx.files.internal("backgroundTapis1.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void makeUi() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        submitButtonTexture = new Texture(Gdx.files.internal("submitButton.png"));
        submitButtonClickedTexture = new Texture(Gdx.files.internal("submitButtonClicked.png"));

        TextureRegionDrawable submitButtonDraw = new TextureRegionDrawable(submitButtonTexture);
        TextureRegionDrawable submitButtonClickedDraw = new TextureRegionDrawable(submitButtonClickedTexture);

        ImageButton submitButton = new ImageButton(submitButtonDraw, submitButtonClickedDraw);

        errorMessage = new Label(SupabaseServices.error, VisUI.getSkin());

        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.app.postRunnable(() -> game.setScreen(new AuthScreen(game)));
            }
        });

        table.add(errorMessage).pad(20).row();
        table.add(submitButton);

        Gdx.input.setInputProcessor(stage);
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
