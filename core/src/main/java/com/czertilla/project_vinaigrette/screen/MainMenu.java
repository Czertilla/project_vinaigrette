package com.czertilla.project_vinaigrette.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MainMenu extends ScreenAdapter {
    private final Texture background;
    private final Stage stage;
//    private final Music backgroundMusic;
//    private final Sound clickSound;
//    private final TextButton PersonnageButton;

    private final Drawable buttonDrawable;

    private TextButton.TextButtonStyle textButtonStyle;
    static final float FRAMES_LOCK = 30f;
    static final int FONT_SIZE = 40;

    public MainMenu(final Game game) {
        background = new Texture("mainMenuBackground.png");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Skin skin = new Skin();
        buttonDrawable = new TextureRegionDrawable(
            new TextureRegion(new Texture("buttonDrawableRegion"))
        );

        createTextButtonStyle();


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
