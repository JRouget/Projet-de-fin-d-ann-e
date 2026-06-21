package com.Jrouget.PJlastProject.screens;

import com.Jrouget.PJlastProject.MainGame;
import com.Jrouget.PJlastProject.network.SupabaseServices;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;

public class FirstScreen implements Screen {
    private MainGame game;

    private Texture textureButton;
    private Texture textureButtonClicked;
    private Texture backgroundTexture;
    private Stage stage;

    private Texture leaderboardBackgroundTexture;
    private Texture textureLeaderboardButton;
    private Texture textureLeaderboardButtonClicked;
    private Texture textureExitButton;
    private Texture textureExitButtonClicked;

    private Group leaderPopUp;
    private Table scoresTable;

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
        makeLeaderboard();
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

        Label scoreLabel = new Label("High Score: " + game.getHighScore(), VisUI.getSkin());

        game.supabaseServices.fetchHighScore(scoreLabel);

        table.add(scoreLabel).padBottom(20).row();

        textureButton = new Texture(Gdx.files.internal("bouton.png"));
        textureButtonClicked = new Texture(Gdx.files.internal("boutonClicked.png"));
        textureLeaderboardButton = new Texture(Gdx.files.internal("leaderboardButton.png"));
        textureLeaderboardButtonClicked = new Texture(Gdx.files.internal("leaderboardButtonClicked.png"));

        TextureRegionDrawable playButtonDraw = new TextureRegionDrawable(textureButton);
        TextureRegionDrawable playButtonClickedDraw = new TextureRegionDrawable(textureButtonClicked);
        TextureRegionDrawable leaderboardButtonDraw = new TextureRegionDrawable(textureLeaderboardButton);
        TextureRegionDrawable leaderboardButtonClickedDraw = new TextureRegionDrawable(textureLeaderboardButtonClicked);

        ImageButton playButton = new ImageButton(playButtonDraw, playButtonClickedDraw);
        ImageButton leaderboardButton = new ImageButton(leaderboardButtonDraw, leaderboardButtonClickedDraw);

        table.setPosition(0,-40);

        playButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("Game starting..");
                game.setScreen(new GameScreen());
            }
        });

        leaderboardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                leaderPopUp.setVisible(true);
                scoresTable.clearChildren();
                scoresTable.add(new Label("Loading...", VisUI.getSkin())).row();
                game.supabaseServices.fetchLeaderboard(scoresTable);
            }
        });

        table.add(playButton).padBottom(10).row();
        table.add(leaderboardButton);

        Gdx.input.setInputProcessor(stage);

    }

    private void makeLeaderboard() {
        leaderboardBackgroundTexture = new Texture(Gdx.files.internal("leaderboardBackground.png"));
        Image background = new Image(leaderboardBackgroundTexture);
        background.setPosition((480 - background.getWidth()) / 2, (270 - background.getHeight()) / 2);

        leaderPopUp = new Group();
        leaderPopUp.setVisible(false);

        leaderPopUp.addActor(background);

        scoresTable = new Table();
        scoresTable.setSize(background.getWidth(), background.getHeight());
        scoresTable.setPosition(background.getX(), background.getY());
        leaderPopUp.addActor(scoresTable);

        textureExitButton = new Texture(Gdx.files.internal("exitButton.png"));
        textureExitButtonClicked = new Texture(Gdx.files.internal("exitButtonClicked.png"));

        TextureRegionDrawable drawExitButton = new TextureRegionDrawable(textureExitButton);
        TextureRegionDrawable drawExitButtonClicked = new TextureRegionDrawable(textureExitButtonClicked);

        ImageButton exitButton = new ImageButton(drawExitButton, drawExitButtonClicked);
        exitButton.setPosition(background.getX() + background.getWidth() - exitButton.getWidth() - 10, background.getY() + 10);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                leaderPopUp.setVisible(false);
            }
        });

        leaderPopUp.addActor(exitButton);
        stage.addActor(leaderPopUp);
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
