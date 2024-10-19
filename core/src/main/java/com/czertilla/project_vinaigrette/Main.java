package com.czertilla.project_vinaigrette;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
import com.badlogic.gdx.Game;
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
