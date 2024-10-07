package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BaseMenuStage extends Stage {
    static BaseMenuStage instance;

    static public BaseMenuStage getInstance(){
        if (instance == null)
            instance = new BaseMenuStage();
        return instance;
    }

    private BaseMenuStage(){
        super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

}
