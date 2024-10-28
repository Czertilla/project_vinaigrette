package com.czertilla.project_vinaigrette.handler;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.czertilla.project_vinaigrette.stage.BaseStage;

public class BaseHandler extends InputAdapter{
    BaseStage stage;
    boolean escape = false;

    public BaseHandler(BaseStage stage) {
        this.stage = stage;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE) escape = true;
        return true;
    }

    public void update(){
        if (escape) {
            escape = false;
            stage.onEscape();
        }
    }
}
