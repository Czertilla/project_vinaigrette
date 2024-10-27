package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.czertilla.project_vinaigrette.screen.menu.MainMenu;
import com.czertilla.project_vinaigrette.utils.R;

public class MainMenuStage extends BaseMenuStage{
    private static MainMenuStage instance;
    public static MainMenuStage getInstance(MainMenu menu){
        if (instance == null){
            instance = new MainMenuStage();
        }
        instance.menu = menu;
        return instance;
    }

    public void show(){
        super.show();

        if (false)
//            TODO implement saves detections
            createContinueButton();
        createNewGameButton();
        createSettingsButton();
        if (Gdx.app.getType().equals(Application.ApplicationType.Desktop))
            createQuitButton();
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
