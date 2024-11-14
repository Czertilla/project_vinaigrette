package com.czertilla.project_vinaigrette.handler;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.czertilla.project_vinaigrette.stage.BaseStage;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;

public class InputHandler extends BaseHandler {
    private final PlayerActor actor;
    private boolean moveUp = false;
    private int state = 1;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private AnimationHandler animation = new AnimationHandler("Player.atlas");

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
        if (keycode == Input.Keys.W) {
            moveUp = false;
            state =1;
        }
        if (keycode == Input.Keys.S) {
            moveDown = false;
            state =2;
        }
        if (keycode == Input.Keys.A)
        {
            moveLeft = false;
            state =3;
        }
        if (keycode == Input.Keys.D)
        {
            moveRight = false;
            state =4;
        }
        if (keycode == Input.Keys.E) {
            actor.pressE();
        }
        if (keycode == Input.Keys.R)
        {
            actor.onReload();
        }
        return super.keyUp(keycode);
    }

    public void update() {
        switch (state) {
            case 1 -> animation.idle_down(actor);
            case 2 -> animation.idle_up(actor);
            case 3 -> animation.idle_left(actor);
            case 4 -> animation.idle_right(actor);
        }
        super.update();
        Vector3 velocity = new Vector3();
        if (moveUp) {
            velocity.y ++;
            animation.up(actor);
        }
        else if (moveDown) {
            velocity.y --;
            animation.down(actor);
        }
        if (moveLeft) {
            velocity.x --;
            animation.left(actor);
        }
        else if (moveRight) {
            velocity.x ++;
            animation.right(actor);
        }
        actor.setVelocity(velocity);
    }
}

