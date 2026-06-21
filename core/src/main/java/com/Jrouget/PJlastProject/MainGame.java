package com.Jrouget.PJlastProject;

import com.Jrouget.PJlastProject.network.SupabaseServices;
import com.Jrouget.PJlastProject.screens.AuthScreen;
import com.badlogic.gdx.Game;

public class MainGame extends Game {

    private SupabaseServices supabaseServices;

    public static final String Supabase_url = "https://vdsaqslmpazwlszbjnhj.supabase.co";
    public static final String Api_key = "sb_publishable_q_pE5dmNZnGKnTh6pU_LHg_CUSzBtaL";

    private String userJwtToken = null;
    private String userId = null;

    private String username = "";

    public MainGame() {

    }

    public void setSession(String token, String id) {
        this.userJwtToken = token;
        this.userId = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserJwtToken() {
        return userJwtToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void create() {
        this.supabaseServices = new SupabaseServices(this);
        setScreen(new AuthScreen(this, supabaseServices));
    }
}
