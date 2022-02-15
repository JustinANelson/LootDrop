package com.forgottenartsstudios.complicatedsimple;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    public static int playerDrops;

    public SimpleGame() {
        super();
    }

    @Override
    public void create() {
        System.out.println("simple game create");
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().markupEnabled = true;
        font.getData().setScale(1.5f, 1.5f);
        this.setScreen(new MainMenuScreen(this));

    }
    @Override
    public void render() {
        super.render();
    }
    @Override
    public void pause() {
        //Save info here.

        super.pause();
        this.setScreen(new PauseScreen(this));

    }
    @Override
    public void resume() {
        super.resume();
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
    @Override
    public Screen getScreen() {
        return super.getScreen();
    }
    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
