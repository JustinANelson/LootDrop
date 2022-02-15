package com.forgottenartsstudios.complicatedsimple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.forgottenartsstudios.database.entities.Stat;

public class GameOverScreen implements Screen {

    final SimpleGame game;
    OrthographicCamera camera;
    Viewport viewport;
    public static String buttonText;
    ShapeRenderer shapeRenderer;
    TextButton button;

    private Stage stage;
    private Skin skin;



    public GameOverScreen(final SimpleGame game) {
        this.game = game;

        stage = new Stage(new FitViewport(480, 854));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        //viewport = new StretchViewport(480,854,camera);
        //viewport.apply();

        buttonText = "Tap here to try again";

        Window window = new Window("Game Over!!", skin, "border");
        window.defaults().pad(8f);
        window.add("Click the button to try again.").row();

        button = new TextButton("Click me!", skin);
        button.pad(8f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        window.add(button);
        window.pack();
        window.setPosition(stage.getWidth() / 2f - window.getWidth() / 2f,
                stage.getHeight() / 2f - window.getHeight() / 2f);
        window.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f)));
        stage.addActor(window);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        game.batch.begin();

        game.font.draw(game.batch, "[RED]Game Over[]", 100, 600);
        game.font.draw(game.batch, "Your score was " + SimpleGame.playerDrops, 100, 550);
        game.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

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

    }
}
