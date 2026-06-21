package com.Jrouget.PJlastProject.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class combosDisplay extends Group {

    public combosDisplay() {

        Texture textureTripleApple = new Texture(Gdx.files.internal("items/tripleApple.png"));
        Texture textureTripleOrange = new Texture(Gdx.files.internal("items/tripleOrange.png"));
        Texture textureTripleSeven = new Texture(Gdx.files.internal("items/tripleSeven.png"));

        Image tripleApple = new Image(textureTripleApple);
        Image tripleOrange = new Image(textureTripleOrange);
        Image tripleSeven = new Image(textureTripleSeven);

        tripleOrange.setSize(90,20);
        tripleApple.setSize(90,20);
        tripleSeven.setSize(90,20);

        tripleApple.setPosition(60,110);
        tripleOrange.setPosition(60,95);
        tripleSeven.setPosition(60,80);

        this.addActor(tripleOrange);
        this.addActor(tripleApple);
        this.addActor(tripleSeven);
    }
}
