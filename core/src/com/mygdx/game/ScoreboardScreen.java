package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ScoreboardScreen implements Screen {
    final Pong game;
    OrthographicCamera camera;
    Scanner scnr;
    LinkedHashMap<String, String> scores;

    public ScoreboardScreen(final Pong game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;

        scores = new LinkedHashMap<>();

        try {
            scnr = new Scanner(Gdx.files.internal("assets/scores/scores.txt").file());
            scnr.skip("");
            int count = 0;
            while (count < 10 && scnr.hasNext()) {
                String line = scnr.nextLine();
                String[] arr = line.split(",");
                try {
                    if (!scores.containsKey(arr[0])) {
                        scores.put(arr[0], arr[1]);
                    }
                } catch (NullPointerException e) {
                    System.err.println("Not enough data in scores.txt file.");
                }
                count++;
                scnr.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not find scores.txt file.");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // set background color
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // update camera
        camera.update();

        // begin batch
        game.batch.begin();
        if (scores != null) {
            int n = 9;
            for (String name : scores.keySet()) {
                game.font.draw(game.batch, name + ": " + scores.get(name),
                        camera.viewportWidth / 2 -50, camera.viewportHeight / 10 * n -50);
                n--;
            }
        }
        game.batch.end();

        // close scoreboard when Escape key pressed
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.changeScreen(Pong.MENU);

        }
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
