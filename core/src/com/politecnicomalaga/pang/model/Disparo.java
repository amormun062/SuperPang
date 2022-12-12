package com.politecnicomalaga.pang.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.politecnicomalaga.pang.managers.AssetsManager;
import com.politecnicomalaga.pang.managers.SettingsManager;

public class Disparo extends Actor {
    //Atlas
    private TextureAtlas atlas;
    //Animacion
    private Animation<TextureRegion> disparoAnimacion;

    private int stateTime;

    //Area de colicion
    public Rectangle body;

    public Disparo(Jugador jugador) {
        super();
        //Ubicamos el disparo
        setX(jugador.getX());
        setY(jugador.getY());

        //Cargamos la imagen
        atlas = new TextureAtlas(AssetsManager.ATLAS_FILE);
        disparoAnimacion = new Animation<TextureRegion>(5f,atlas.findRegions("disparo"));
        stateTime = 0;

        //Area de colicion
        body = new Rectangle(getX(),getY(),SettingsManager.DISPARO_WIDTH,SettingsManager.DISPARO_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += parentAlpha;

        TextureRegion currentFrame = disparoAnimacion.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, this.getX(), this.getY(), SettingsManager.DISPARO_WIDTH,SettingsManager.DISPARO_HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Se mueve verticalmente
        this.setY(this.getY()+SettingsManager.VELOCIDAD_DISPARO);

        //Actualizamos la posicion del area de colicion
        body.setX(getX());
        body.setY(getY());
    }

    public void dispose() {
        atlas.dispose();
    }
}
