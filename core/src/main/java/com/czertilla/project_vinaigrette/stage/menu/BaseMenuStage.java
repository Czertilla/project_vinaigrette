package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.asset.Bundle;
import com.czertilla.project_vinaigrette.handler.BaseHandler;
import com.czertilla.project_vinaigrette.screen.menu.BaseMenu;
import com.czertilla.project_vinaigrette.stage.BaseStage;
import com.czertilla.project_vinaigrette.utils.C;
import com.czertilla.project_vinaigrette.utils.CharSet;
import com.czertilla.project_vinaigrette.utils.R;
import com.czertilla.project_vinaigrette.utils.ScrollPane;

abstract public class BaseMenuStage extends BaseStage {
    protected static I18NBundle bundle;
    private BaseHandler handler;
    static BaseMenuStage instance;
    protected BaseMenu menu;
    protected final Drawable buttonDrawable;
    ScrollPane scrollPane;
    protected final Table buttonTable;
    private TextButton.TextButtonStyle textButtonStyle;
    private SelectBox.SelectBoxStyle selectBoxStyle;

    public static
    float[] scaleLine = C.SCALE_ARRAY;

    protected BaseMenuStage(){
        super(new ScreenViewport());

        Skin skin = new Skin();
        bundle = Bundle.getInstance();
        createTextButtonStyle();
        buttonDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture(R.path.BUTTON_DRAWABLE_REGION))
        );
        buttonTable = new Table();
        scrollPane = new ScrollPane(buttonTable);

        this.addActor(scrollPane);
    }

    public void setHandler(BaseHandler handler) {
        this.handler = handler;
    }

    public void show(){
        buttonTable.clear();
        setDebugAll(Main.debug);

        createTextButtonStyle();
    }
    protected void initButton(Actor button){
        button.setDebug(Main.debug);
        buttonTable.add(button)
            .width(C.MENU_BUTTONS_WIDTH * scaleLine[Main.scale])
            .height(C.MENU_BUTTONS_HEIGHT * scaleLine[Main.scale])
            ;
        buttonTable.row();
    }

    public TextButton getNewTextButton(String label){
        return new TextButton(label, textButtonStyle);
    }

    <T> SelectBox<T> getNewSelectBox(T[] params){
        SelectBox<T> box = new SelectBox<>(selectBoxStyle);
        box.setAlignment(Align.center);
        box.getList().setAlignment(Align.center);
        box.setItems(params);
        return box;
    }

    private BitmapFont getFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal(R.path.PIXEL_FONT)
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (C.FONT_SIZE * scaleLine[Main.scale]);
        parameter.characters = CharSet.CHARSET;
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

    void createSelectBoxStyle() {
        selectBoxStyle = new SelectBox.SelectBoxStyle();
        BitmapFont font = getFont();
        selectBoxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
        selectBoxStyle.listStyle = new List.ListStyle();
        selectBoxStyle.listStyle.font = font;
        selectBoxStyle.listStyle.selection = buttonDrawable;
        selectBoxStyle.font = font;
        selectBoxStyle.background = buttonDrawable;
        selectBoxStyle.fontColor = Color.BLACK;
        selectBoxStyle.overFontColor = Color.CORAL;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        handler.update();
    }

    public void resize(int width, int height) {
        getViewport().update(width, height, true);
        scrollPane.setBounds(0, 0, getWidth(), getHeight());
    }
}
