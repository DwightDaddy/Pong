package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball implements Drawable {
    final OrthographicCamera camera;
    final Sprite sprite;
    final Paddle leftPaddle;
    final Paddle rightPaddle;
    Rectangle boundingBox;
    long start;
    Vector2 velocity;
    ParticleEffect effect;
    Sound sound;
    int hits;
    public Ball(OrthographicCamera camera, Paddle left, Paddle right) {
        this.camera = camera;
        sprite = new Sprite(new Texture(Gdx.files.internal("ball.png")));
        sprite.setOriginCenter();
        sprite.setPosition(camera.viewportWidth/2 - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
        boundingBox = sprite.getBoundingRectangle();
        velocity = new Vector2(5, MathUtils.random(-3f, -0.5f));
        leftPaddle = left;
        rightPaddle = right;
        start = System.currentTimeMillis();
        hits = 0;

        effect = new ParticleEffect();
        effect.loadEmitters(Gdx.files.internal("particles/collision.p"));
        effect.loadEmitterImages(Gdx.files.internal("particles"));

        sound = Gdx.audio.newSound(Gdx.files.internal("paddleHit.mp3"));
    }

    public void update() {
        sprite.translate(velocity.x, velocity.y);
        boundingBox = sprite.getBoundingRectangle();

        // check if ball hit paddle
        if (collide()) {
            hits++;
            System.out.println(hits);
            while (collide()) {
                if (velocity.x < 0) {
                    sprite.translateX(1);
                    effect.setPosition(sprite.getX(), sprite.getY() + sprite.getHeight()/2);
                }
                if (velocity.x > 0) {
                    sprite.translateX(-1);
                    effect.setPosition(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
                }
            }
            velocity.scl(-1, 1);
            velocity.y += MathUtils.random(-0.5f, 0.5f);
            effect.start();
            sound.play(.75f);
        }

        // check if ball is within screen
        if (sprite.getY() < 0 || sprite.getY() > camera.viewportHeight - sprite.getHeight()) {
            velocity.scl(1, -1);
        }

        if (System.currentTimeMillis() - start > 5000 && !(velocity.x > 30)) {
            start = System.currentTimeMillis();
            velocity.scl(1.2f, 1);
        } else if (velocity.x > 30) {
            velocity.x = 30;
        }

    }
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        effect.draw(batch, Gdx.graphics.getDeltaTime());
    }

    public void reset() {
        sprite.setPosition(camera.viewportWidth/2 - sprite.getWidth()/2,
                camera.viewportHeight/2 - sprite.getHeight()/2);
        boundingBox = sprite.getBoundingRectangle();

        velocity.x = 0;
        velocity.y = 0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored){
        }

        velocity.x = 5;
        velocity.y = MathUtils.random(-3f, -0.5f);

        effect.reset();
    }

    public boolean collide() {
        boundingBox = sprite.getBoundingRectangle();
        return (boundingBox.overlaps(leftPaddle.getBoundingBox()) ||
                boundingBox.overlaps(rightPaddle.getBoundingBox()));
    }
}
