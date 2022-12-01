package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class EndlessPaddle extends Paddle {
    Ball ball;
    Vector2 trajectory;
    Vector2 position;
    public EndlessPaddle(OrthographicCamera camera) {
        super(camera);
        sprite.setPosition((camera.viewportWidth - camera.viewportWidth/10) - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void move() {
        if (!(ball.velocity.x < 0)) {
            trajectory = ball.velocity;
            position = new Vector2(ball.sprite.getX(), ball.sprite.getY());

            while (position.x < sprite.getX()) {
                position.add(trajectory);
            }

            if (sprite.getY()+20 < position.y) {
                // move up
                sprite.setY(sprite.getY() + 300 * Gdx.graphics.getDeltaTime());
            }
            if (sprite.getY()+20 > position.y) {
                // move down
                sprite.setY(sprite.getY() - 300 * Gdx.graphics.getDeltaTime());
            }
        }
        super.move();
    }

    public void reset() {
        sprite.setPosition((camera.viewportWidth - camera.viewportWidth/10) - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
    }
}
