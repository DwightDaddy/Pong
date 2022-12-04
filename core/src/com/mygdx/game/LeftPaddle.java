package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class LeftPaddle extends Paddle implements Drawable {
    public LeftPaddle(OrthographicCamera camera) {
        super(camera);
        sprite.setPosition(camera.viewportWidth/10 - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
    }

    @Override
    public void move() {
        // move down
        if (Gdx.input.isKeyPressed(Input.Keys.S)) sprite.setY(sprite.getY() - 300 * Gdx.graphics.getDeltaTime());
        // move up
        if (Gdx.input.isKeyPressed(Input.Keys.W)) sprite.setY(sprite.getY() + 300 * Gdx.graphics.getDeltaTime());

        super.move();
    }

    public void reset() {
        sprite.setPosition(camera.viewportWidth/10 - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
    }
}