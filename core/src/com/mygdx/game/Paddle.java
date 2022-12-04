package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Paddle implements Drawable{
    final OrthographicCamera camera;
    final Sprite sprite;

    public Paddle(OrthographicCamera camera) {
        this.camera = camera;
        sprite = new Sprite(new Texture(Gdx.files.internal("paddle.png")));
        sprite.setOriginCenter();
    }

    public void update() {
        move();
    }

    public void move() {
        // check if paddle is within screen
        if (sprite.getY() < 0) {
            sprite.setY(0);
        }
        if (sprite.getY() > camera.viewportHeight - sprite.getHeight()) {
            sprite.setY(camera.viewportHeight - sprite.getHeight());
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getBoundingBox() {
        return sprite.getBoundingRectangle();
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
