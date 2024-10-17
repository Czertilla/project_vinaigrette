package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.czertilla.project_vinaigrette.screen.menu.BaseMenu;
import com.czertilla.project_vinaigrette.screen.menu.MainMenu;

public class SettingsMenuStage extends BaseMenuStage{
    private static SettingsMenuStage instance;
    public static SettingsMenuStage getInstance(BaseMenu menu){
        if (instance == null){
            instance = new SettingsMenuStage();
        }
        instance.menu = menu;
        return instance;
    }

//    public static SettingsMenuStage getInstance(MainMenu menu){
//        return getInstance(menu);
//    }
//
//    public static SettingsMenuStage getInstance(PauseMenu menu){
//        return getInstance(menu);
//    }

    public void show(){
        super.show();

        createVideoButton();
        createBackButton();
    }

    private void createVideoButton(){
        TextButton button = getNewTextButton(bundle.get(prefix+"VIDEO"));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.setStage(VideoMenuStage.getInstance(menu));
            }
        });

        initButton(button);
    }
    private void createBackButton() {
        TextButton button = getNewTextButton(bundle.get(prefix+"BACK"));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.setDefaultStage();
            }
        });

        initButton(button);
    }

}
