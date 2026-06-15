package com.Jrouget.PJlastProject;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public enum ShopItems {

    PEPIN_FERTILE("Pépins Fertiles", "+15 chances de Pomme", 20, "APPLE", 15, "silverApple.png"),
    SUPER_VERGER("Super Verger", "+30 chances de Pomme", 35, "APPLE", 30, "goldApple.png"),
    ZESTE_CHANCEUX("Zeste Chanceux", "+10 chances d'Orange", 40, "ORANGE", 10, "goldOrange.png"),
    TREFLE_DORE("Trèfle Doré", "+5 chances de Seven", 60, "SEVEN", 5, "goldSeven.png"),
    DE_DIVIN("Dé Divin", "+8 chances de Seven", 90, "SEVEN", 8, "divineSeven.png"),
    TIRAGE("Tirage", "+5 Tirage", 100, "TIRAGE", 5, "ticket.png");

    private String name;
    private String description;
    private Integer price;
    private String effectType;
    private Integer value;
    private String imagePath;

    ShopItems(String name, String description, Integer price, String effectType, Integer value, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.effectType = effectType;
        this.value = value;
        this.imagePath = imagePath;
    }

    public void boost(GameMechanic gameMechanic) {
        if(effectType.equals("APPLE")) {
            gameMechanic.boosterApple(value);
        } else if (effectType.equals("ORANGE")) {
            gameMechanic.boosterOrange(value);
        } else if (effectType.equals("SEVEN")) {
            gameMechanic.boosterSeven(value);
        } else if (effectType.equals("TIRAGE")) {
            gameMechanic.bonusTirage(value);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }
}
