package com.czertilla.project_vinaigrette.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;

public class ScrollPane extends com.badlogic.gdx.scenes.scene2d.ui.ScrollPane {

    private class Listener extends InputListener {
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            getStage().setScrollFocus(ScrollPane.this);
        }

        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            getStage().setScrollFocus(null);
        }
    }
    public ScrollPane (@Null Actor actor){
        super(actor);
        addListener(new Listener());
    }
    public ScrollPane (@Null Actor actor, Skin skin){
        super(actor, skin);
        addListener(new Listener());
    }
    public ScrollPane (@Null Actor actor, Skin skin, String styleName){
        super(actor, skin, styleName);
        addListener(new Listener());
    }
    public ScrollPane (@Null Actor actor, ScrollPaneStyle style){
        super(actor, style);
        addListener(new Listener());
    }
}
