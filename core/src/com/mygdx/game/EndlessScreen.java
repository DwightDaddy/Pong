package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;


import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class EndlessScreen implements Screen {
    final Pong game;
    final OrthographicCamera camera;
    String username = "";
    leftPaddle leftPaddle;
    EndlessPaddle rightPaddle;
    Ball ball;
    Integer score = 0;
    PrintWriter writer;
    FileWriter file;
    Set<Map.Entry<String, String>> scores;
    Music music;

    public EndlessScreen(final Pong game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;

        // create paddles
        leftPaddle = new leftPaddle(camera);

        rightPaddle = new EndlessPaddle(camera);

        ball = new Ball(camera, leftPaddle, rightPaddle);
        rightPaddle.setBall(ball);

        music = Gdx.audio.newMusic(Gdx.files.internal("music/endlessMusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.25f);

        try {
            scores = new ScoreboardScreen(game, camera).scores.entrySet();
            file = new FileWriter(Gdx.files.internal("assets/scores/scores.txt").file());
            writer = new PrintWriter(file, true);
        } catch (IOException e) {
            System.err.println("Could not find scores.txt file.");
        }

        music.play();
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

        // update player
        leftPaddle.update();
        rightPaddle.update();

        // begin batch
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        leftPaddle.draw(game.batch);
        rightPaddle.draw(game.batch);
        ball.draw(game.batch);
        game.font.draw(game.batch, score.toString(),
                camera.viewportWidth/2 - 50, camera.viewportHeight - 50);
        game.batch.end();

        if (ball.sprite.getX() < camera.viewportWidth/2 && ball.collide()) {
            score++;
        }

        // if game ends or Escape key is pressed update scores and go to main menu
        if (ball.sprite.getX() < 0 || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            int count = 0;
            boolean newHighScore = false;

            if (this.score != 0) {
                if (!scores.isEmpty()) {
                    // read through scores and compare user's score to them
                    for (Map.Entry<String, String> entry : scores) {
                        if (count > 10) {
                            break;
                        } else if (this.score > Integer.parseInt(entry.getValue())) {
                            // text input listener for username input
                            ScreenUtils.clear(0, 0, 0.2f, 1);
                            while (username.equals("")) {
                                username = JOptionPane.showInputDialog(null, "Please enter your name.");
                            }

                            newHighScore = true;
                            break;
                        } else {
                            count++;
                        }
                    }
                } else {
                    // text input listener for username input
                    ScreenUtils.clear(0, 0, 0.2f, 1);
                    while (username.equals("")) {
                        username = JOptionPane.showInputDialog(null, "Please enter your name.");
                    }

                    writer.println(this.username + "," + this.score);
                }

                if (!newHighScore) {
                    count++;
                }

                int n = 0;
                // write to scores.txt the new scores
                for (Map.Entry<String, String> entry : scores) {
                    if (n > 10) {
                        break;
                    } else if (n == count) {
                        writer.println(this.username + "," + this.score);
                    }
                    writer.println(entry.getKey() + "," + entry.getValue());
                    n++;
                }


            } else {
                // write to scores.txt the new scores
                int n = 0;
                for (Map.Entry<String, String> entry : scores) {
                    if (n > 10) {
                        break;
                    }
                    writer.println(entry.getKey() + "," + entry.getValue());
                    n++;
                }
            }


            game.changeScreen(Pong.MENU);
            dispose();
        }

        // update ball
        ball.update();
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
    public void dispose () {
        music.dispose();
    }
}
