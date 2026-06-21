package com.Jrouget.PJlastProject.game;

import com.Jrouget.PJlastProject.screens.GameOverScreen;
import com.Jrouget.PJlastProject.MainGame;
import com.Jrouget.PJlastProject.screens.ShopScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MachineSous extends Group {

    private Texture leverTexture;
    private Texture machineTexture;

    private Texture orange;
    private Texture apple;
    private Texture seven;

    private Image roll1;
    private Image roll2;
    private Image roll3;

    private SideMenu sideMenu;

    private ShopScreen shopScreen;

    public MachineSous (final GameMechanic gameMechanic, SideMenu menu, ShopScreen shopScreen) {
        this.sideMenu = menu;
        this.setSize(300, 150);

        this.shopScreen = shopScreen;

        //Assets of the GB
        leverTexture = new Texture(Gdx.files.internal("ui/levier.png"));
        machineTexture = new Texture(Gdx.files.internal("ui/gamblingMachine.png"));
        //Assets of the symbols
        orange = new Texture(Gdx.files.internal("items/orange.png"));
        apple = new Texture(Gdx.files.internal("items/apple.png"));
        seven = new Texture(Gdx.files.internal("items/Seven.png"));

        //TexturesDraw of the GB
        TextureRegionDrawable leverDraw = new TextureRegionDrawable(leverTexture);
        TextureRegionDrawable dessinMachine = new TextureRegionDrawable(machineTexture);

        //Image of the GB (button and body of the machine)
        ImageButton boutonLevier = new ImageButton(leverDraw);
        Image machineImage = new Image(dessinMachine);

        //Image of the symbols
        roll1 = new Image(orange);
        roll2 = new Image(apple);
        roll3 = new Image(seven);

        boutonLevier.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(gameMechanic.canPlay()){
                    int mancheAvant = gameMechanic.getRound();

                    int[] results = gameMechanic.tirage();

                    getResults(results[0], results[1], results[2]);

                    sideMenu.refreshMoney(gameMechanic.getPlayerSold());
                    sideMenu.refreshScore(gameMechanic.getScore());
                    sideMenu.refreshQuota(gameMechanic.getQuota());
                    sideMenu.refreshRound(gameMechanic.getRound());
                    sideMenu.refreshTickets(gameMechanic.getTickets());

                    if(gameMechanic.getRound() > mancheAvant) {
                        System.out.println("Shop opening...");
                        shopScreen.refreshShop(gameMechanic);
                        shopScreen.setVisible(true);
                    }

                }else {
                    System.out.println("No tickets left, game over");
                    MainGame game = (MainGame) Gdx.app.getApplicationListener();
                    game.setScreen(new GameOverScreen(game, gameMechanic.getRound()));
                }
            }
        });

        machineImage.setPosition(170, 0);
        boutonLevier.setPosition(295, 60);

        roll1.setPosition(190, 57);
        roll2.setPosition(222,57);
        roll3.setPosition(253,57);

        this.addActor(machineImage);
        this.addActor(boutonLevier);

        this.addActor(roll1);
        this.addActor(roll2);
        this.addActor(roll3);
    }

    public void getResults(int result1, int result2, int result3){
        roll1.setDrawable(new TextureRegionDrawable(chooseTexture(result1)));
        roll2.setDrawable(new TextureRegionDrawable(chooseTexture(result2)));
        roll3.setDrawable(new TextureRegionDrawable(chooseTexture(result3)));
    }

    public Texture chooseTexture(int number){
        if (number == 1) return apple;
        else if (number == 2) return orange;
        else return seven;
    }

    public void dispose() {
        if (machineTexture != null) machineTexture.dispose();
        if (leverTexture != null) leverTexture.dispose();
    }
}
