package com.forgottenartsstudios.complicatedsimple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Collections;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class GameScreen implements Screen {
    final SimpleGame game;

    private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;

    private OrthographicCamera camera;
    Viewport viewport;

    private Rectangle bucket;
    private int dropsGathered;


    private static Vector3 touchPos = new Vector3();

    private static Vector2[] drops;
    private long lastSpawnTime;
    private static long LastUpdateTime_SpawnRaindrop;
    private static final long UpdateTime_Raindrop = 500;
    private static Random rand;
    private static int maxRainDrops = 50;
    private static int dropSpeed;



    public GameScreen(final SimpleGame game) {
        this.game = game;
        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        int width = 480;
        int height = 800;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        viewport = new StretchViewport(480,854,camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        bucket = new Rectangle();
        bucket.x = 480 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        //raindrops = new Array<>();
        //spawnRaindrop();

        drops = new Vector2[500 + 1];
        for (int x = 0; x < drops.length; x++){
            drops[x] = new Vector2();
            drops[x].x = -64;
            drops[x].y = -64;
        }

        rand = new Random();
        dropSpeed = 200;
        dropsGathered = SimpleGame.playerDrops;


    }
    @Override
    public void show() {
        Collections.allocateIterators = false;
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }
    @Override
    public void render(float delta) {

        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, "Drops collected: " + dropsGathered, 0, 854);
        game.font.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, 814);
        game.font.draw(game.batch, "" + drops.length, 0, 794);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);

        for (Vector2 drop : drops) {
            if (dropSpeed > 500) game.batch.setColor(Color.YELLOW);
            if (dropSpeed > 800) game.batch.setColor(Color.ORANGE);
            if (dropSpeed > 1200) game.batch.setColor(Color.RED);
            game.batch.draw(dropImage, drop.x, drop.y);
            if (drop.y < 0 && drop.y > -63) {
                SimpleGame.playerDrops = dropsGathered;
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }
        game.batch.setColor(Color.WHITE);
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
            //bucket.y = touchPos.y;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= 1200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += 1200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 480 - 64)
            bucket.x = 480 - 64;

        // check if we need to create a new raindrop
        //if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            //spawnRaindrop();


        if (LastUpdateTime_SpawnRaindrop < System.currentTimeMillis()) {
            for (int i = 1; i <= maxRainDrops; i++) {
                if (drops[i].y < 0) {
                    drops[i].x = rand.nextInt(480 - 64);
                    drops[i].y = 864;
                    break;
                }
            }
            dropSpeed += 20;
            LastUpdateTime_SpawnRaindrop = System.currentTimeMillis() + UpdateTime_Raindrop;
        }
        for (int x = 0; x < drops.length; x++) {
            //moves down only drops on screen
            if (drops[x].y + 64 >= 0) {
                drops[x].y -= dropSpeed * Gdx.graphics.getDeltaTime();
            }
            if (drops[x].y + 64 < 0){
                drops[x].y = -64;
            }
            //checks collision
            if (drops[x].x + dropImage.getWidth() >= bucket.x && drops[x].x < bucket.x + bucketImage.getWidth()) {
                if (drops[x].y >= bucket.y && drops[x].y < bucket.y + bucketImage.getHeight()) {
                    //adds 1
                    dropsGathered++;
                    //plays sound
                    dropSound.play();
                    //removes drop
                    drops[x].y = -64;
                }
            }
        }

    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

    }
    @Override
    public void pause() {
        SimpleGame.playerDrops += dropsGathered;
        System.out.println("game screen pause");

    }
    @Override
    public void resume() {
        System.out.println("game screen resume");

    }
    @Override
    public void hide() {
        rainMusic.stop();
    }
    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
