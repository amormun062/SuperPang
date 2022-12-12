package com.politecnicomalaga.pang.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {

    public enum TypeMusic{INTRO, JUGANDO1};

    private static MusicManager singletonMusic;

    //Asegura que solo haya una instancia de la clase
    public static MusicManager getSingleton() {
        if (singletonMusic == null) {
            singletonMusic = new MusicManager();
        }
        return singletonMusic;
    }

    public Music getMusic(TypeMusic typeMusic){
        switch (typeMusic){
            case INTRO:
                return Gdx.audio.newMusic(Gdx.files.internal("sounds/intro_pang.wav"));

            case JUGANDO1:
                return Gdx.audio.newMusic(Gdx.files.internal("sounds/jugando1_pang.wav"));
            default:
                return null;
        }
    }

}
