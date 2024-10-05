package com.czertilla.project_vinaigrette.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.czertilla.project_vinaigrette.scene.actor.BaseActor;
import com.czertilla.project_vinaigrette.handler.InputHandler;

public class BaseScene extends Stage {
    protected BaseActor actor;
    private InputHandler inputHandler;
    private ShapeRenderer shapeRenderer;
    public BaseScene() {
        shapeRenderer = new ShapeRenderer();
        // Устанавливаем FitViewport с начальной шириной и высотой
        setViewport(new FitViewport(1280, 960)); // Устанавливаем начальные размеры сцены

        // Загрузка текстуры
        Texture texture = new Texture(Gdx.files.internal("ui/shutgun.png"));
        TextureRegion region = new TextureRegion(texture); // Создаем TextureRegion
        actor = new BaseActor(region);
        actor.setPosition(200, 200);
        actor.setSize(200, 100);// Устанавливаем актера в центре экрана
        actor.toFront();
        addActor(actor); // Добавляем актера в сцену
        actor.toFront();

        inputHandler = new InputHandler(actor,this);
        Gdx.input.setInputProcessor(inputHandler);
    }


    @Override
    public void draw() {
        super.draw();
        drawBoundingBoxes();
    }
    private void drawBoundingBoxes() {
        shapeRenderer.setProjectionMatrix(getCamera().combined);  // Установка матрицы проекции камеры
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);  // Начало отрисовки линий
        shapeRenderer.setColor(1, 0, 0, 1);  // Цвет бокса (красный)

        // Проход по всем актёрам
        for (Actor actor : getActors()) {
            if (actor instanceof BaseActor) {
                BaseActor baseActor = (BaseActor) actor;
                baseActor.drawBoundingBox(shapeRenderer);  // Отрисовка бокса для каждого BaseActor
            }
        }

        shapeRenderer.end();  // Конец отрисовки
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        inputHandler.update(delta);
    }

    // Метод для обработки изменения размера окна

    public void resize(int width, int height) {
        // Обновляем viewport при изменении размеров окна
        getViewport().update(width, height, true);
    }

}
