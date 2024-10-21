package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.czertilla.project_vinaigrette.screen.menu.PauseMenu;
import com.czertilla.project_vinaigrette.utils.R;

public class PauseMenuStage extends BaseMenuStage{
    private static PauseMenuStage instance;

    public static PauseMenuStage getInstance(PauseMenu menu){
        if (instance == null)
            instance = new PauseMenuStage();
        instance.menu = menu;
        return instance;
    }

    private PauseMenuStage(){
        super();
    }

    @Override
    public void show() {
        super.show();

        createContinueButton();
        createSettingsButton();
        createQuitButton();
    }

    private void createSettingsButton() {
        TextButton button = getNewTextButton(bundle.get(R.id.SETTINGS));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.setStage(SettingsMenuStage.getInstance(menu));
            }
        });

        initButton(button);
    }

    private void createContinueButton() {
        TextButton button = getNewTextButton(bundle.get(R.id.CONTINUE));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onEscape();
            }
        });

        initButton(button);
    }

    private void createQuitButton() {
        TextButton button = getNewTextButton(bundle.get(R.id.QUIT));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((PauseMenu)menu).exit();
            }
        });

        initButton(button);
    }

    @Override
    public void onEscape() {
        ((PauseMenu)menu).resumeGame();
    }

    @Override
    public void onBack() {
        onEscape();
    }
}
