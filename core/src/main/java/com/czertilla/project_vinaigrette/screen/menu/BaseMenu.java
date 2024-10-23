package com.czertilla.project_vinaigrette.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.handler.BaseHandler;
import com.czertilla.project_vinaigrette.stage.menu.BaseMenuStage;
import com.czertilla.project_vinaigrette.utils.C;
import com.czertilla.project_vinaigrette.utils.R;

public abstract class BaseMenu extends ScreenAdapter {
    static protected BaseMenu instance;
    protected final Main game;
    protected Texture background;
    protected BaseMenuStage stage;
    private static final Sound clickSound = Gdx.audio.newSound(new FileHandle(R.path.CLICK_SOUND));

    public BaseMenu(final Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.stage.show();
    }

    public void setStage(BaseMenuStage stage){
        this.stage = stage;

        if (stage != null) {
            InputMultiplexer multiplexer = new InputMultiplexer();
            BaseHandler handler = new BaseHandler(stage);
            stage.setHandler(handler);
            multiplexer.addProcessor(handler);
            multiplexer.addProcessor(stage);
            Gdx.input.setInputProcessor(multiplexer);
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

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / C.FRAMES_LOCK));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.resize(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        clickSound.dispose();
    }
}
