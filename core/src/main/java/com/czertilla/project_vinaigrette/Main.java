package com.czertilla.project_vinaigrette;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.czertilla.project_vinaigrette.scene.GameScene;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.czertilla.project_vinaigrette.screen.MainGameScreen;

public class Main extends Game {

    @Override
    public void create() {
        // Устанавливаем стартовый экран
        this.setScreen(new MainGameScreen());
    }

    @Override
    public void render() {
        // Вызываем метод render() текущего экрана
        super.render();
    }

    @Override
    public void dispose() {
        // Освобождаем ресурсы текущего экрана
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}
