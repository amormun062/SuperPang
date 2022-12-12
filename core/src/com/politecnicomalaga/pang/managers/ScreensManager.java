package com.politecnicomalaga.pang.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.politecnicomalaga.pang.view.GameScreen;
import com.politecnicomalaga.pang.view.SplashScreen;

public class ScreensManager {
    public enum TypeScreen{SPLASH_SCREEN,GAME_SCREEN};

    private static ScreensManager singletonScreen;

    //Constructor
    private ScreensManager(){}

    //Asegura que solo haya una instancia de la clase
    public static ScreensManager getSingleton() {
        if (singletonScreen == null) {
            singletonScreen = new ScreensManager();
        }
        return singletonScreen;
    }

    //Metodo encargado de devolver la instancia del screen pedido por parametro
    public Screen getScreen(Game game, TypeScreen typeScreen){
        Screen screen;

        switch (typeScreen){
            case SPLASH_SCREEN:
                screen = new SplashScreen(game);
                break;
            case GAME_SCREEN:
                screen = new GameScreen(game);
                break;
            default:
                screen = null;
                break;
        }
        return screen;
    }

}
