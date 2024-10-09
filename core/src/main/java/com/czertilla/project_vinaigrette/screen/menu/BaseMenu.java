package com.czertilla.project_vinaigrette.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.stage.menu.BaseMenuStage;

public abstract class BaseMenu extends ScreenAdapter {
    static protected BaseMenu instance;
    protected final Main game;
    protected Texture background;
    protected BaseMenuStage stage;
//    private final Sound clickSound;

    static protected final float
        FRAMES_LOCK = 30f;

    public BaseMenu(final Main game) {
        this.game = game;
    }

    public void show(){
        this.stage.show();
    }

    public void setStage(BaseMenuStage stage){
        this.stage = stage;

        if (stage != null) {
            Gdx.input.setInputProcessor(stage);
            stage.show();
            this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public abstract void setDefaultStage();

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        stage.getBatch().begin();
//        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / FRAMES_LOCK));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
//        clickSound.dispose();
    }
}
