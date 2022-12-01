package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class AIPaddle extends Paddle {
    float timeSinceLastCalc;
    Ball ball;
    Vector2 trajectory;
    Vector2 position;
    public AIPaddle(OrthographicCamera camera) {
        super(camera);
        sprite.setPosition((camera.viewportWidth - camera.viewportWidth/10) - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
        timeSinceLastCalc = 1f/60f;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
        trajectory = new Vector2(ball.velocity);
        position = new Vector2(ball.sprite.getX(), ball.sprite.getY());
    }

    @Override
    public void move() {
        if (!(ball.velocity.x < 0)) {
            if (MathUtils.random() > .8f && timeSinceLastCalc > 1f/60f) {
                trajectory = new Vector2(ball.velocity);
                position = new Vector2(ball.sprite.getX(), ball.sprite.getY());
                trajectory.y = MathUtils.random(trajectory.y + MathUtils.random(10));
                while (position.x < sprite.getX()) {
                    position.add(trajectory);
                }
                timeSinceLastCalc = Gdx.graphics.getDeltaTime();
            } else {
                timeSinceLastCalc += Gdx.graphics.getDeltaTime();;
            }
            if (sprite.getY() + 64 < position.y) {
                // move up
                sprite.setY(sprite.getY() + 300 * Gdx.graphics.getDeltaTime());
            }
            if (sprite.getY() + 64 > position.y) {
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
