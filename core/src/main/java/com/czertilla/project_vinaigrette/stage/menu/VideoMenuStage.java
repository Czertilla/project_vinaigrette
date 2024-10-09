package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
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
        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            createFullscreenButton();
        createScaleButton();
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

    private void createFullscreenButton() {
        TextButton button = getNewTextButton("full screen");

        boolean isFullscreen = Gdx.graphics.isFullscreen();
        button.setChecked(isFullscreen);
        button.setText("full screen: " + (isFullscreen ? "on" : "off"));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (button.isChecked())
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                else
//                    TODO implement remembering last window size to get back
//                    TODO put fullscreen switcher in some method
                    Gdx.graphics.setWindowedMode(800, 600);
                show();
            }
        });

        initButton(button);
    }

    private void createScaleButton() {
        TextButton button = getNewTextButton("");

        int scale = Main.scale;
        button.setText("scale: x" + scaleLine[scale]);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (scaleLine.length <= Main.scale + 1)
                    Main.scale = 0;
                else
                    Main.scale += 1;
                show();
            }
        });

        initButton(button);
    }

    private void createDebugButton() {
        TextButton button = getNewTextButton("debug");

        boolean isDebug = Main.debug;
        button.setChecked(isDebug);
        button.setText("debug: " + (isDebug ? "on" : "off"));
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
