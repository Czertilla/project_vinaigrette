package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.czertilla.project_vinaigrette.screen.menu.MainMenu;
import com.czertilla.project_vinaigrette.stage.menu.SettingsMenuStage;
import com.czertilla.project_vinaigrette.utils.R;

public class MainMenuStage extends BaseMenuStage {
    private static MainMenuStage instance;

    public static MainMenuStage getInstance(MainMenu menu) {
        if (instance == null) {
            instance = new MainMenuStage(menu); // Вызываем конструктор с параметром
        }
        instance.menu = menu;
        return instance;
    }

    private MainMenuStage(MainMenu menu) {
        super();
        this.menu = menu;

        addLogoAndButtons();
    }

    private void addLogoAndButtons() {
        Table table = new Table();
        table.setFillParent(true);
        this.addActor(table);

        // Загружаем логотип
        Texture logoTexture = new Texture("ui/logo.png"); // Проверьте путь к файлу
        Image logoImage = new Image(logoTexture);

        // Добавляем логотип
        table.top().add(logoImage).padBottom(20f);
        table.row();
    }

    @Override
    public void show() {
        super.show();

        // TODO: Реализовать обнаружение сохранений
        createNewGameButton();
        createSettingsButton();
        if (Gdx.app.getType().equals(Application.ApplicationType.Desktop)) {
            createQuitButton();
        }
    }

    private void createSettingsButton() {
        TextButton button = getNewTextButton(bundle.get(R.id.SETTINGS));

        button.addListener(new SoundClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                menu.setStage(SettingsMenuStage.getInstance(menu));
            }
        });

        initButton(button);
    }

    private void createContinueButton() {
        TextButton button = getNewTextButton(bundle.get(R.id.CONTINUE));

        button.addListener(new SoundClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
//                TODO implement Continue Button click trigger
            }
        });

        initButton(button);
    }

    private void createNewGameButton() {
        TextButton button = getNewTextButton(bundle.get(R.id.NEW_GAME));

        button.addListener(new SoundClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ((MainMenu)menu).startGame();
            }
        });

        initButton(button);
    }

    private void createQuitButton() {
        TextButton button = getNewTextButton(bundle.get(R.id.QUIT));

        button.addListener(new SoundClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                onEscape();
            }
        });

        initButton(button);
    }

    @Override
    public void onEscape() {
        Gdx.app.exit();
    }

    @Override
    public void onBack() {
        onEscape();
    }
}
