package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class GameOverScreen implements Screen {
    private MainGame game;

    private Stage stage;
    private Texture backgroundTexture;
    private Texture textureButtonReplay;
    private Texture textureButtonReplayClicked;

    private int finalRound;
    private int bestRound;
    private String username;

    public GameOverScreen(MainGame game, int finalRound) {
        this.game = game;
        this.finalRound = finalRound;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(480,  270));

        creerFond();
        creerInterface();

        getBestRound();

        Gdx.input.setInputProcessor(stage);
    }

    private void creerFond() {
        backgroundTexture = new Texture(Gdx.files.internal("backgroundGameOver.png"));
        com.badlogic.gdx.scenes.scene2d.ui.Image fond = new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture);
        stage.addActor(fond);
    }

    private void creerInterface() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        textureButtonReplay = new Texture(Gdx.files.internal("boutonRejouer.png"));
        textureButtonReplayClicked = new Texture(Gdx.files.internal("boutonRejouerClicked.png"));

        TextureRegionDrawable dessinButtonReplay = new TextureRegionDrawable(textureButtonReplay);
        TextureRegionDrawable dessinButtonReplayClicked = new TextureRegionDrawable(textureButtonReplayClicked);

        ImageButton buttonReplay = new ImageButton(dessinButtonReplay, dessinButtonReplayClicked);

        table.setPosition(0,-40);

        buttonReplay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new FirstScreen(game));
            }
        });

        table.add(buttonReplay);

    }

    private void getBestRound() {
        if (game.getUserJwtToken() == null) {
            System.out.println("user not connected -> no score stored");
            return;
        }

        Net.HttpRequest getRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        getRequest.setUrl(game.Supabase_url + "/rest/v1/high_scores?select=highest_round&user_id=eq." + game.getUserId());

        getRequest.setHeader("apikey", MainGame.Api_key);
        getRequest.setHeader("Authorization", "Bearer " + game.getUserJwtToken());
        getRequest.setHeader("Accept", "application/json");

        Gdx.net.sendHttpRequest(getRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 200) {
                    String getResponse = httpResponse.getResultAsString();

                    JsonValue jsonArray = new JsonReader().parse(getResponse);

                    if (jsonArray.size > 0) {
                        bestRound = jsonArray.get(0).getInt("highest_round");
                        System.out.println("highest score :" + bestRound);

                        if (bestRound < finalRound) {
                            Net.HttpRequest patchScore = new Net.HttpRequest(Net.HttpMethods.PATCH);
                            patchScore.setUrl(game.Supabase_url + "/rest/v1/high_scores?user_id=eq." + game.getUserId());

                            patchScore.setHeader("apikey", MainGame.Api_key);
                            patchScore.setHeader("Authorization", "Bearer " + game.getUserJwtToken());
                            patchScore.setHeader("Content-Type", "application/json");
                            patchScore.setHeader("Prefer", "return=minimal");

                            String payload = "{\"user_id\": \"" + game.getUserId() + "\", \"highest_round\": " + finalRound + "}";
                            patchScore.setContent(payload);

                            Gdx.net.sendHttpRequest(patchScore, new Net.HttpResponseListener() {
                                @Override
                                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                    if (httpResponse.getStatus().getStatusCode() < 300 && httpResponse.getStatus().getStatusCode() >= 200) {
                                        System.out.println("round successfully stored");
                                    } else {
                                        System.out.println("error : round couldnt be stored");
                                    }
                                }

                                @Override
                                public void failed(Throwable throwable) {

                                }

                                @Override
                                public void cancelled() {

                                }
                            });
                        } else {
                            System.out.println("highest round is above this round : it will not be stored");
                            return;
                        }
                    } else {
                        System.out.println("no round found");

                        Net.HttpRequest scoreRequest = new Net.HttpRequest(Net.HttpMethods.POST);
                        scoreRequest.setUrl(game.Supabase_url + "/rest/v1/high_scores");

                        scoreRequest.setHeader("apikey", MainGame.Api_key);
                        scoreRequest.setHeader("Authorization", "Bearer " + game.getUserJwtToken());
                        scoreRequest.setHeader("Content-Type", "application/json");
                        scoreRequest.setHeader("Prefer", "return=minimal");

                        String payload = "{\"user_id\": \"" + game.getUserId() + "\", \"highest_round\": " + finalRound + "}";
                        scoreRequest.setContent(payload);

                        Gdx.net.sendHttpRequest(scoreRequest, new Net.HttpResponseListener() {
                            @Override
                            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                if (httpResponse.getStatus().getStatusCode() == 201) {
                                    System.out.println("round" + finalRound + "successfully stored");
                                } else {
                                    System.out.println("error, cannot save the round" + httpResponse.getResultAsString());
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
