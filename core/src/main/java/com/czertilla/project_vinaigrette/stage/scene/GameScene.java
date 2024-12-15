package com.czertilla.project_vinaigrette.stage.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
import com.czertilla.project_vinaigrette.stage.scene.actor.EnemyActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.WallActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm.Rifle;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm.Shotgun;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.melee.MeleeWeapon;
import com.czertilla.project_vinaigrette.utils.C;

public class GameScene extends BaseScene {
    private PlayerActor player;
    private Texture actorTexture2;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Texture bulletTexture;
    EnemyActor enemy;
    private OrthographicCamera camera;

    private static GameScene instance;

    public static GameScene getInstance(MainGame screen) {
        if (instance == null) {
            instance = new GameScene(screen);
        } else {
            instance.screen = screen;
            Gdx.input.setInputProcessor(instance.inputHandler);
        }
        return instance;
    }

    private GameScene(MainGame screen) {
        super(screen);

        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("ui/demo_scene.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getViewport().setCamera(camera);

        actorTexture2 = new Texture(Gdx.files.internal("ui/zombie.png"));
        TextureRegion region2 = new TextureRegion(actorTexture2);

        TextureRegion playerRegion = new TextureRegion(actorTexture2);
        player = new PlayerActor(playerRegion);
        player.setPosition(500, 500);
        player.setSize(200, 300);
        addActor(player);
        TextureRegion mele = new TextureRegion(new Texture(Gdx.files.internal("ui/bita.png")));
        MeleeWeapon melle = new MeleeWeapon(mele,this);
        melle.setPosition(500, 500);
        melle.setSize(100, 100);
        addActor(melle);

        bulletTexture = new Texture(Gdx.files.internal("ui/bullet.png"));

        Texture texture = new Texture(Gdx.files.internal("ui/shutgun.png"));
        TextureRegion region = new TextureRegion(texture);
        Rifle shotgun = new Rifle(region, "rifleA");
        shotgun.setPosition(300, 500);
        shotgun.setSize(200, 100);
        addActor(shotgun);

        TextureRegion region3 = new TextureRegion(actorTexture2);
        enemy = new EnemyActor(region3, 100);
        enemy.setPosition(-50, -50);
        enemy.setSize(200, 200);
        addActor(enemy);

        for (int i = 0; i < 250; i++) {
            addActor(new EnemyActor(region3, 100) {{
                setPosition(MathUtils.random(-10000, 0), MathUtils.random(-1000, 10000));
                setSize(200, 200);
            }});
        }

        addActor(new WallActor(-2000, 0, 550, 500));
        addActor(new WallActor(250, -500, 550, 500));
        addActor(new WallActor(-2000, -1000, 550, -500));
        camera.zoom =0.5f;

    }

    private void dragCamera() {
        Vector3 delta = new Vector3(actor.getX() + actor.getWidth() / 2, actor.getY() + actor.getHeight() / 2, 0).sub(camera.position);
        float mlp = delta.len();
        float LIM = C.CAM_DELTA_LIMIT;
        if (mlp < LIM)
            mlp = mlp / LIM;
        else
            mlp = 1f - (LIM / mlp);
        camera.position.mulAdd(delta, (float) Math.sqrt(mlp));

        Vector3 sight = getViewport().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        sight.sub(new Vector3(actor.getX() + actor.getWidth() / 2, actor.getY() +actor.getHeight() / 2, 0));
        camera.position.mulAdd(sight, Math.min(sight.len() / LIM, LIM / sight.len()) / sight.len() * C.CAM_SIGHT_DELTA);

        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);

        float viewportWidth = camera.viewportWidth * camera.zoom;
        float viewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, viewportWidth / 2, mapWidth - viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, viewportHeight / 2, mapHeight - viewportHeight / 2);

        camera.update();
    }

    public void act(float deltaTime) {
        super.act(deltaTime);
        dragCamera();
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();

        // Рисуем актеров
        super.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (map != null) map.dispose();
        if (renderer != null) renderer.dispose();
        actorTexture2.dispose();
        bulletTexture.dispose();
        instance = null;
    }
}
