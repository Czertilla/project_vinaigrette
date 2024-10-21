package com.czertilla.project_vinaigrette.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class BaseStage extends Stage {
    public BaseStage(ScreenViewport screenViewport) {
        super(screenViewport);
    }
    public BaseStage(){
        super();
    }
    abstract public void onEscape();

    abstract public void onBack();
}
