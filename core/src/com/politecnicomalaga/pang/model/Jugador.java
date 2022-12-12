package com.politecnicomalaga.pang.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.politecnicomalaga.pang.managers.AssetsManager;
import com.politecnicomalaga.pang.managers.SettingsManager;

public class Jugador extends Actor {

    //Stage
    private Stage stage;
    //Atlas
    private TextureAtlas atlas;

    //Animacion
    private final Animation<TextureRegion> andandoDerAnimacion;
    private final Animation<TextureRegion> andandoIzqAnimacion;

    private boolean direccion;   //True -> Izquierda, False -> Derecha
    private int stateTime;
    private float velX;

    //Area de colicion
    public Rectangle body;

    public Jugador (Stage stage){
        super();
        this.stage = stage;
        setSize(SettingsManager.JUGADOR_WIDTH,SettingsManager.JUGADOR_HEIGHT);
        setX(Gdx.graphics.getWidth()/2f);
        setY(SettingsManager.ALTO_SUELO);
        stateTime = 0;
        direccion = true;
        velX = 0;

        //Cargamos la imagen
        atlas = new TextureAtlas(AssetsManager.ATLAS_FILE);
        andandoDerAnimacion = new Animation<TextureRegion>(5f,atlas.findRegions("andando"));
        andandoIzqAnimacion = new Animation<TextureRegion>(5f,atlas.findRegions("andandoIZQ"));

        //Area de colicion
        body = new Rectangle(getX(),getY(),SettingsManager.JUGADOR_WIDTH,SettingsManager.JUGADOR_HEIGHT);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        stateTime += parentAlpha;

        TextureRegion currentFrame;
        if (direccion){
            currentFrame = andandoIzqAnimacion.getKeyFrame(stateTime, true);
        }else{
            currentFrame = andandoDerAnimacion.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame,getX(),getY(),SettingsManager.JUGADOR_WIDTH,SettingsManager.JUGADOR_HEIGHT);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        //EVENTO. Cambio de posicion del JUGADOR segun la posicion del raton
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.input.getX() < (getX()-SettingsManager.JUGADOR_WIDTH)){
                    direccion = true;
                    velX = -SettingsManager.JUGADOR_VELX;

                }else if (Gdx.input.getX() > (getX()+SettingsManager.JUGADOR_WIDTH)){
                    direccion = false;
                    velX = SettingsManager.JUGADOR_VELX;

                } else if (Gdx.input.getX() > getX()-SettingsManager.JUGADOR_WIDTH && Gdx.input.getX() < getX()+SettingsManager.JUGADOR_WIDTH){
                    velX = 0;

                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        setX(getX()+velX);

        if (getX() <= 0 || getX() >= Gdx.graphics.getWidth()-SettingsManager.JUGADOR_WIDTH){
            velX = 0;
        }

        //Actualizamos la posicion del area de colicion
        body.setX(getX());
        body.setY(getY());
    }


    public void dispose() {
        atlas.dispose();
        this.dispose();
    }

}
