package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
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
import com.kotcrab.vis.ui.widget.VisTextField;

public class UsernameScreen implements Screen {

    private MainGame game;
    private Stage stage;

    private VisTextField usernameField;
    private String username;

    private Texture backgroundTexture;
    private Texture submitButtonTexture;
    private Texture submitButtonClickedTexture;

    public UsernameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new com.badlogic.gdx.scenes.scene2d.Stage(new FitViewport(480,  270));

        creerFond();
        creerInterface();


    }

    private void creerFond() {
        backgroundTexture = new Texture(Gdx.files.internal("backgroundFirstScreen.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void creerInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        submitButtonTexture = new Texture(Gdx.files.internal("submitButton.png"));
        submitButtonClickedTexture = new Texture(Gdx.files.internal("submitButtonClicked.png"));

        TextureRegionDrawable submitButtonDraw = new TextureRegionDrawable(submitButtonTexture);
        TextureRegionDrawable submitButtonClickedDraw = new TextureRegionDrawable(submitButtonClickedTexture);

        ImageButton submitButton = new ImageButton(submitButtonDraw, submitButtonClickedDraw);

        usernameField = new VisTextField("");
        usernameField.setMessageText("Username");

        table.setPosition(0,-40);
        table.add(usernameField).width(200).padBottom(10).row();
        table.add(submitButton);

        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    saveUsername(username);
                } else {
                    System.out.println("enter a username first");
                }

            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    private void saveUsername(String username) {
        Net.HttpRequest usernamePost = new Net.HttpRequest(Net.HttpMethods.POST);
        usernamePost.setUrl(game.Supabase_url + "/rest/v1/profils");

        usernamePost.setHeader("apikey", MainGame.Api_key);
        usernamePost.setHeader("Authorization", "Bearer " + game.getUserJwtToken());
        usernamePost.setHeader("Content-Type", "application/json");
        usernamePost.setHeader("Prefer", "return=minimal");

        String payload = "{\"user_id\": \"" + game.getUserId() + "\", \"username\": \"" + username + "\"}";
        usernamePost.setContent(payload);

        Gdx.net.sendHttpRequest(usernamePost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 201) {
                    System.out.println("username stored successfully");
                    Gdx.app.postRunnable(() -> game.setScreen(new FirstScreen(game)));
                } else {
                    System.out.println("error, username couldnt be stored" + httpResponse.getResultAsString());
                }
            }

            @Override
            public void failed(Throwable throwable) {
                System.out.println("error" + throwable.getMessage());
            }

            @Override
            public void cancelled() {

            }
        });
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
        if (stage != null) stage.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (submitButtonTexture != null) submitButtonTexture.dispose();
        if (submitButtonClickedTexture != null) submitButtonClickedTexture.dispose();
    }
}
