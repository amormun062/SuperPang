package com.politecnicomalaga.pang.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.politecnicomalaga.pang.managers.MusicManager;
import com.politecnicomalaga.pang.managers.ScreensManager;
import com.politecnicomalaga.pang.managers.SettingsManager;
import com.politecnicomalaga.pang.managers.SoundsManager;
import com.politecnicomalaga.pang.model.Bola;
import com.politecnicomalaga.pang.model.Disparo;
import com.politecnicomalaga.pang.model.Jugador;

public class GameScreen implements Screen {
    private Game game;
    private Stage stage;

    private SpriteBatch batch;
    private Texture fondoEscenario;

    private Music music;
    private boolean isVictoria;
    private boolean isMuerto;
    private boolean hayDisparo;

    //OBJETOS
    private Jugador jugador;
    private Array<Disparo> auxDisparos;
    private Array<Bola> auxBolas;
    private float tiempoDisparo;
    private Sound disparoSound;


    public GameScreen(Game game) {
        batch = new SpriteBatch();
        fondoEscenario = new Texture(Gdx.files.internal("screens/fondo.jpg"));
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.act();
        tiempoDisparo = 0;
        hayDisparo = true;

        //Activamos la musica de la partida
        music = MusicManager.getSingleton().getMusic(MusicManager.TypeMusic.JUGANDO1);
        music.setLooping(true);
        music.play();

        //OBJETOS
        jugador = new Jugador(stage);
        stage.addActor(jugador);

        auxDisparos = new Array<>();
        auxBolas = new Array<>();

        Bola bola;
        bola = new Bola(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2f, stage, SettingsManager.BOLA_RADIO, Bola.DireccionBola.DERECHA);
        auxBolas.add(bola);
        stage.addActor(bola);

        Disparo disparo;
        disparo = new Disparo(jugador);
        auxDisparos.add(disparo);
        stage.addActor(disparo);
        isMuerto = false;
    }


    @Override
    public void show() {
        Gdx.app.log("GameScreen","show");
    }


    //Renderiza el escenario
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        tiempoDisparo += delta;

        //Pintamos el fondo de pantalla
        batch.begin();
            batch.draw(fondoEscenario,0,0);
        batch.end();
        //Pintamos los actores
        stage.draw();

        //Disparo automatico
        if (tiempoDisparo > SettingsManager.TIEMPO_DISPARO && hayDisparo){
            Disparo nuevoDisparo = new Disparo(jugador);
            auxDisparos.add(nuevoDisparo);
            stage.addActor(nuevoDisparo);
            tiempoDisparo = 0;
        }
        //Comprobamos si ha habido alguna colision, aplicando las consecuencias correspondientes
        comprobarColiciones();
    }


    //METODO encargado de comprobar las colisiones
    public void comprobarColiciones(){
        //Revisamos por cada BOLA del Array
        for (Bola bola : auxBolas){
            //En caso de que una BOLA colisione con el JUGADOR y el NO se haya producido una muerte anterior
            if (Intersector.overlaps(bola.body, jugador.body) && !isMuerto){
                muerteJugador();
            }

            //Revisamos por cada DISPARO partiendo de los datos de la BOLA correspondiente
            for (Disparo disparo : auxDisparos){
                //En caso de que un DISPARO colisione con una BOLA
                if (Intersector.overlaps(bola.body, disparo.body)){
                    destruccionBola(bola,disparo);
                }

                //CONTROLAMOS que se ELIMINE el disparo al salirse de la pantalla
                if (disparo.getY() > SettingsManager.SCREEN_HEIGHT){
                    auxDisparos.removeValue(disparo,true);
                    stage.getActors().removeValue(disparo, true);
                }

                //En caso de que ya no haya mas BOLAS en el la SCREEN
                if (auxBolas.size == 0 && !isVictoria){
                    partidaGanada();
                }
            }
        }

    }


    //METODO encargado de aplicar las consecuencias de que una BOLA colisione con el JUGADOR
    private void muerteJugador(){
        isMuerto = true;
        music.stop();
        final Sound sonidoMuerte;
        sonidoMuerte = SoundsManager.getSingleton().getSound(SoundsManager.TypeSound.MUERTE);
        sonidoMuerte.play();
        jugador.remove();
        hayDisparo = false;

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                sonidoMuerte.dispose();
                game.setScreen(ScreensManager.getSingleton().getScreen(game, ScreensManager.TypeScreen.SPLASH_SCREEN));
                dispose();
            }
        }, SettingsManager.TIEMPO_ESPERA_MUERTE);
    }


    //METODO encargado de aplicar las consecuencias de que un DISPARO colisione con una BOLA
    public void destruccionBola(Bola bola, Disparo disparo){
        auxDisparos.removeValue(disparo,true);
        stage.getActors().removeValue(disparo, true);

        //Sonido
        disparoSound = SoundsManager.getSingleton().getSound(SoundsManager.TypeSound.EXPLOTAR);
        disparoSound.play();

        //Instanciamos 2 NUEVAS BOLAS
        Bola nuevaBola;
        float nuevoRadio = bola.getRadio()/2f;

        if (nuevoRadio > 15){
            nuevaBola = new Bola(bola.getX(), bola.getY(), stage, (short) (nuevoRadio), Bola.DireccionBola.IZQUIERDA);
            auxBolas.add(nuevaBola);
            stage.addActor(nuevaBola);

            nuevaBola = new Bola(bola.getX(), bola.getY(), stage,(short) (nuevoRadio), Bola.DireccionBola.DERECHA);
            auxBolas.add(nuevaBola);
            stage.addActor(nuevaBola);
        }
        //Eliminamos la BOLA ORIGEN
        auxBolas.removeValue(bola,true);
        stage.getActors().removeValue(bola,true);
    }


    //METODO encargado de aplicar las consecuencias de que ya no haya ninguna BOLA en el SCREEN
    public void partidaGanada(){
        isVictoria = true;
        music.stop();
        final Sound sonidoVictoria;
        sonidoVictoria = SoundsManager.getSingleton().getSound(SoundsManager.TypeSound.VICTORIA);
        sonidoVictoria.play();

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                sonidoVictoria.dispose();
                game.setScreen(ScreensManager.getSingleton().getScreen(game, ScreensManager.TypeScreen.SPLASH_SCREEN));
                dispose();
            }
        }, SettingsManager.TIEMPO_ESPERA_VICTORIA);
    }


    //Ajusta el tamaño del escenario
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
        batch.dispose();
        fondoEscenario.dispose();
        stage.dispose();
        music.dispose();
    }

}
