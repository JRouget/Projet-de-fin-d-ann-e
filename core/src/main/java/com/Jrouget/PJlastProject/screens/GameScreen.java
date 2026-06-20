package com.Jrouget.PJlastProject.screens;

import com.Jrouget.PJlastProject.game.CombosAffichage;
import com.Jrouget.PJlastProject.game.GameMechanic;
import com.Jrouget.PJlastProject.game.MachineSous;
import com.Jrouget.PJlastProject.game.SideMenu;
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
    private CombosAffichage combosAffichage;
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

        ShopScreen shopScreen = new ShopScreen(gameMechanic, sideMenu);

        machineSous = new MachineSous(gameMechanic, sideMenu, shopScreen);
        combosAffichage = new CombosAffichage();

        table.add(combosAffichage);
        table.add(machineSous);
        table.add(sideMenu);
        stage.addActor(shopScreen);
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
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
