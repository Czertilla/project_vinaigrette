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
    private final SpriteBatch batch;
    private Texture backgroundTexture;
    public BaseScene scene;
    private final Main game;

    public MainGame(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        scene = GameScene.getInstance(this);
    }

    @Override
    public void show(){
        scene.show();
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
    }

    @Override
    public void dispose() {
        // Освобождение ресурсов
        batch.dispose();
        scene.dispose();
    }

    public void onPause() {
        game.setScreen(new PauseMenu(game));
    }
}
