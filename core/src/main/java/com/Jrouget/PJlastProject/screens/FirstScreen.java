package com.Jrouget.PJlastProject.screens;

import com.Jrouget.PJlastProject.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;

public class FirstScreen implements Screen {
    private MainGame game;

    private Texture textureButton;
    private Texture textureButtonClicked;
    private Texture backgroundTexture;
    private Stage stage;

    public FirstScreen(MainGame game) {

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

    private void makeBackground(){
        backgroundTexture = new Texture(Gdx.files.internal("backgroundFirstScreen.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void makeUi() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        textureButton = new Texture(Gdx.files.internal("bouton.png"));
        textureButtonClicked = new Texture(Gdx.files.internal("boutonClicked.png"));

        TextureRegionDrawable playButtonDraw = new TextureRegionDrawable(textureButton);
        TextureRegionDrawable playButtonClickedDraw = new TextureRegionDrawable(textureButtonClicked);

        ImageButton playButton = new ImageButton(playButtonDraw, playButtonClickedDraw);

        table.setPosition(0,-40);

        playButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("Game starting..");
                game.setScreen(new GameScreen());
            }
        });
        table.add(playButton);

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
        stage.dispose();
        textureButton.dispose();
        backgroundTexture.dispose();
    }
}
