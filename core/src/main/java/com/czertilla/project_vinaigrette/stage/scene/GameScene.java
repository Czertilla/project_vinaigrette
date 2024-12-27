package com.czertilla.project_vinaigrette.stage.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.czertilla.project_vinaigrette.handler.InputHandler;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
import com.czertilla.project_vinaigrette.stage.scene.actor.EnemyActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.WallActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm.Rifle;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm.Shotgun;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.melee.MeleeWeapon;
import com.czertilla.project_vinaigrette.utils.C;

import java.util.ArrayList;

public class GameScene extends BaseScene {
    private PlayerActor player;
    SpriteBatch batch = new SpriteBatch();
    Texture rectangleTexture = new Texture("ui/gg.png");
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Texture actorTexture2;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    final InputHandler inputHandler;
    private Texture bulletTexture;
    EnemyActor enemy;
    private OrthographicCamera camera;

    private static GameScene instance;
    int[] up = new int[2];
    ArrayList<Integer> down = new ArrayList<>();
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
        //
        Texture texturep = new Texture(Gdx.files.internal("ui/img.png"));
        actor = new PlayerActor(new TextureRegion(texturep));
//        TODO replace numeric constant
        actor.setPosition(200, 200);
        actor.setSize(150, 185);// Устанавливаем актера в центре экрана
        addActor(actor); // Добавляем актера в сцену
        //
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("ui/demo_scene.tmx");

        inputHandler = new InputHandler(actor,this);
        Gdx.input.setInputProcessor(inputHandler);

        MapProperties prop = map.getProperties();

// Ширина и высота карты в тайлах
        int mapWidthInTiles = prop.get("width", Integer.class);
        int mapHeightInTiles = prop.get("height", Integer.class);

// Размер тайла в пикселях (ширина и высота)
        int tileWidth = prop.get("tilewidth", Integer.class);
        int tileHeight = prop.get("tileheight", Integer.class);

// Подсчёт размеров карты в пикселях
        float mapPixelWidth = mapWidthInTiles * tileWidth;
        float mapPixelHeight = mapHeightInTiles * tileHeight;
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.render();
        MapLayers layers = map.getLayers();
// Перебираем все слои и выводим их названия
        for (MapLayer layer : layers) {
            if(layer.getName().equalsIgnoreCase( "decor")){
                up[0] = (layers.getIndex(layer.getName()));
            } else if (layer.getName().equalsIgnoreCase( "decor2")){
                up[1] = (layers.getIndex(layer.getName()));
            }
            else  {
                down.add(layers.getIndex(layer.getName()));
            }

        }
        MapObjects objects = map.getLayers().get("cool").getObjects();

        // Перебираем объекты и извлекаем их прямоугольники
        for (int i = 0; i < objects.getCount(); i++) {
            if (objects.get(i) instanceof RectangleMapObject) {
                RectangleMapObject rectObj = (RectangleMapObject) objects.get(i);
                Rectangle rect = rectObj.getRectangle();
                float worldX = rect.x;
                float worldY = rect.y;
                float worldWidth = rect.width;
                float worldHeight = rect.height;
                // Добавляем актёра-стену в сцену
                addActor(new WallActor(worldX, worldY, worldWidth, worldHeight));
            }
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getViewport().setCamera(camera);

        actorTexture2 = new Texture(Gdx.files.internal("ui/zombie.png"));
        TextureRegion region2 = new TextureRegion(actorTexture2);

        TextureRegion playerRegion = new TextureRegion(actorTexture2);
        player = new PlayerActor(playerRegion);
        player.setPosition(500, 500);
        player.setSize(32, 64);
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
        shotgun.setSize(100, 50);
        addActor(shotgun);

        TextureRegion region3 = new TextureRegion(actorTexture2);
//        enemy = new EnemyActor(region3, 100);
//        enemy.setPosition(-50, -50);
//        enemy.setSize(200, 200);
//        addActor(enemy);w





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
        inputHandler.update();
        dragCamera();
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        int[] arr = new int[down.size()];
        for (int i = 0; i < down.size(); i++) {
            arr[i] = down.get(i);
        }
        renderer.render(arr);
        // Рисуем актеров
        super.draw();
        renderer.render(up);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        // Рисуем линии между вершинами полигона
        float[] vertices =actor.boundingBox.getTransformedVertices();
        for (int i = 0; i < vertices.length; i += 2) {
            float x1 = vertices[i];
            float y1 = vertices[i + 1];
            float x2 = vertices[(i + 2) % vertices.length];
            float y2 = vertices[(i + 3) % vertices.length];
            shapeRenderer.line(x1, y1, x2, y2); // Соединяем вершины линиями

        }
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.end();

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
