package com.czertilla.project_vinaigrette.screen.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BaseMenu extends ScreenAdapter {
    static protected BaseMenu instance;
    protected final Game game;
    protected Texture background;
    protected final Stage stage;
//    private final Music backgroundMusic;
//    private final Sound clickSound;

    protected final Drawable buttonDrawable;

    private TextButton.TextButtonStyle textButtonStyle;
    protected final Table buttonTable;

    static public boolean debug = true;
    static protected final float
        FRAMES_LOCK = 30f,
        MENU_BUTTONS_WIDTH = 500f,
        MENU_BUTTONS_HEIGHT = 150f;
    static  protected final int
        FONT_SIZE = 40,
        MENU_BUTTONS_ROWS = 1;

    public static BaseMenu getInstance(final Game game) {
        if (instance == null)
            instance = new BaseMenu(game);
        return instance;
    }
    /** call this, only when you sure, that instance already exists */
    public static BaseMenu getInstance(){
        return instance;
    }

    public BaseMenu(final Game game) {
        this.game = game;

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin();
        buttonDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture("buttonDrawableRegion.png"))
        );
        float
            posX = Gdx.graphics.getWidth() / 2f - MENU_BUTTONS_WIDTH,
            posY = Gdx.graphics.getHeight() / 1.5f;
        buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.setSize(MENU_BUTTONS_WIDTH*2, posY);

        stage.addActor(buttonTable);
    }

    public void show(){
        buttonTable.clear();
        buttonTable.setDebug(BaseMenu.debug);

        createTextButtonStyle();
    }

    protected void initButton(Button button){
        button.setDebug(debug);
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

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        stage.getBatch().begin();
//        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / FRAMES_LOCK));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
//        background.dispose();
        stage.dispose();
//        backgroundMusic.dispose();
//        clickSound.dispose();
    }
}
