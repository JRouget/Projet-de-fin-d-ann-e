package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private Stage stage;
    private Texture backgroundTexture;
    private MachineSous machineSous;
    private SideMenu sideMenu;
    private GameMechanic gameMechanic;

    @Override
    public void show() {
        System.out.println("Game started");
        gameMechanic = new GameMechanic();
        sideMenu = new SideMenu(gameMechanic);

        stage = new Stage(new FitViewport(480,  270));

        creerFond();
        creerInterface();

        Gdx.input.setInputProcessor(stage);
    }

    private void creerFond() {
        backgroundTexture = new Texture(Gdx.files.internal("backgroundTapis1.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void creerInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        machineSous = new MachineSous(gameMechanic, sideMenu);

        table.add(machineSous);
        table.add(sideMenu);
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
