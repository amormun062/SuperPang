package com.politecnicomalaga.pang.model;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.politecnicomalaga.pang.managers.AssetsManager;
import com.politecnicomalaga.pang.managers.SettingsManager;

public class Bola extends Actor {

    //Atlas
    private TextureAtlas atlas;

    //Atributos
    private TextureRegion circuloTextura;
    private Stage stage;

    private float velX;
    private float velY;
    private final float acelY;
    private final short radio;

    //Area de colicion
    public Circle body;

    //Direccion
    public enum DireccionBola{IZQUIERDA,DERECHA};

    //Constructor
    public Bola(float posX, float posY, Stage stage, short radio, DireccionBola direccion) {
        //Hay que crear las bolas (sólo es necesario instanciar una) donde nos digan
        //pero la acelY es siempre 0.2 y la velY es 0 al principio.
        atlas = new TextureAtlas(AssetsManager.ATLAS_FILE);

        //Establecemos una textura para la bola de manera aleatoria (Puede ser azul, verde, roja)
        switch (random(0,2)){
            case 0:
                circuloTextura = new TextureRegion(atlas.findRegion(AssetsManager.ATLAS_CIRCULO_AZUL));
                break;
            case 1:
                circuloTextura = new TextureRegion(atlas.findRegion(AssetsManager.ATLAS_CIRCULO_VERDE));
                break;
            case 2:
                circuloTextura = new TextureRegion(atlas.findRegion(AssetsManager.ATLAS_CIRCULO_ROJO));
                break;
        }

        setX(posX);
        setY(posY);
        setSize(radio,radio);
        velY = SettingsManager.BOLA_VELY_INICIAL;
        this.stage = stage;

        switch (direccion){
            case IZQUIERDA:
                velX = -SettingsManager.BOLA_VELX_INICIAL;
                break;
            case DERECHA:
                velX = SettingsManager.BOLA_VELX_INICIAL;
                break;
        }

        acelY = SettingsManager.BOLA_ACELY_INICIAL;    //Nunca varia
        this.radio = radio;

        //Establecemos el area de colicion
        body = new Circle(getX(),getY(),radio);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        //Vamos a necesitar como otributos una velX que es constante, pero + o - y cambia
        //al tocar las paredes.
        if (this.getX()<=0 || this.getX()>=Gdx.graphics.getWidth()-radio){
            velX = -velX;
        }

        //También una velY que empieza siendo 0. Pero que cuando actua el actor, cambia a
        //velY = velY - acelY
        velY = velY - acelY;

        //Hay que cambiar la velocidad si la bola llega al suelo (20 pixeles en Y). La velocidad
        //debe ser velY=radio * 0.10 - 6
        if (this.getY() <= SettingsManager.ALTO_SUELO){
            velY =  SettingsManager.BOLA_RADIO * 0.18f - 6;
        }

        //Actualizamos la posicion de la bola
        setX(getX()+velX);
        setY(getY()+velY);

        //Actualizamos la posicion del area de colicion
        body.setX(getX()+radio);
        body.setY(getY()+radio);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(circuloTextura,getX(),getY(),radio,radio);
        //Aquí dibujamos la texture de la bola con respecto a su radio
    }


    public short getRadio(){
        return radio;
    }

}
