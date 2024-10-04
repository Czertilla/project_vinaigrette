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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.czertilla.project_vinaigrette.screen.utils.ButtonContainer;
// TODO replace all strings with variables alt

public class MainMenu extends ScreenAdapter {
    private final Texture background;
    private final Stage stage;
//    private final Music backgroundMusic;
//    private final Sound clickSound;
//    private final TextButton PersonnageButton;

    private final Drawable buttonDrawable;

    private TextButton.TextButtonStyle textButtonStyle;
    private final ButtonContainer buttonContainer;

    static final float
        FRAMES_LOCK = 30f,
        MENU_BUTTONS_WIDTH = 500f,
        MENU_BUTTONS_HEIGHT = 150f;
    static final int
        FONT_SIZE = 40,
        MENU_BUTTONS_ROWS = 1;

    public MainMenu(final Game game) {
        background = new Texture("mainMenuBackground.png");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Skin skin = new Skin();
        buttonDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture("buttonDrawableRegion.png"))
        );
        buttonContainer = new ButtonContainer(
            Gdx.graphics.getWidth() / 2f - MENU_BUTTONS_WIDTH,
            Gdx.graphics.getHeight() / 1.5f,
            MENU_BUTTONS_WIDTH,
            MENU_BUTTONS_HEIGHT
        );

        createTextButtonStyle();

        if (true) {// TODO implement saves detections
            createContinueButton();
        }
    }


    private void initButton(Button button){
        stage.addActor(button);
        buttonContainer.add(button);
    }

    private void createSettingsButton() {
        TextButton button = new TextButton("Settings", textButtonStyle);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                TODO implement Continue Button click trigger
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
        generator.dispose();
        textButtonStyle.font = font;
        textButtonStyle.up = buttonDrawable;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
