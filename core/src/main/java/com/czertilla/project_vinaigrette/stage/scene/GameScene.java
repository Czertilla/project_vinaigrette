package com.czertilla.project_vinaigrette.stage.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.BulletActor;
import com.czertilla.project_vinaigrette.utils.C;

public class GameScene extends BaseScene {
    private PlayerActor actor2;
    private Texture actorTexture1;
    private Texture actorTexture2;
    private Texture bulletTexture;
    private TextureRegion regionbullet;
    private float bulletSpeed = 5000f;
    private OrthographicCamera camera;

    private static GameScene instance;

    public static GameScene getInstance(MainGame screen){
        if (instance == null)
            instance = new GameScene(screen);
        else {
            instance.screen = screen;
            Gdx.input.setInputProcessor(instance.inputHandler);
        }
        return instance;
    }

    private GameScene(MainGame screen) {
        super(screen); // Вызов конструктора базовой сцены

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        getViewport().setCamera(camera);

        // Загружаем текстуры для актеров
        actorTexture2 = new Texture(Gdx.files.internal("ui/gg.png"));
        TextureRegion region2 = new TextureRegion(actorTexture2);
        actor2 = new PlayerActor(region2);
        actor2.setPosition(500,500 );
        addActor(actor2);
        actor2.setSize(200,200);
        bulletTexture = new Texture(Gdx.files.internal("ui/bullet.png"));
        regionbullet = new TextureRegion(bulletTexture);

        // Устанавливаем обработчик ввода для упр   авления первым актером
    }

    private void dragCamera(){
        Vector3 delta = actor.getCenter().sub(camera.position);
        float mlp = delta.len();
        float LIM = C.CAM_DELTA_LIMIT;
        if (mlp < LIM)
            mlp = mlp / LIM;
        else
            mlp = 1f - (LIM / mlp);
        camera.position.mulAdd(delta, (float)Math.sqrt(mlp));

        Vector3 sight = getViewport().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        sight.sub(actor.getCenter());
        camera.position.mulAdd(sight, Math.min(sight.len()/LIM, LIM/sight.len())/sight.len()*C.CAM_SIGHT_DELTA);
    }

    public void act(float deltaTime) {
        super.act(deltaTime);
        dragCamera();
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            shootBullet();
        }

    }

    private void shootBullet() {
        // Получаем координаты центра актора (игрока или пушки)
        Vector3 dot = actor.getCenter();


        // Получаем координаты курсора
        Vector3 screenCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 destination = getViewport().unproject(screenCoords);


        // Создаем пулю и добавляем её на сцену
        BulletActor bullet = new BulletActor(regionbullet, actor.getCenter(), destination, bulletSpeed);
        addActor(bullet);
    }


    @Override
    public void dispose() {
        super.dispose(); // Освобождаем ресурсы базовой сцен// Освобождаем текстуры
        actorTexture2.dispose();
        bulletTexture.dispose();
        instance = null;
    }
}
