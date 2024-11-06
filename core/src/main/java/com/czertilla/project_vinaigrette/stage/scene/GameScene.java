package com.czertilla.project_vinaigrette.stage.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
import com.czertilla.project_vinaigrette.stage.scene.actor.EnemyActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.WallActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm.Shotgun;
import com.czertilla.project_vinaigrette.utils.C;

public class GameScene extends BaseScene {
    private PlayerActor player;
    private Texture actorTexture2;
    private Texture bulletTexture;
    EnemyActor enemy;
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
        actorTexture2 = new Texture(Gdx.files.internal("ui/zombie.png"));
        TextureRegion region2 = new TextureRegion(actorTexture2);
        //player
        TextureRegion playerRegion = new TextureRegion(actorTexture2);
        player = new PlayerActor(playerRegion);
        player.setPosition(500,500);
        addActor(player);
        player.setSize(200,300);
        //bullet
        bulletTexture = new Texture(Gdx.files.internal("ui/bullet.png"));

        Texture texture = new Texture(Gdx.files.internal("ui/shutgun.png"));
        TextureRegion region = new TextureRegion(texture); // Создаем TextureRegion
        Shotgun shotgun = new Shotgun(region, "shotgunA");
        shotgun.setPosition(10,10);
        shotgun.setSize(200,100);
        addActor(shotgun);

        TextureRegion region3 = new TextureRegion(actorTexture2); // Создаем TextureRegion
        enemy = new EnemyActor(region3, 100);
        enemy.setPosition(10,10);
        enemy.setSize(200,200);
        addActor(enemy);

        addActor(new WallActor(300, 500));

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
        enemy.setVelocity(actor.getCenter().sub(enemy.getCenter()).setLength(200));
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            actor.attack();
        }
    }

    @Override
    public void dispose() {
        super.dispose(); // Освобождаем ресурсы базовой сцен// Освобождаем текстуры
        actorTexture2.dispose();
        bulletTexture.dispose();
        instance = null;
    }
}
