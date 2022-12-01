package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    final Pong game;
    OrthographicCamera camera;
    Table table;
    Stage stage;
    public MainMenuScreen(final Pong game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        TextButton newPVPGame = new TextButton("Player VS. Player", skin);
        TextButton newEndlessGame = new TextButton("Endless", skin);
        TextButton newAIGame = new TextButton("Player VS. Computer", skin);
        TextButton scoreboard = new TextButton("Scoreboard", skin);
        TextButton exit = new TextButton("Exit", skin);

        // exit button listener
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // PVP game listener
        newPVPGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.changeScreen(Pong.PVP);

            }
        });
        // Endless game listener
        newEndlessGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.changeScreen(Pong.ENDLESS);

            }
        });
        // AI game listener
        newAIGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.changeScreen(Pong.AI);

            }
        });
        // Scoreboard listener
        scoreboard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.changeScreen(Pong.SCORE);

            }
        });


        table.add(newPVPGame).fillX().uniformX();
        table.row().pad(50, 0, 50, 0);
        table.add(newEndlessGame).fillX().uniformX();
        table.row().padBottom(50);
        table.add(newAIGame).fillX().uniformX();
        table.row().padBottom(50);
        table.add(scoreboard).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();
        stage.act(delta);
        stage.draw();
        game.batch.end();
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Pong! ", camera.viewportWidth/3*2, camera.viewportHeight/2);
        game.batch.end();

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
        stage.dispose();
    }
}
