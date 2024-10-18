package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.asset.Bundle;
import com.czertilla.project_vinaigrette.screen.menu.BaseMenu;

public class BaseMenuStage extends Stage {

    protected static final String prefix = "BUTTONS/MENU/";
    protected I18NBundle bundle;
    static BaseMenuStage instance;
    protected BaseMenu menu;
    protected final Drawable buttonDrawable;
    ScrollPane scrollPane;
    protected final Table buttonTable;
    private TextButton.TextButtonStyle textButtonStyle;

    public static
    float[] scaleLine = new float[]{1f, 1.5f, 2f, 0.25f, 0.5f, 0.75f};

    static protected final float
        MENU_BUTTONS_WIDTH = 500f,
        MENU_BUTTONS_HEIGHT = 150f;
    static protected final int
        FONT_SIZE = 40,
        MENU_BUTTONS_ROWS = 1;

    protected BaseMenuStage(){
        super(new ScreenViewport());

        Skin skin = new Skin();
        bundle = Bundle.getInstance();
        createTextButtonStyle();
        buttonDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture("buttonDrawableRegion.png"))
        );
        buttonTable = new Table();
        scrollPane = new ScrollPane(buttonTable);

        this.addActor(scrollPane);
    }

    public void show(){
        buttonTable.clear();
        setDebugAll(Main.debug);

        createTextButtonStyle();
    }
    protected void initButton(Button button){
        button.setDebug(Main.debug);
        buttonTable.add(button)
            .width(MENU_BUTTONS_WIDTH * scaleLine[Main.scale])
            .height(MENU_BUTTONS_HEIGHT * scaleLine[Main.scale])
            ;
        buttonTable.row();
    }

    public TextButton getNewTextButton(String label){
        return new TextButton(label, textButtonStyle);
    }
    private BitmapFont getFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/pixel_font.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (FONT_SIZE * scaleLine[Main.scale]);
        BitmapFont font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        generator.dispose();
        return font;
    }

    private void createTextButtonStyle() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = getFont();
        textButtonStyle.up = buttonDrawable;
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.downFontColor = Color.WHITE;
        textButtonStyle.overFontColor = Color.CORAL;
        textButtonStyle.checkedFontColor = Color.GREEN;
    }

    public void resize(int width, int height) {
        getViewport().update(width, height, true);
        scrollPane.setBounds(0, 0, getWidth(), getHeight());
    }
}
