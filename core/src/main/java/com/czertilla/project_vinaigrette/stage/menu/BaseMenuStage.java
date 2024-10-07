package com.czertilla.project_vinaigrette.stage.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BaseMenuStage extends Stage {
    static BaseMenuStage instance;
//    TODO implement 'back' button logic
    protected final Drawable buttonDrawable;
    protected final Table buttonTable;
    private TextButton.TextButtonStyle textButtonStyle;

    static protected final float
        MENU_BUTTONS_WIDTH = 500f,
        MENU_BUTTONS_HEIGHT = 150f;
    static  protected final int
        FONT_SIZE = 40,
        MENU_BUTTONS_ROWS = 1;

    private BaseMenuStage(){
        super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Skin skin = new Skin();
        createTextButtonStyle();
        buttonDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture("buttonDrawableRegion.png"))
        );
        float
            posX = Gdx.graphics.getWidth() / 2f - MENU_BUTTONS_WIDTH,
            posY = Gdx.graphics.getHeight() / 1.5f;
        buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.setSize(MENU_BUTTONS_WIDTH*2, posY);

        this.addActor(buttonTable);
    }

    public void show(){
        buttonTable.clear();
        buttonTable.setDebug(Main.debug);
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
