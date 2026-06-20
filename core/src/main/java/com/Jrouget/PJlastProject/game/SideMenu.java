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

        textureSideMenu = new Texture(Gdx.files.internal("Bandeau.png"));
        TextureRegionDrawable dessinSideMenu = new TextureRegionDrawable(textureSideMenu);
        Image imageSideMenu = new Image(dessinSideMenu);

        imageSideMenu.setSize(120, 270);

        imageSideMenu.setPosition(80, -35);

        this.addActor(imageSideMenu);

        font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        affichageArgent = new Label(String.valueOf(gameMechanic.getsoldeJoueur()), style);
        affichageScore = new Label(String.valueOf(gameMechanic.getScore()), style);
        affichageQuota = new Label(String.valueOf(gameMechanic.getQuota()), style);
        affichageManche = new Label(String.valueOf(gameMechanic.getManche()), style);
        affichageTirage = new Label(String.valueOf(gameMechanic.getTirage()), style);

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

    public void rafraichirArgent(int nouvelArgent) {
        affichageArgent.setText(String.valueOf(nouvelArgent));
    }

    public void rafraichirScore(int nouveauScore) {
        affichageScore.setText(String.valueOf(nouveauScore));
    }

    public void rafraichirQuota(int nouveauQuota) {
        affichageQuota.setText(String.valueOf(nouveauQuota));
    }

    public void rafraichirManche(int nouvelleManche) {
        affichageManche.setText(String.valueOf(nouvelleManche));
    }

    public void rafraichirTirage(int nouveauTirage) {
        affichageTirage.setText(String.valueOf(nouveauTirage));
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



