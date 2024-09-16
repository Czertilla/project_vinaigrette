package com.czertilla.project_vinaigrette;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Vector2 touchPos;
    private SpriteBatch batch;
    private Sprite bucketSprite;
    private Array<Sprite> dropSprites;
    private Rectangle
        bucketHitbox,
        dropHitbox;
    private FitViewport viewport;
    private Texture
        backgroundTexture,
        bucketTexture,
        dropTexture;

    private Sound dropSound;
    private Music music;
    private long dropCreatedTimestamp = 0;

    @Override
    public void create() {
        touchPos = new Vector2();
        bucketHitbox = new Rectangle();
        dropHitbox = new Rectangle();

        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");

        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1, 1);
        dropSprites = new Array<>();

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
    }

    @Override
    public void resize (int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(backgroundTexture, 140, 210);
        batch.end();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            bucketSprite.translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            bucketSprite.translateX(- speed * delta);
        }
        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY());
            viewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        bucketSprite.setX(MathUtils.clamp(
            bucketSprite.getX(),
            0,
            worldWidth - bucketWidth
        ));

        float fallSpeed = 2f;
        float delta = Gdx.graphics.getDeltaTime();

        bucketHitbox.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);
        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i);
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-fallSpeed * delta);
            dropHitbox.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
            else if (bucketHitbox.overlaps(dropHitbox)) {
                dropSprites.removeIndex(i);
            }

        }

        long timestamp = TimeUtils.millis();
        long dropCreateGapMs = 1000;
        if (timestamp - dropCreatedTimestamp >= dropCreateGapMs) {
            dropCreatedTimestamp = timestamp;
            createDroplet();
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.CLEAR);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        /*add draw implementations only in this scope*/{
            float worldWidth = viewport.getWorldWidth();
            float worldHeight = viewport.getWorldHeight();

            batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
            bucketSprite.draw(batch);
//            dropSprites.forEach(sprite -> {sprite.draw(batch);}); TODO edit min API
            for (Sprite sprite: dropSprites){
                sprite.draw(batch);
            }
        }
        batch.end();
    }

    private void createDroplet(){
        float dropWidth = 1f;
        float dropHeight = 1f;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        bucketTexture.dispose();
        dropTexture.dispose();
        dropSound.dispose();
        music.dispose();
    }
}
