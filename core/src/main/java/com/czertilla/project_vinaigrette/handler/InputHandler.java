package com.czertilla.project_vinaigrette.handler;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.czertilla.project_vinaigrette.stage.BaseStage;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;

public class InputHandler extends BaseHandler {
    private final PlayerActor actor;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public InputHandler(PlayerActor actor, BaseStage stage) {
        super(stage);
        this.actor = actor;
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
        if (keycode == Input.Keys.E)  actor.pressE();
        return super.keyUp(keycode);
    }

    public void update() {
        super.update();
        Vector3 velocity = new Vector3();
        if (moveUp) velocity.y ++;
        if (moveDown) velocity.y --;
        if (moveLeft) velocity.x --;
        if (moveRight) velocity.x ++;
        actor.setVelocity(velocity);
    }
}

