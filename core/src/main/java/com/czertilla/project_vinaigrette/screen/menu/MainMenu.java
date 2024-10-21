package com.czertilla.project_vinaigrette.screen.menu;

import com.badlogic.gdx.graphics.Texture;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
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


    public void startGame() {
        game.setScreen(new MainGame());
    }

    public void dispose() {
        super.dispose();
        background.dispose();
//        backgroundMusic.dispose();
//        clickSound.dispose();
    }
}
