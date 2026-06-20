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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.Jrouget.PJlastProject.network.SupabaseServices;

public class AuthScreen implements Screen {

    private MainGame game;
    private SupabaseServices supabaseServices;

    private Stage stage;
    private VisTextField emailField;
    private VisTextField passwordField;

    private Texture backgroundTexture;
    private Texture loginButtonTexture;
    private Texture LoginButtonTextureClicked;
    private Texture guestButtonTexture;
    private Texture guestButtonClickedTexture;

    private String ApiResponse;

    public AuthScreen(MainGame game, SupabaseServices supabaseServices) {
        this.game = game;
        this.supabaseServices = new SupabaseServices(this.game);
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }

        stage = new com.badlogic.gdx.scenes.scene2d.Stage(new FitViewport(480,  270));

        creerFond();
        creerInterface();
    }

    private void creerFond(){
        backgroundTexture = new Texture(Gdx.files.internal("backgroundFirstScreen.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void creerInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        loginButtonTexture = new Texture(Gdx.files.internal("loginButton.png"));
        LoginButtonTextureClicked = new Texture(Gdx.files.internal("loginButtonClicked.png"));
        guestButtonTexture = new Texture(Gdx.files.internal("guestButton.png"));
        guestButtonClickedTexture = new Texture(Gdx.files.internal("guestButtonClicked.png"));

        TextureRegionDrawable loginButtonDraw = new TextureRegionDrawable(loginButtonTexture);
        TextureRegionDrawable loginButtonClickedDraw = new TextureRegionDrawable(LoginButtonTextureClicked);
        TextureRegionDrawable guestButtonDraw = new TextureRegionDrawable(guestButtonTexture);
        TextureRegionDrawable guestButtonClickedDraw = new TextureRegionDrawable(guestButtonClickedTexture);

        ImageButton loginButton = new ImageButton(loginButtonDraw, loginButtonClickedDraw);
        ImageButton guestButton = new ImageButton(guestButtonDraw, guestButtonClickedDraw);

        emailField = new VisTextField("");
        emailField.setMessageText("Email");

        passwordField = new VisTextField("");
        passwordField.setMessageText("Mot-de-passe");
        passwordField.setPasswordMode(true);

        table.setPosition(0,-40);

        table.add(emailField).width(200).padBottom(10).row();
        table.add(passwordField).width(200).padBottom(15).row();
        table.add(loginButton);

        stage.addActor(guestButton);

        guestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new FirstScreen(game));
            }
        });

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                supabaseServices.supabaseLogin(emailField.getText(), passwordField.getText());
            }
        });

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
