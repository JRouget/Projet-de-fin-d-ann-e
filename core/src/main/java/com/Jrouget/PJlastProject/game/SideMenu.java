package com.Jrouget.PJlastProject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SideMenu extends Group {

    private Texture textureSideMenu;

    private BitmapFont font;
    private Label affichageArgent;
    private Label affichageScore;
    private Label affichageQuota;
    private Label affichageManche;
    private Label affichageTirage;

    public SideMenu(GameMechanic gameMechanic) {

        this.setSize(300, 200);
        this.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.childrenOnly);

        textureSideMenu = new Texture(Gdx.files.internal("ui/Bandeau.png"));
        TextureRegionDrawable dessinSideMenu = new TextureRegionDrawable(textureSideMenu);
        Image imageSideMenu = new Image(dessinSideMenu);

        imageSideMenu.setSize(120, 270);

        imageSideMenu.setPosition(80, -35);

        this.addActor(imageSideMenu);

        font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        affichageArgent = new Label(String.valueOf(gameMechanic.getPlayerSold()), style);
        affichageScore = new Label(String.valueOf(gameMechanic.getScore()), style);
        affichageQuota = new Label(String.valueOf(gameMechanic.getQuota()), style);
        affichageManche = new Label(String.valueOf(gameMechanic.getRound()), style);
        affichageTirage = new Label(String.valueOf(gameMechanic.getTickets()), style);

        affichageScore.setPosition(135, 170);
        affichageQuota.setPosition(150,115);
        affichageArgent.setPosition(120, 25);
        affichageManche.setPosition(135, 135);
        affichageTirage.setPosition(150, 65);

        this.addActor(affichageScore);
        this.addActor(affichageQuota);
        this.addActor(affichageArgent);
        this.addActor(affichageManche);
        this.addActor(affichageTirage);
    }

    public void refreshMoney(int newMoney) {

        affichageArgent.setText(String.valueOf(newMoney));
    }

    public void refreshScore(int newScore) {

        affichageScore.setText(String.valueOf(newScore));

    }

    public void refreshQuota(int newQuota) {

        affichageQuota.setText(String.valueOf(newQuota));
    }

    public void refreshRound(int newRound) {

        affichageManche.setText(String.valueOf(newRound));
    }

    public void refreshTickets(int newTickets) {

        affichageTirage.setText(String.valueOf(newTickets));
    }

    public void dispose() {
        if (textureSideMenu != null) {
            textureSideMenu.dispose();
        }

        if (font != null) {
            font.dispose();
        }
    }
}



