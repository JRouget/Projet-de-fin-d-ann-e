package com.Jrouget.PJlastProject.screens;

import com.Jrouget.PJlastProject.game.GameMechanic;
import com.Jrouget.PJlastProject.game.ShopItems;
import com.Jrouget.PJlastProject.game.SideMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShopScreen  extends Group {

    private GameMechanic gameMechanic;
    private Label showSold;
    private BitmapFont font;
    private Texture shopBackground;
    private Label.LabelStyle style;

    private Texture textureExitButton;
    private Texture textureExitButtonClicked;

    private ShopItems item1, item2, item3;
    private Label priceSlot1, priceSlot2,  priceSlot3;
    private Image imageSlot1, imageSlot2, imageSlot3;

    private Group infoPopup;
    private Texture backgroundInfoPopup;
    private Label infoName;
    private Label infoDescription;

    private Texture textureBuyButton;
    private ImageButton buyButton1, buyButton2, buyButton3;

    public ShopScreen(GameMechanic gameMechanic, SideMenu sideMenu) {

        font = new BitmapFont();
        style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;


        this.setSize(300,200);

        shopBackground = new Texture(Gdx.files.internal("backgrounds/shopFond.png"));
        textureExitButton = new Texture(Gdx.files.internal("buttons/exitButton.png"));
        textureExitButtonClicked = new Texture(Gdx.files.internal("buttons/exitButtonClicked.png"));
        textureBuyButton = new Texture(Gdx.files.internal("buttons/buyButton.png"));

        Image background = new Image(shopBackground);

        TextureRegionDrawable drawExitButton = new TextureRegionDrawable(textureExitButton);
        TextureRegionDrawable drawExitButtonClicked = new TextureRegionDrawable(textureExitButtonClicked);
        TextureRegionDrawable drawBuyButton = new TextureRegionDrawable(textureBuyButton);

        ImageButton exitButton = new ImageButton(drawExitButton, drawExitButtonClicked);

        buyButton1 = new ImageButton(drawBuyButton);
        buyButton2 = new ImageButton(drawBuyButton);
        buyButton3 = new ImageButton(drawBuyButton);

        buyButton1.setPosition(20,30);
        buyButton2.setPosition(122,30);
        buyButton3.setPosition(223,30);

        showSold = new Label(String.valueOf(gameMechanic.getPlayerSold()), style);

        this.setPosition(90, 35);
        exitButton.setPosition(0, 200);
        showSold.setPosition(250,9);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("shop closing");

                gameMechanic.prepareNewRound();

                sideMenu.refreshTickets(gameMechanic.getTickets());
                sideMenu.refreshMoney(gameMechanic.getPlayerSold());

                setVisible(false);
            }
        });

        buyButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (item1 != null && gameMechanic.getPlayerSold() >= item1.getPrice()) {
                    System.out.println("buying" + item1.getName());

                    gameMechanic.buying(item1.getPrice());

                    item1.boost(gameMechanic);

                    showSold.setText(String.valueOf(gameMechanic.getPlayerSold()));

                    buyButton1.setVisible(false);
                    imageSlot1.setVisible(false);
                    priceSlot1.setText("Vendu");

                    sideMenu.refreshTickets(gameMechanic.getTickets());
                    sideMenu.refreshMoney(gameMechanic.getPlayerSold());
                }
            }
        });

        buyButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (item2 != null && gameMechanic.getPlayerSold() >= item2.getPrice()) {
                    System.out.println("buying" + item2.getName());

                    gameMechanic.buying(item2.getPrice());

                    item2.boost(gameMechanic);

                    showSold.setText(String.valueOf(gameMechanic.getPlayerSold()));


                    buyButton2.setVisible(false);
                    imageSlot2.setVisible(false);
                    priceSlot2.setText("Vendu");

                    sideMenu.refreshTickets(gameMechanic.getTickets());
                    sideMenu.refreshMoney(gameMechanic.getPlayerSold());
                }
            }
        });

        buyButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (item3 != null && gameMechanic.getPlayerSold() >= item3.getPrice()) {
                    System.out.println("buying" + item3.getName());

                    gameMechanic.buying(item3.getPrice());

                    item3.boost(gameMechanic);

                    showSold.setText(String.valueOf(gameMechanic.getPlayerSold()));


                    buyButton3.setVisible(false);
                    imageSlot3.setVisible(false);
                    priceSlot3.setText("Vendu");

                    sideMenu.refreshTickets(gameMechanic.getTickets());
                    sideMenu.refreshMoney(gameMechanic.getPlayerSold());
                }
            }
        });

        this.addActor(background);
        this.addActor(exitButton);
        this.addActor(showSold);

        this.addActor(buyButton1);
        this.addActor(buyButton2);
        this.addActor(buyButton3);

        createShopItems();

        this.setVisible(false);
    }

    public void createShopItems() {

        infoPopup = new Group();
        backgroundInfoPopup = new Texture(Gdx.files.internal("ui/infoPopup.png"));
        Image infoPopupImage = new Image(backgroundInfoPopup);

        infoName = new Label("", style);
        infoName.setFontScale(0.6f);

        infoDescription = new Label("", style);
        infoDescription.setFontScale(0.4f);

        infoName.setPosition(10,35);
        infoDescription.setPosition(10,10);

        priceSlot1 = new Label("", style);
        priceSlot2 = new Label("", style);
        priceSlot3 = new Label("", style);

        imageSlot1 = new Image();
        imageSlot2 = new Image();
        imageSlot3 = new Image();

        imageSlot1.setSize(60, 60);
        imageSlot2.setSize(60, 60);
        imageSlot3.setSize(60, 60);

        imageSlot1.setPosition(20, 70);
        imageSlot2.setPosition(120, 70);
        imageSlot3.setPosition(220, 70);

        priceSlot1.setPosition(20, 70);
        priceSlot2.setPosition(120, 70);
        priceSlot3.setPosition(220, 70);

        infoPopup.addActor(infoPopupImage);
        infoPopup.addActor(infoName);
        infoPopup.addActor(infoDescription);

        infoPopup.setVisible(false);

        imageSlot1.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1 && item1 != null) {
                    infoName.setText(item1.getName());
                    infoDescription.setText(item1.getDescription());
                    infoPopup.setPosition(imageSlot1.getX() + 20, imageSlot1.getY() - 60);
                    infoPopup.setVisible(true);
                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    infoPopup.setVisible(false);
                }
            }
        });

        // SLOT 2
        imageSlot2.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1 && item2 != null) {
                    infoName.setText(item2.getName());
                    infoDescription.setText(item2.getDescription());
                    infoPopup.setPosition(imageSlot2.getX() + 20, imageSlot2.getY() - 60);
                    infoPopup.setVisible(true);
                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    infoPopup.setVisible(false);
                }
            }
        });

        // SLOT 3
        imageSlot3.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1 && item3 != null) {
                    infoName.setText(item3.getName());
                    infoDescription.setText(item3.getDescription());
                    infoPopup.setPosition(imageSlot3.getX() - 10, imageSlot3.getY() - 60);
                    infoPopup.setVisible(true);
                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    infoPopup.setVisible(false);
                }
            }
        });

        this.addActor(imageSlot1);
        this.addActor(imageSlot2);
        this.addActor(imageSlot3);

        this.addActor(priceSlot1);
        this.addActor(priceSlot2);
        this.addActor(priceSlot3);

        this.addActor(infoPopup);

        this.setVisible(false);
    }

    public void refreshShop(GameMechanic gameMechanic) {
        List<ShopItems> catalogue = Arrays.asList(ShopItems.values());
        Collections.shuffle(catalogue);

        item1 = catalogue.get(0);
        item2 = catalogue.get(1);
        item3 = catalogue.get(2);

        Texture tex1 = new Texture(Gdx.files.internal(item1.getImagePath()));
        Texture tex2 = new Texture(Gdx.files.internal(item2.getImagePath()));
        Texture tex3 = new Texture(Gdx.files.internal(item3.getImagePath()));

        imageSlot1.setDrawable(new TextureRegionDrawable(new TextureRegion(tex1)));
        imageSlot2.setDrawable(new TextureRegionDrawable(new TextureRegion(tex2)));
        imageSlot3.setDrawable(new TextureRegionDrawable(new TextureRegion(tex3)));

        priceSlot1.setText(item1.getPrice());
        priceSlot2.setText(item2.getPrice());
        priceSlot3.setText(item3.getPrice());

        imageSlot1.setVisible(true);
        imageSlot2.setVisible(true);
        imageSlot3.setVisible(true);

        priceSlot1.setVisible(true);
        priceSlot2.setVisible(true);
        priceSlot3.setVisible(true);

        buyButton1.setVisible(true);
        buyButton2.setVisible(true);
        buyButton3.setVisible(true);

        showSold.setText(String.valueOf(gameMechanic.getPlayerSold()));
    }
}
