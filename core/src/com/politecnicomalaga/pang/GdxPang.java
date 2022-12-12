package com.politecnicomalaga.pang;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.politecnicomalaga.pang.managers.ScreensManager;

public class GdxPang extends Game {
	OrthographicCamera camera;
	SpriteBatch batch;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		//Establecemos el primer screen, el cual sera SPLASH_SCREEN

		this.setScreen(ScreensManager.getSingleton().getScreen(this, ScreensManager.TypeScreen.SPLASH_SCREEN));
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
			getScreen().render(Gdx.graphics.getDeltaTime());
		batch.end();
		//System.out.println(Gdx.graphics.getFramesPerSecond());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
