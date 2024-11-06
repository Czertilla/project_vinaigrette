package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;

public class WallActor extends BaseActor{
    static final Texture empty = new Texture("ui/empty.png");
    public WallActor(float width, float height) {
        super(new TextureRegion(empty));
        setSize(width, height);
    }

    public void move(BaseActor other) {
        Vector3 center = getCenter();
        float
            cx = center.x,
            cy = center.y;
        Vector3 direction = other.getCenter().sub(center).setLength(1);
        float dst = Float.POSITIVE_INFINITY;
        Vector3 axis = new Vector3(0, 0, 1);
        Vector3 dir = new Vector3(1, 0, 0).rotate(
            axis,
            getRotation()
        );
        Vector3 result = new Vector3(0, 0, 0);
        for (int i = 0; i < 4; i++, dir.rotate(axis, 90)){
            float mlp = ((i % 2 == 1) ? getHeight() : getWidth()) / 2;
            float len = dir.cpy().sub(direction).len() * mlp;
            if (len < dst) {
                result.set(dir);
                dst = len;
            }
        }
        result.setLength(1);
        do {
            other.moveBy(result.x, result.y);
            other.updateBoundingBox();
        } while (Intersector.overlapConvexPolygons(
            this.getBoundingBox(),
            other.getBoundingBox()
        ));
    }
}
