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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private Stage stage;
    private Texture backgroundTexture;
    private Texture levierTexture;
    private Texture machineTexture;

    private GameMechanic gameMechanic;

    @Override
    public void show() {
        System.out.println("L'écran est affiché !");

        gameMechanic = new GameMechanic();

        stage = new Stage(new FitViewport(480,  270));

        backgroundTexture = new Texture(Gdx.files.internal("backgroundTapis1.png"));

        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        levierTexture = new Texture(Gdx.files.internal("levier.png"));
        machineTexture = new Texture(Gdx.files.internal("gamblingMachine.png"));

        TextureRegionDrawable dessinLevier = new TextureRegionDrawable(levierTexture);
        TextureRegionDrawable dessinMachine = new TextureRegionDrawable(machineTexture);

        ImageButton boutonLevier = new ImageButton(dessinLevier);
        Image machineImage = new Image(dessinMachine);

        boutonLevier.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameMechanic.tirage();
            }
        });

        table.add(machineImage);
        table.add(boutonLevier);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {

    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void hide() {

    }
    @Override public void dispose() {

    }
}
