package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class AIScreen implements Screen {
    Pong game;
    OrthographicCamera camera;
    leftPaddle leftPaddle;
    AIPaddle rightPaddle;
    Ball ball;
    Vector2 score = new Vector2(0, 0);
    Music music;
    public AIScreen(final Pong game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        // create paddles
        leftPaddle = new leftPaddle(camera);

        rightPaddle = new AIPaddle(camera);

        // create ball
        ball = new Ball(camera, leftPaddle, rightPaddle);
        rightPaddle.setBall(ball);

        // create music
        music = Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.25f);
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

        // update ball
        ball.update();

        // begin batch
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        leftPaddle.draw(game.batch);
        rightPaddle.draw(game.batch);
        ball.draw(game.batch);
        game.font.draw(game.batch, (int)score.x + " | " + (int)score.y,
                camera.viewportWidth/2 - 50, camera.viewportHeight - 50);
        game.batch.end();

        if (ball.sprite.getX() < 0) {
            score.y++;
            reset();
        }
        if (ball.sprite.getX() > camera.viewportWidth) {
            score.x++;
            reset();
        }

        if (score.x >= 5 || score.y >= 5 || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.changeScreen(Pong.MENU);
            dispose();
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
    public void dispose () {
        music.dispose();
    }

    public void reset() {
        leftPaddle.reset();
        rightPaddle.reset();
        ball.reset();
    }
}
