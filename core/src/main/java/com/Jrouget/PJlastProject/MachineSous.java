package com.Jrouget.PJlastProject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MachineSous extends Group {

    private Texture levierTexture;
    private Texture machineTexture;

    public MachineSous (final GameMechanic gameMechanic) {
        this.setSize(300, 150);

        levierTexture = new Texture(Gdx.files.internal("levier.png"));
        machineTexture = new Texture(Gdx.files.internal("gamblingMachine.png"));

        TextureRegionDrawable dessinLevier = new TextureRegionDrawable(levierTexture);
        TextureRegionDrawable dessinMachine = new TextureRegionDrawable(machineTexture);

        ImageButton boutonLevier = new ImageButton(dessinLevier);
        Image machineImage = new Image(dessinMachine);

        boutonLevier.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gameMechanic.tirage();
            }
        });

        machineImage.setPosition(150, 0);
        boutonLevier.setPosition(275, 50);

        this.addActor(machineImage);
        this.addActor(boutonLevier);
    }

    public void dispose() {
        if (machineTexture != null) machineTexture.dispose();
        if (levierTexture != null) levierTexture.dispose();
    }
}
