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
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextField;

public class AuthScreen implements Screen {

    private MainGame game;
    private Stage stage;
    private VisTextField emailField;
    private VisTextField passwordField;

    private Texture backgroundTexture;
    private Texture loginButtonTexture;
    private Texture LoginButtonTextureClicked;
    private Texture guestButtonTexture;
    private Texture guestButtonClickedTexture;

    private String ApiResponse;

    public AuthScreen(MainGame game) {
        this.game = game;
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
                supabaseLogin(emailField.getText(), passwordField.getText());
            }
        });

        Gdx.input.setInputProcessor(stage);

    }

    private void supabaseLogin(String email, String password) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(MainGame.Supabase_url + "/auth/v1/token?grant_type=password");
        request.setHeader("apikey", MainGame.Api_key);
        request.setHeader("Content-Type", "application/json");

        String payload = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
        request.setContent(payload);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                ApiResponse = httpResponse.getResultAsString();

                if (httpResponse.getStatus().getStatusCode() == 200) {
                    JsonValue json = new JsonReader().parse(ApiResponse);
                    String token = json.getString("access_token");
                    String id = json.get("user").getString("id");

                    game.setSession(token, id);

                    System.out.println("Connexion successfull");

                    verifyUsername();
                } else {
                    System.out.println("Connexion failed" + ApiResponse);
                }
            }

            @Override
            public void failed(Throwable throwable) {
                System.out.println("Connexion failed" + throwable.getMessage());
            }

            @Override
            public void cancelled() {

            }

        });
    }

    private void verifyUsername() {
        Net.HttpRequest usernameRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        usernameRequest.setUrl(game.Supabase_url + "/rest/v1/profils?select=username&user_id=eq." + game.getUserId());

        usernameRequest.setHeader("apikey", MainGame.Api_key);
        usernameRequest.setHeader("Authorization", "Bearer " + game.getUserJwtToken());
        usernameRequest.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(usernameRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 200) {
                    String apiAnswer = httpResponse.getResultAsString();
                    JsonValue jsonArray = new JsonReader().parse(apiAnswer);

                    if (jsonArray.size > 0) {
                        String username = jsonArray.get(0).getString("username");
                        System.out.println("user already has a username : " + username);
                        Gdx.app.postRunnable(() -> game.setScreen(new UsernameScreen(game)));
                    } else {
                        System.out.println("user doesn't have a username, please create one");
                        Gdx.app.postRunnable(() -> game.setScreen(new UsernameScreen(game)));
                    }
                }
            }

            @Override
            public void failed(Throwable throwable) {

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

    }
}
