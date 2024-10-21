package com.czertilla.project_vinaigrette.handler;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.czertilla.project_vinaigrette.stage.BaseStage;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;

public class InputHandler extends InputAdapter {
    private final PlayerActor actor;
    private final float speed = 200f;
//    TODO remove useless numeric constants
    private final BaseStage stage;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean escape = false;

    public InputHandler(PlayerActor actor, BaseStage stage) {
        this.actor = actor;
        this.stage = stage;
    }

    @Override
    public boolean keyDown(int keycode) {
        // Устанавливаем флаги для движения
        if (keycode == Input.Keys.W) moveUp = true;
        if (keycode == Input.Keys.S) moveDown = true;
        if (keycode == Input.Keys.A) moveLeft = true;
        if (keycode == Input.Keys.D) moveRight = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        // Сбрасываем флаги при отпускании клавиш
        if (keycode == Input.Keys.W) moveUp = false;
        if (keycode == Input.Keys.S) moveDown = false;
        if (keycode == Input.Keys.A) moveLeft = false;
        if (keycode == Input.Keys.D) moveRight = false;
        if (keycode == Input.Keys.ESCAPE) escape = true;
        return true;
    }

    public void update(float delta) {
        float speed = 300 * delta; // Скорость перемещения актора
//        TODO remove constants as local variables. Use property file "game" in "numbers" assets dir
        // Обновляем позицию актора в зависимости от состояния флагов
        if (escape) {
            escape = false;
            stage.onEscape();
        }
        if (moveUp) actor.moveBy(0, speed);
        if (moveDown) actor.moveBy(0, -speed);
        if (moveLeft) actor.moveBy(-speed, 0);
        if (moveRight) actor.moveBy(speed, 0);
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 screenCoords = new Vector3(screenX, screenY, 0);
        Vector3 worldCoords = stage.getViewport().unproject(screenCoords);

        // Поворачиваем актера в сторону курсора с учетом преобразованных координат
        actor.rotateTowards(worldCoords.x, worldCoords.y);
        return true;
        // Получаем координаты курсора


    }
}

