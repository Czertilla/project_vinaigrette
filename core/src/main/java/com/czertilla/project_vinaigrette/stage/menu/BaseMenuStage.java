package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.screen.menu.BaseMenu;

public class BaseMenuStage extends Stage {
    static BaseMenuStage instance;
//    TODO implement 'back' button logic
    protected BaseMenu menu;
    protected final Drawable buttonDrawable;
    protected final Table buttonTable;
    private TextButton.TextButtonStyle textButtonStyle;

    static protected final float
        MENU_BUTTONS_WIDTH = 500f,
        MENU_BUTTONS_HEIGHT = 150f;
    static  protected final int
        FONT_SIZE = 40,
        MENU_BUTTONS_ROWS = 1;

    protected BaseMenuStage(){
        super(new ScreenViewport());

        Skin skin = new Skin();
        createTextButtonStyle();
        buttonDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture("buttonDrawableRegion.png"))
        );
        buttonTable = new Table();
        buttonTable.setFillParent(true);

        this.addActor(buttonTable);
    }

    public void show(){
        buttonTable.clear();
        buttonTable.setDebug(Main.debug);

        createTextButtonStyle();
    }
    protected void initButton(Button button){
        button.setDebug(Main.debug);
        buttonTable.add(button)
            .width(MENU_BUTTONS_WIDTH)
            .height(MENU_BUTTONS_HEIGHT)
            .center();
        buttonTable.row();
    }

    public TextButton getNewTextButton(String label){
        return new TextButton(label, textButtonStyle);
    }

    private void createTextButtonStyle() {
        textButtonStyle = new TextButton.TextButtonStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/pixel_font.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;
        BitmapFont font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        generator.dispose();
        textButtonStyle.font = font;
        textButtonStyle.up = buttonDrawable;
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.downFontColor = Color.WHITE;
        textButtonStyle.overFontColor = Color.CORAL;
        textButtonStyle.checkedFontColor = Color.GREEN;
    }

}
