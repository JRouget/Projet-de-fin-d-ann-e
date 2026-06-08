package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MachineSous extends Group {

    private MainGame game;

    private Texture levierTexture;
    private Texture machineTexture;

    private Texture orange;
    private Texture apple;
    private Texture seven;

    private Image roll1;
    private Image roll2;
    private Image roll3;

    private SideMenu sideMenu;

    public MachineSous (final GameMechanic gameMechanic, SideMenu menu) {
        this.sideMenu = menu;
        this.setSize(300, 150);

        //Assets of the GB
        levierTexture = new Texture(Gdx.files.internal("levier.png"));
        machineTexture = new Texture(Gdx.files.internal("gamblingMachine.png"));
        //Assets of the symbols
        orange = new Texture(Gdx.files.internal("orange.png"));
        apple = new Texture(Gdx.files.internal("apple.png"));
        seven = new Texture(Gdx.files.internal("Seven.png"));

        //TexturesDraw of the GB
        TextureRegionDrawable dessinLevier = new TextureRegionDrawable(levierTexture);
        TextureRegionDrawable dessinMachine = new TextureRegionDrawable(machineTexture);

        //Image of the GB (button and body of the machine)
        ImageButton boutonLevier = new ImageButton(dessinLevier);
        Image machineImage = new Image(dessinMachine);
        //Image of the symbols
        roll1 = new Image(orange);
        roll2 = new Image(apple);
        roll3 = new Image(seven);

        boutonLevier.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(gameMechanic.peutJouer()){
                    int[] resultats = gameMechanic.tirage();

                    afficherResultat(resultats[0], resultats[1], resultats[2]);

                    sideMenu.rafraichirArgent(gameMechanic.getsoldeJoueur());
                    sideMenu.rafraichirScore(gameMechanic.getScore());
                    sideMenu.rafraichirQuota(gameMechanic.getQuota());
                    sideMenu.rafraichirManche(gameMechanic.getManche());
                    sideMenu.rafraichirTirage(gameMechanic.getTirage());
                }else {
                    System.out.println("Morricio.. I can't move it move it anymore...");
                    game.setScreen(new FirstScreen(game));
                }
            }
        });

        machineImage.setPosition(150, 0);
        boutonLevier.setPosition(275, 50);

        roll1.setPosition(170, 57);
        roll2.setPosition(202,57);
        roll3.setPosition(233,57);

        this.addActor(machineImage);
        this.addActor(boutonLevier);

        this.addActor(roll1);
        this.addActor(roll2);
        this.addActor(roll3);
    }

    public void afficherResultat(int result1, int result2, int result3){
        roll1.setDrawable(new TextureRegionDrawable(chooseTexture(result1)));
        roll2.setDrawable(new TextureRegionDrawable(chooseTexture(result2)));
        roll3.setDrawable(new TextureRegionDrawable(chooseTexture(result3)));
    }

    public Texture chooseTexture(int chiffre){
        if (chiffre == 1) return apple;
        else if (chiffre == 2) return orange;
        else return seven;
    }

    public void dispose() {
        if (machineTexture != null) machineTexture.dispose();
        if (levierTexture != null) levierTexture.dispose();
    }
}
