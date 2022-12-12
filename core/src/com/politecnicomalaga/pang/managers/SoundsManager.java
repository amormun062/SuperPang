package com.politecnicomalaga.pang.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundsManager {

    public enum TypeSound{EXPLOTAR, VICTORIA, MUERTE};

    private static SoundsManager singletonSound;

    //Asegura que solo haya una instancia de la clase
    public static SoundsManager getSingleton() {
        if (singletonSound == null) {
            singletonSound = new SoundsManager();
        }
        return singletonSound;
    }

    public Sound getSound(TypeSound typeSound){
        switch (typeSound){
            case EXPLOTAR:
                return Gdx.audio.newSound(Gdx.files.internal("sounds/explotar.wav"));

            case VICTORIA:
                return Gdx.audio.newSound(Gdx.files.internal("sounds/ganar_pang.wav"));

            case MUERTE:
                return Gdx.audio.newSound(Gdx.files.internal("sounds/morir_pang.wav"));
            default:
                return null;
        }
    }

}
