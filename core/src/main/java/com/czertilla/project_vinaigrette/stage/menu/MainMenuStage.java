package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.czertilla.project_vinaigrette.screen.menu.MainMenu;

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

        createContinueButton();
        createSettingsButton();
        if (Gdx.app.getType().equals(Application.ApplicationType.Desktop))
            createQuitButton();
    }

    private void createSettingsButton() {
        TextButton button = getNewTextButton("Settings");

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.setStage(SettingsMenuStage.getInstance(menu));
            }
        });

        initButton(button);
    }

    private void createContinueButton() {
        TextButton button = getNewTextButton("Continue");

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                TODO implement Continue Button click trigger
            }
        });

        initButton(button);
    }

    private void createNewGameButton() {
        TextButton button = getNewTextButton("New Game");

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                TODO implement New Game Button click trigger
            }
        });

        initButton(button);
    }

    private void createQuitButton() {
        TextButton button = getNewTextButton("Quit");

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        initButton(button);
    }

}
