package com.czertilla.project_vinaigrette.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.screen.menu.PauseMenu;
import com.czertilla.project_vinaigrette.stage.scene.BaseScene;
import com.czertilla.project_vinaigrette.stage.scene.GameScene;

public class MainGame extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture backgroundTexture;
    public BaseScene scene;
    private Main game;

    public MainGame(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        scene = GameScene.getInstance(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Очистка экрана черным цветом
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Обновляем логику сцен

        // Отрисовываем сцену
        scene.draw();
        scene.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        scene.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Освобождение ресурсов при скрытии экрана
//        TODO implement stopping game without dispose for PauseMenu
    }

    @Override
    public void dispose() {
        // Освобождение ресурсов
        batch.dispose();
        game.dispose();
    }
}
