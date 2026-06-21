package com.Jrouget.PJlastProject.network;

import com.Jrouget.PJlastProject.MainGame;
import com.Jrouget.PJlastProject.screens.ErrorScreen;
import com.Jrouget.PJlastProject.screens.FirstScreen;
import com.Jrouget.PJlastProject.screens.UsernameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class SupabaseServices {

    private final MainGame game;

    public static String error;

    public SupabaseServices(MainGame game) {

        this.game = game;
    }

    public void supabaseLogin(String email, String password) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(MainGame.Supabase_url + "/auth/v1/token?grant_type=password");
        request.setHeader("apikey", MainGame.Api_key);
        request.setHeader("Content-Type", "application/json");

        String payload = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
        request.setContent(payload);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String ApiResponse = httpResponse.getResultAsString();

                if (httpResponse.getStatus().getStatusCode() == 200) {
                    JsonValue json = new JsonReader().parse(ApiResponse);
                    String token = json.getString("access_token");
                    String id = json.get("user").getString("id");

                    game.setSession(token, id);

                    System.out.println("Connexion successful");

                    verifyUsername();
                } else {
                    System.out.println("Connexion failed" + ApiResponse);
                }
            }

            @Override
            public void failed(Throwable throwable) {
                System.out.println("Connexion failed" + throwable.getMessage());
                error = throwable.getMessage();

                Gdx.app.postRunnable(() -> game.setScreen(new ErrorScreen()));
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
                        game.setUsername(username);
                        System.out.println("welcome back" + username);
                        Gdx.app.postRunnable(() -> game.setScreen(new FirstScreen(game)));
                    } else {
                        System.out.println("user doesn't have a username, please create one");
                        Gdx.app.postRunnable(() -> game.setScreen(new UsernameScreen(game, SupabaseServices.this)));
                    }
                }
            }

            @Override
            public void failed(Throwable throwable) {
                System.out.println("error : " + throwable.getMessage());
                error = throwable.getMessage();
                Gdx.app.postRunnable(() -> game.setScreen(new ErrorScreen()));
            }

            @Override
            public void cancelled() {

            }
        });
    }

    public void saveUsername(String username) {
        Net.HttpRequest usernamePost = new Net.HttpRequest(Net.HttpMethods.POST);
        usernamePost.setUrl(MainGame.Supabase_url + "/rest/v1/profils");

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
                error = throwable.getMessage();
                Gdx.app.postRunnable(() -> game.setScreen(new ErrorScreen()));
            }

            @Override
            public void cancelled() {

            }
        });
    }

    public void getBestRound(int finalRound) {
        if (game.getUserJwtToken() == null) {
            System.out.println("user not connected -> no score stored");
            return;
        }

        Net.HttpRequest getRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        getRequest.setUrl(MainGame.Supabase_url + "/rest/v1/high_scores?select=highest_round&user_id=eq." + game.getUserId());

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
                        int bestRound = jsonArray.get(0).getInt("highest_round");
                        System.out.println("highest score :" + bestRound);

                        if (bestRound < finalRound) {
                            Net.HttpRequest patchScore = new Net.HttpRequest(Net.HttpMethods.PUT);
                            patchScore.setUrl(MainGame.Supabase_url + "/rest/v1/high_scores?user_id=eq." + game.getUserId());

                            patchScore.setHeader("apikey", MainGame.Api_key);
                            patchScore.setHeader("Authorization", "Bearer " + game.getUserJwtToken());
                            patchScore.setHeader("Content-Type", "application/json");
                            patchScore.setHeader("Prefer", "return=minimal");

                            String payload = "{\"user_id\": \"" + game.getUserId() + "\", \"username\": \"" + game.getUsername() + "\", \"highest_round\": " + finalRound + "}";
                            patchScore.setContent(payload);

                            Gdx.net.sendHttpRequest(patchScore, new Net.HttpResponseListener() {
                                @Override
                                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                    if (httpResponse.getStatus().getStatusCode() < 300 && httpResponse.getStatus().getStatusCode() >= 200) {
                                        System.out.println("round successfully stored");
                                    } else {
                                        System.out.println("error : round couldnt be stored" + httpResponse.getResultAsString());
                                    }
                                }

                                @Override
                                public void failed(Throwable throwable) {
                                    System.out.println("error :" + throwable.getMessage());
                                    error = throwable.getMessage();
                                    Gdx.app.postRunnable(() -> game.setScreen(new ErrorScreen()));
                                }

                                @Override
                                public void cancelled() {

                                }
                            });
                        } else {
                            System.out.println("highest round is above or equal to this round : it will not be stored");
                            return;
                        }
                    } else {
                        System.out.println("no round found");

                        Net.HttpRequest scoreRequest = new Net.HttpRequest(Net.HttpMethods.POST);
                        scoreRequest.setUrl(MainGame.Supabase_url + "/rest/v1/high_scores");

                        scoreRequest.setHeader("apikey", MainGame.Api_key);
                        scoreRequest.setHeader("Authorization", "Bearer " + game.getUserJwtToken());
                        scoreRequest.setHeader("Content-Type", "application/json");
                        scoreRequest.setHeader("Prefer", "return=minimal");

                        String payload = "{\"user_id\": \"" + game.getUserId() + "\", \"username\": \"" + game.getUsername() + "\", \"highest_round\": " + finalRound + "}";
                        scoreRequest.setContent(payload);


                        Gdx.net.sendHttpRequest(scoreRequest, new Net.HttpResponseListener() {
                            @Override
                            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                if (httpResponse.getStatus().getStatusCode() == 201) {
                                    System.out.println("round " + finalRound + " successfully stored");
                                } else {
                                    System.out.println("error, cannot save the round" + httpResponse.getResultAsString());
                                }
                            }

                            @Override
                            public void failed(Throwable throwable) {
                                System.out.println("error :" + throwable.getMessage());
                                error = throwable.getMessage();
                                Gdx.app.postRunnable(() -> game.setScreen(new ErrorScreen()));
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
                System.out.println("error :" + throwable.getMessage());
                error = throwable.getMessage();
                Gdx.app.postRunnable(() -> game.setScreen(new ErrorScreen()));
            }

            @Override
            public void cancelled() {

            }
        });
    }
}
