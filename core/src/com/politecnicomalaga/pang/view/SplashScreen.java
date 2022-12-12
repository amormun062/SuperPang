package com.politecnicomalaga.pang.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.politecnicomalaga.pang.managers.MusicManager;
import com.politecnicomalaga.pang.managers.ScreensManager;
import com.politecnicomalaga.pang.managers.SettingsManager;

public class SplashScreen implements Screen {
    private Game game;
    private Stage stage;

    private SpriteBatch batch;
    private Texture fondoEscenario;

    private Music music;
    //Boton
    private Skin skinButton;

    //Constructor
    public SplashScreen(final Game game){
        batch = new SpriteBatch();
        fondoEscenario = new Texture(Gdx.files.internal("screens/pantalla_inicial.png"));
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.act();
        batch = new SpriteBatch();

        //Creamos un boton que al pulsarlo nos lleve al GameScreen
        skinButton = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        TextButton startButton = new TextButton("Start Game",skinButton);
        startButton.setPosition(Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/4f);   //Posicionamos el actor
        stage.addActor(startButton);

        //Activamos la musica de la partida
        music = MusicManager.getSingleton().getMusic(MusicManager.TypeMusic.INTRO);
        music.setLooping(true);
        music.play();

        Gdx.input.setInputProcessor(stage);

        //EVENTO del boton "startButton"
        startButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //Cuando el botón se suelta
                game.setScreen(ScreensManager.getSingleton().getScreen(game, ScreensManager.TypeScreen.GAME_SCREEN));
                dispose();
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Cuando el botón se pulsa
                return true;
            }
        });
    }


    @Override
    public void show() {
        Gdx.app.log("SplashScreen","show");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        //Pintamos el fondo de pantalla
        batch.begin();
        batch.draw(fondoEscenario,0,0, SettingsManager.SCREEN_WIDTH,SettingsManager.SCREEN_HEIGHT);
        batch.end();
        //Pintamos los actores
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        fondoEscenario.dispose();
        stage.dispose();
        music.dispose();
    }
}
