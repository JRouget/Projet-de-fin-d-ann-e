package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

    public static final String Supabase_url = "https://vdsaqslmpazwlszbjnhj.supabase.co";
    public static final String Api_key = "sb_publishable_q_pE5dmNZnGKnTh6pU_LHg_CUSzBtaL";

    private String userJwtToken = null;
    private String userId = null;

    public void setSession(String token, String id) {
        this.userJwtToken = token;
        this.userId = id;
    }

    public String getUserJwtToken() {
        return userJwtToken;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public void create() {
        setScreen(new AuthScreen(this));
    }
}
