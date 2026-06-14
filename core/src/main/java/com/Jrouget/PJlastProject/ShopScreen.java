package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ShopScreen  extends Group {

    private GameMechanic gameMechanic;
    private Label showSold;
    private BitmapFont font;
    private Texture shopBackground;

    private Texture textureExitButton;
    private Texture getTextureExitButtonClicked;

    public ShopScreen(GameMechanic gameMechanic) {

        font = new BitmapFont();
        com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle style = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;

        this.setSize(300,200);

        shopBackground = new Texture(Gdx.files.internal("shopFond.png"));
        textureExitButton = new Texture(Gdx.files.internal("exitButton.png"));
        getTextureExitButtonClicked = new Texture(Gdx.files.internal("exitButtonClicked.png"));

        Image background = new Image(shopBackground);

        TextureRegionDrawable drawExitButton = new TextureRegionDrawable(textureExitButton);
        TextureRegionDrawable drawExitButtonClicked = new TextureRegionDrawable(getTextureExitButtonClicked);

        ImageButton exitButton = new ImageButton(drawExitButton, drawExitButtonClicked);

        showSold = new Label(String.valueOf(gameMechanic.getsoldeJoueur()), style);

        this.setPosition(90, 35);
        exitButton.setPosition(0, 200);
        showSold.setPosition(250,9);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("shop closing");
                setVisible(false);
            }
        });
        this.addActor(background);
        this.addActor(exitButton);
        this.addActor(showSold);

        this.setVisible(false);
    }

}
