package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.input;


public class Testing extends com.badlogic.gdx.Game {
    SpriteBatch batch;
    OrthographicCamera camera;
    ArrayList<Drawable> drawList;
    Paddle right;
    Paddle left;
    AIPaddle ai;
    Paddle endless;
    Ball ball;


    public void create() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        resize((int) camera.viewportWidth, (int) camera.viewportHeight);

        right = new RightPaddle(camera);
        left = new LeftPaddle(camera);
        ai = new AIPaddle(camera);
        endless = new EndlessPaddle(camera);
        ball = new Ball(camera, left, right);
        ai.setBall(ball);

        drawList = new ArrayList<>();
    }

    public void render() {
        super.render();
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (Drawable E : drawList) {
            E.draw(batch);
        }
        batch.end();

        if (input.isKeyPressed(Input.Keys.NUM_1)) {
            drawList.clear();
            addToBatch(right);
            addToBatch(left);
        } else if (input.isKeyPressed(Input.Keys.NUM_2)) {
            drawList.clear();
            addToBatch(ai);
        } else if (input.isKeyPressed(Input.Keys.NUM_3)) {
            drawList.clear();
            addToBatch(endless);
        } else if (input.isKeyPressed(Input.Keys.NUM_4)) {
            drawList.clear();
            addToBatch(ball);
        }
    }

    public void dispose() {
        batch.dispose();
    }

    public <T extends Drawable> void addToBatch(T obj) {
        try {
            drawList.add(obj);
        } catch (Exception ignored) {
        }
    }
}