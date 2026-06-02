package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Symbole {

    private Texture orange;
    private Texture apple;
    private Texture seven;

    private void creerSymbole() {

        orange = new Texture(Gdx.files.internal("orange.png"));
        apple = new Texture(Gdx.files.internal("apple.png"));
        seven = new Texture(Gdx.files.internal("Seven.png"));

        TextureRegionDrawable dessinOrange = new TextureRegionDrawable(orange);
        TextureRegionDrawable dessinApple = new TextureRegionDrawable(apple);
        TextureRegionDrawable dessinSeven = new TextureRegionDrawable(seven);
    }
}
