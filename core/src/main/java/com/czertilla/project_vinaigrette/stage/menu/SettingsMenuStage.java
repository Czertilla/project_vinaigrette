package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.czertilla.project_vinaigrette.asset.Bundle;
import com.czertilla.project_vinaigrette.screen.menu.BaseMenu;
import com.czertilla.project_vinaigrette.utils.R;

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
        createSelectBoxStyle();

        createVideoButton();
        createLanguageButton();
        createBackButton();
    }

    private void createLanguageButton() {
        SelectBox<String> box = getNewSelectBox(Bundle.getLangList());

        box.setSelectedIndex(Bundle.getSelectedLang());
        box.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Bundle.setLanguage(box.getSelectedIndex());
                bundle = Bundle.getInstance();
                show();
            }
        });

        initButton(box);
    }

    private void createVideoButton(){
        TextButton button = getNewTextButton(bundle.get(R.id.VIDEO));

        button.addListener(new SoundClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                menu.setStage(VideoMenuStage.getInstance(menu));
            }
        });

        initButton(button);
    }
    private void createBackButton() {
        TextButton button = getNewTextButton(bundle.get(R.id.BACK));
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
        menu.setDefaultStage();
    }

    @Override
    public void onBack() {
        onEscape();
    }
}
