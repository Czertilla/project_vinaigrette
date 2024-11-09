package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class NoiseActor extends BaseActor{

    private final float maxRadius;
    private float currentRadius;

    static final Texture empty = new Texture("ui/empty.png");
    public NoiseActor(float loud, BaseActor source) {
        super(new TextureRegion(empty));
        maxRadius = loud;
        currentRadius = 0;
        Vector3 center = source.getCenter();
        setPosition(center.x, center.y);
        setSize(0, 0);
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        super.drawDebugBounds(shapes);
        shapes.setColor(Color.CORAL);
        Vector3 center = getCenter();
        shapes.circle(center.x, center.y, currentRadius);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        currentRadius += 3200 * delta;
        if (currentRadius >= maxRadius){
            remove();
        }
    }
}
