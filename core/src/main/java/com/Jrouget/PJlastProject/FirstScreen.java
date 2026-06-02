package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;

public class FirstScreen implements Screen {
    private MainGame game;

    private Texture textureBouton;
    private Texture textureBoutonClicked;
    private Texture backgroundTexture;
    private Stage stage;

    public FirstScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) {
            VisUI.load();

            stage = new com.badlogic.gdx.scenes.scene2d.Stage(new FitViewport(480,  270));

            creerFond();
            creerInterface();
        }
    }

    private void creerFond(){
        backgroundTexture = new Texture(Gdx.files.internal("backgroundTapis1.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void creerInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        textureBouton = new Texture(Gdx.files.internal("bouton.png"));
        textureBoutonClicked = new Texture(Gdx.files.internal("boutonClicked.png"));

        TextureRegionDrawable dessinBouton = new TextureRegionDrawable(textureBouton);
        TextureRegionDrawable dessinBoutonClicked = new TextureRegionDrawable(textureBoutonClicked);

        ImageButton boutonDemarrer = new ImageButton(dessinBouton, dessinBoutonClicked);

        boutonDemarrer.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("Game starting..");
                game.setScreen(new GameScreen());
            }
        });
        table.add(boutonDemarrer);

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
        if(width <= 0 || height <= 0) return;

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        stage.dispose();
        textureBouton.dispose();
        backgroundTexture.dispose();
    }
}
