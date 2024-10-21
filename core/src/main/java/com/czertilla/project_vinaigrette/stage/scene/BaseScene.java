package com.czertilla.project_vinaigrette.stage.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
import com.czertilla.project_vinaigrette.stage.BaseStage;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;
import com.czertilla.project_vinaigrette.handler.InputHandler;

public class BaseScene extends BaseStage {
    protected PlayerActor actor;
    final InputHandler inputHandler;

    protected MainGame screen;
    public BaseScene(MainGame screen) {
        super();
        this.screen = screen;
        // Устанавливаем FitViewport с начальной шириной и высотой
        setViewport(new FitViewport(1280, 960)); // Устанавливаем начальные размеры сцены

        // Загрузка текстуры
//        TODO replace string filepath by variable from R.path class in utils. use "paths" bundle
        Texture texture = new Texture(Gdx.files.internal("ui/shutgun.png"));
        TextureRegion region = new TextureRegion(texture); // Создаем TextureRegion
        actor = new PlayerActor(region);
//        TODO replace numeric constant
        actor.setPosition(200, 200);
        actor.setSize(200, 100);// Устанавливаем актера в центре экрана
        actor.toFront();
        addActor(actor); // Добавляем актера в сцену
        actor.toFront();

        inputHandler = new InputHandler(actor,this);
        Gdx.input.setInputProcessor(inputHandler);
    }

    public void show(){
        setDebugAll(Main.debug);
    }

    @Override
    public void onEscape() {
        screen.onPause();
    }

    @Override
    public void onBack() {

    }

    private void checkCollisions() {
        // Сохраняем ссылки на актеров в виде списка
        Array<Actor> actors = getActors();

        // Проходим по каждому актеру
        for (int i = 0; i < actors.size; i++) {
            Actor actor = actors.get(i);
            if (actor instanceof PlayerActor) {
                PlayerActor playerActor = (PlayerActor) actor;

                // Проверяем столкновения только с остальными актерами
                for (int j = 0; j < actors.size; j++) {
                    if (i == j) continue; // Игнорируем самого себя
                    Actor other = actors.get(j);
                    if (other instanceof PlayerActor) {
                        PlayerActor otherActor = (PlayerActor) other;
                        if (playerActor.collidesWith(otherActor)) {
                            playerActor.handleCollision(otherActor);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        inputHandler.update(delta);
        checkCollisions();
    }

    // Метод для обработки изменения размера окна

    public void resize(int width, int height) {
        // Обновляем viewport при изменении размеров окна
        getViewport().update(width, height, true);
    }

}
