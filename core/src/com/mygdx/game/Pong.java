package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class Pong extends com.badlogic.gdx.Game {
    private static MainMenuScreen menuScreen;
    private static PVPScreen pvpScreen;
    private static EndlessScreen endlessScreen;
    private static ScoreboardScreen scoreScreen;
    private static AIScreen aiScreen;
    public final static int MENU = 0;
    public final static int PVP = 1;
    public final static int ENDLESS = 2;
    public final static int SCORE = 3;
    public final static int AI = 4;

    SpriteBatch batch;
    BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontParameter parameter;
    OrthographicCamera camera;

    public void create() {
        batch = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Pacifico.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        resize((int) camera.viewportWidth, (int) camera.viewportHeight);

        this.setScreen(new MainMenuScreen(this, camera));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        generator.dispose();
        font.dispose();
    }


    public void changeScreen(int screen) {
        switch(screen){
            case MENU:
                if(menuScreen == null) menuScreen = new MainMenuScreen(this, camera);
                this.setScreen(menuScreen);
                break;
            case PVP:
                if(pvpScreen == null) pvpScreen = new PVPScreen(this, camera);
                this.setScreen(pvpScreen);
                break;
            case ENDLESS:
                if(endlessScreen == null) endlessScreen = new EndlessScreen(this, camera);
                this.setScreen(endlessScreen);
                break;
            case SCORE:
                if(scoreScreen == null) scoreScreen = new ScoreboardScreen(this, camera);
                this.setScreen(scoreScreen);
                break;
            case AI:
                if(aiScreen == null) aiScreen = new AIScreen(this, camera);
                this.setScreen(aiScreen);
                break;
        }
    }
}
