package com.politecnicomalaga.pang.managers;

import com.badlogic.gdx.Gdx;

public class GameManager {
    private float gameTime;
    private static GameManager singletonGameManager;

    public static GameManager getSingleton() {
        if (singletonGameManager == null) {
            singletonGameManager = new GameManager();
        }
        return singletonGameManager;
    }

    public float getGameTime() {
        gameTime += Gdx.graphics.getDeltaTime();
        return gameTime;
    }
}
