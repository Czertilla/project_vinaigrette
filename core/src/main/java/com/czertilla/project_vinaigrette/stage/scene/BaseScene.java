package com.czertilla.project_vinaigrette.stage.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.czertilla.project_vinaigrette.Main;
import com.czertilla.project_vinaigrette.screen.game.MainGame;
import com.czertilla.project_vinaigrette.stage.BaseStage;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;
import com.czertilla.project_vinaigrette.handler.InputHandler;
import com.czertilla.project_vinaigrette.utils.C;

public class BaseScene extends BaseStage {
    protected PlayerActor actor;
    final InputHandler inputHandler;

    protected MainGame screen;
    public BaseScene(MainGame screen) {
        super();
        this.screen = screen;
        // Устанавливаем FitViewport с начальной шириной и высотой
        setViewport(new ExtendViewport(C.MIN_WORLD_WIDTH, C.MIN_WORLD_HEIGHT));

        // Загрузка текстуры
//        TODO replace string filepath by variable from R.path class in utils. use "paths" bundle
        Texture texture = new Texture(Gdx.files.internal("ui/img.png"));
        TextureRegion region = new TextureRegion(texture); // Создаем TextureRegion
        actor = new PlayerActor(region);
//        TODO replace numeric constant
        actor.setPosition(200, 200);
        actor.setSize(200, 400);// Устанавливаем актера в центре экрана
        actor.toFront();
        addActor(actor); // Добавляем актера в сцену
        actor.toFront();

        inputHandler = new InputHandler(actor,this);
        Gdx.input.setInputProcessor(inputHandler);
    }

    public void show(){
        setDebugAll(Main.debug);
    }

    @Override
    public void onEscape() {
        screen.onPause();
    }

    @Override
    public void onBack() {

    }

    public Vector3 getMousePos(){
        return getViewport().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        inputHandler.update();
    }

    public void resize(int width, int height) {
        // Обновляем viewport при изменении размеров окна
        getViewport().update(width, height, true);
    }

}
