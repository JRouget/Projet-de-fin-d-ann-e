package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CombosAffichage extends Group {

    private Texture textureTripleApple;
    private Texture textureTripleOrange;
    private Texture textureTripleSeven;

    public CombosAffichage() {

        textureTripleApple = new Texture(Gdx.files.internal("tripleApple.png"));
        textureTripleOrange = new Texture(Gdx.files.internal("tripleOrange.png"));
        textureTripleSeven = new Texture(Gdx.files.internal("tripleSeven.png"));

        TextureRegionDrawable dessinTripleApple = new TextureRegionDrawable(textureTripleApple);
        TextureRegionDrawable dessinTripleOrange = new TextureRegionDrawable(textureTripleOrange);
        TextureRegionDrawable dessinTripleSeven = new TextureRegionDrawable(textureTripleSeven);

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
