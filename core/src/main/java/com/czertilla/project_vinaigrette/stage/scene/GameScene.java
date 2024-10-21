package com.czertilla.project_vinaigrette.stage.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.BulletActor;

public class GameScene extends BaseScene {
    private PlayerActor actor2;
    private Texture actorTexture1;
    private Texture actorTexture2;
    private Texture bulletTexture;
    private TextureRegion regionbullet;
    private float bulletSpeed = 500f;

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

        // Загружаем текстуры для актеров
        actorTexture2 = new Texture(Gdx.files.internal("ui/gg.png"));
        TextureRegion region2 = new TextureRegion(actorTexture2);
        actor2 = new PlayerActor(region2);
        actor2.setPosition(500,500 );
        addActor(actor2);
        actor2.setSize(200,200);
        bulletTexture = new Texture(Gdx.files.internal("ui/bullet.png"));
        TextureRegion regionbullet = new TextureRegion(bulletTexture);

        // Устанавливаем обработчик ввода для упр   авления первым актером
    }

    public void act(float deltaTime) {
        super.act(deltaTime);
        //if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            //shootBullet();
        //}

    }

    private void shootBullet() {
        // Получаем координаты центра актора (игрока или пушки)
        float actorCenterX = actor.getX() + actor.getWidth() / 2;
        float actorCenterY = actor.getY() + actor.getHeight() / 2;

        // Получаем координаты курсора
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Инверсия Y

        // Вычисляем угол между центром актора и курсором
        float angle = (float) Math.toDegrees(Math.atan2(mouseY - actorCenterY, mouseX - actorCenterX));

        // Создаем пулю и добавляем её на сцену
        BulletActor bullet = new BulletActor(regionbullet, actorCenterX, actorCenterY, angle, bulletSpeed);
        addActor(bullet);
    }


    @Override
    public void dispose() {
        super.dispose(); // Освобождаем ресурсы базовой сцен// Освобождаем текстуры
        actorTexture2.dispose();
        bulletTexture.dispose();
    }
}
