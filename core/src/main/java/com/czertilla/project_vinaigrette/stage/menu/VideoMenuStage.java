package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.asset.Bundle;
import com.czertilla.project_vinaigrette.screen.menu.BaseMenu;
import com.czertilla.project_vinaigrette.screen.menu.MainMenu;
import com.czertilla.project_vinaigrette.utils.C;
import com.czertilla.project_vinaigrette.utils.R;

public class VideoMenuStage extends BaseMenuStage{
    private static VideoMenuStage instance;

    private int
        lastWidth = C.DEFAULT_WIDTH,
        lastHeight = C.DEFAULT_HEIGHT;
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
        TextButton button = getNewTextButton(bundle.get(R.id.BACK));

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onBack();
            }
        });

        initButton(button);
    }

    private void createFullscreenButton() {
        TextButton button = getNewTextButton("");

        boolean isFullscreen = Gdx.graphics.isFullscreen();
        button.setChecked(isFullscreen);
        button.setText(
            bundle.get(R.id.FULLSCREEN) +
            (isFullscreen ?
                bundle.get(R.id.SWITCH_ON) :
                bundle.get(R.id.SWITCH_OFF)
            )
        );
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (button.isChecked()) {
                    lastWidth = Gdx.graphics.getWidth();
                    lastHeight = Gdx.graphics.getHeight();
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
                else
//                    TODO put fullscreen switcher in some method
                    Gdx.graphics.setWindowedMode(lastWidth, lastHeight);
                show();
            }
        });

        initButton(button);
    }

    private void createScaleButton() {
        TextButton button = getNewTextButton("");

        int scale = Main.scale;
        button.setText(bundle.format(R.id.SCALE, scaleLine[scale]));
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
        TextButton button = getNewTextButton("");

        boolean isDebug = Main.debug;
        button.setChecked(isDebug);
        button.setText(
            bundle.get(R.id.DEBUG) + (isDebug ?
                bundle.get(R.id.SWITCH_ON) :
                bundle.get(R.id.SWITCH_OFF)
            )
        );
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.debug = button.isChecked();
                show();
            }
        });

        initButton(button);
    }

    @Override
    public void onEscape() {
        menu.setDefaultStage();
    }

    @Override
    public void onBack() {
        menu.setStage(SettingsMenuStage.getInstance(menu));
    }
}
