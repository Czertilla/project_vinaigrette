package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.screen.menu.BaseMenu;
import com.czertilla.project_vinaigrette.screen.menu.MainMenu;

public class VideoMenuStage extends BaseMenuStage{
    private static VideoMenuStage instance;
    public static VideoMenuStage getInstance(BaseMenu menu){
        if (instance == null){
            instance = new VideoMenuStage();
        }
        instance.menu = menu;
        return instance;
    }

//    public static VideoMenuStage getInstance(MainMenu menu){
//        return getInstance(menu);
//    } TODO implement class validation

//    public static SettingsMenuStage getInstance(PauseMenu menu){
//        return getInstance(menu);
//    }

    public void show(){
        super.show();

        createDebugButton();
        createBackButton();
    }

    private void createBackButton() {
        TextButton button = getNewTextButton("back");

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.setStage(SettingsMenuStage.getInstance(menu));
            }
        });

        initButton(button);
    }

    private void createDebugButton() {
        TextButton button = getNewTextButton("debug");

        button.setChecked(Main.debug);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.debug = button.isChecked();
                show();
            }
        });

        initButton(button);
    }
}
