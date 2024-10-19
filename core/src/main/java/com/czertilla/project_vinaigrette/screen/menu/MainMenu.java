package com.czertilla.project_vinaigrette.screen.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.stage.menu.MainMenuStage;
import com.czertilla.project_vinaigrette.utils.R;

public class MainMenu extends BaseMenu {
    public MainMenu(final Main game) {
        super(game);

        background = new Texture(R.path.MAIN_MENU_BACKGROUND);

        setDefaultStage();
    }

    public void setDefaultStage(){
        setStage(MainMenuStage.getInstance(this));
    }


    public void dispose() {
        super.dispose();
        background.dispose();
//        backgroundMusic.dispose();
//        clickSound.dispose();
    }
}
