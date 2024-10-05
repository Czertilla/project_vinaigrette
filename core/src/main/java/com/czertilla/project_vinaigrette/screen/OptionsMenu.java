package com.czertilla.project_vinaigrette.screen;

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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class OptionsMenu extends ScreenAdapter {
    private Class<? extends ScreenAdapter> lastAdapter;
    static private OptionsMenu instance;
    private final Game game;
//    private final Texture background;
    private final Stage stage;
//    private final Music backgroundMusic;
//    private final Sound clickSound;

    private final Drawable buttonDrawable;

    private TextButton.TextButtonStyle textButtonStyle;
    private final Table buttonTable;

    static final float
        FRAMES_LOCK = 30f,
        MENU_BUTTONS_WIDTH = 500f,
        MENU_BUTTONS_HEIGHT = 150f;
    static final int
        FONT_SIZE = 40,
        MENU_BUTTONS_ROWS = 1;

    public static OptionsMenu getInstance(final Game game, ScreenAdapter lastAdapter) {
        if (instance == null)
            instance = new OptionsMenu(game, lastAdapter);
        return instance;
    }
    /** call this, only when you sure, that instance already exists */
    public static OptionsMenu getInstance(ScreenAdapter lastAdapter){
        instance.lastAdapter = lastAdapter.getClass();
        return instance;
    }

    public OptionsMenu(final Game game, ScreenAdapter lastAdapter) {
        this.game = game;
        this.lastAdapter = lastAdapter.getClass();

//        background = new Texture("optionsMenuBackground.png");

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

    @Override
    public void show(){
        buttonTable.clear();


        buttonTable.setDebug(MainMenu.debug);

        createTextButtonStyle();

        createDebugButton();
        createBackButton();
    }

    private void initButton(Button button){
        button.setDebug(MainMenu.debug);
        buttonTable.add(button)
            .width(MENU_BUTTONS_WIDTH)
            .height(MENU_BUTTONS_HEIGHT)
            .center();
        buttonTable.row();
    }

    private void createDebugButton() {
        TextButton button = new TextButton("Debug", textButtonStyle);


        button.setChecked(MainMenu.debug);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenu.debug = button.isChecked();
                show();
            }
        });

        initButton(button);
    }

    private void createBackButton() {
        TextButton button = new TextButton("back", textButtonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenAdapter adapter;
                if (lastAdapter.equals(MainMenu.class)) {
                    adapter = new MainMenu(game);
                }
                else
                    adapter = new ScreenAdapter();
                game.setScreen(adapter);
                dispose();
            }
        });

        initButton(button);
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
