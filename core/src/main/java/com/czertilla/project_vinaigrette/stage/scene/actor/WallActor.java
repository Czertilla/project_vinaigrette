package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.czertilla.project_vinaigrette.utils.Movable;

public class WallActor extends BaseActor{
    static final Texture empty = new Texture("ui/empty.png");
    public WallActor(float width, float height) {
        super(new TextureRegion(empty));
        setWidth(width);
        setHeight(height);
    }
}
