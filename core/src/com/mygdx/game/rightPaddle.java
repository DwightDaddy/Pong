package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class rightPaddle extends Paddle implements Controllable{
    public rightPaddle(OrthographicCamera camera) {
        super(camera);
        sprite.setPosition((camera.viewportWidth - camera.viewportWidth/10) - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
    }

    @Override
    public void move() {
        // move down
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) sprite.setY(sprite.getY() - 300 * Gdx.graphics.getDeltaTime());
        // move up
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) sprite.setY(sprite.getY() + 300 * Gdx.graphics.getDeltaTime());

        super.move();
    }

    public void reset() {
        sprite.setPosition((camera.viewportWidth - camera.viewportWidth/10) - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
    }
}
