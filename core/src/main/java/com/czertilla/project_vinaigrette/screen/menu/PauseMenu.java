package com.czertilla.project_vinaigrette.screen.menu;

import com.badlogic.gdx.graphics.Texture;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
import com.czertilla.project_vinaigrette.stage.menu.PauseMenuStage;
import com.czertilla.project_vinaigrette.utils.R;

public class PauseMenu extends BaseMenu{
    public PauseMenu(Main game) {
        super(game);

        background = new Texture(R.path.MAIN_MENU_BACKGROUND);

        setDefaultStage();
    }

    @Override
    public void setDefaultStage() {
        setStage(PauseMenuStage.getInstance(this));
    }

    public void resumeGame(){
        game.setScreen(new MainGame(game));
    }
    public void exit() {
        (new MainGame(game)).dispose();
        game.setScreen(new MainMenu(game));
    }

    public void dispose() {
        super.dispose();
        background.dispose();
//        backgroundMusic.dispose();
//        clickSound.dispose();
    }

}
