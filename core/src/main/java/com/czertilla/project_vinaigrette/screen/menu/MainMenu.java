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
// TODO replace all strings with variables alt

public class MainMenu extends ScreenAdapter {
    static private MainMenu instance;
    private final Game game;
    private final Texture background;
    private final Stage stage;
//    private final Music backgroundMusic;
//    private final Sound clickSound;

    private final Drawable buttonDrawable;

    private TextButton.TextButtonStyle textButtonStyle;
    private final Table buttonTable;

    static boolean debug = true;
    static final float
        FRAMES_LOCK = 30f,
        MENU_BUTTONS_WIDTH = 500f,
        MENU_BUTTONS_HEIGHT = 150f;
    static final int
        FONT_SIZE = 40,
        MENU_BUTTONS_ROWS = 1;

    public static MainMenu getInstance(final Game game) {
        if (instance == null)
            instance = new MainMenu(game);
        return instance;
    }
    /** call this, only when you sure, that instance already exists */
    public static MainMenu getInstance(){
        return instance;
    }

    public MainMenu(final Game game) {
        this.game = game;

        background = new Texture("mainMenuBackground.png");

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

        if (false) {// TODO implement saves detections
            createContinueButton();
        }
        createSettingsButton();

    }

    private void initButton(Button button){
        button.setDebug(debug);
        buttonTable.add(button)
            .width(MENU_BUTTONS_WIDTH)
            .height(MENU_BUTTONS_HEIGHT)
            .center();
        buttonTable.row();
    }

    private void createSettingsButton() {
        TextButton button = new TextButton("Settings", textButtonStyle);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsMenu(game, MainMenu.this));
                dispose();
            }
        });

        initButton(button);
    }

    private void createContinueButton() {
        TextButton button = new TextButton("Continue", textButtonStyle);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                TODO implement Continue Button click trigger
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
        background.dispose();
        stage.dispose();
//        backgroundMusic.dispose();
//        clickSound.dispose();
    }
}
