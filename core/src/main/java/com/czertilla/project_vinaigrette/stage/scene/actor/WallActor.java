package com.czertilla.project_vinaigrette.stage.scene.actor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.czertilla.project_vinaigrette.stage.scene.actor.BaseActor;

import java.util.HashSet;
import java.util.Set;

public class WallActor extends BaseActor {
    public static final Set<WallActor> walls = new HashSet<>();
    static final Texture empty = new Texture("ui/empty.png");
    public WallActor(float width, float height) {
        super(new TextureRegion(empty));
        setSize(width, height);
        walls.add(this);
    }

    public WallActor(float ax, float ay, float bx, float by){
        super(new TextureRegion(empty));
        setPosition(ax, ay);
        setSize(bx, by);
        walls.add(this);

    }

    public void move(BaseActor other) {
        Vector3 wallCenter = getCenter();
        float[] wallWorldVertices = getBoundingBox().getTransformedVertices();
        float
            cx = wallCenter.x,
            cy = wallCenter.y;
        Vector3 otherCenter = new Vector3(
            other.getBoundingBox().getX() +
                other.getBoundingBox().getBoundingRectangle().getWidth() / 2,
            other.getBoundingBox().getY() +
                other.getBoundingBox().getBoundingRectangle().getHeight() / 2,
            0);
        Vector3 otherDirection = otherCenter.cpy().sub(wallCenter).setLength(1);
        float otherDirAngle = MathUtils.atan2(otherDirection.y, otherDirection.x);
        Vector3 result = new Vector3(1, 0, 0);
        for (int i=0; i <= getBoundingBox().getVertexCount()*2; i += 2){
            float
                x1 = wallWorldVertices[i % wallWorldVertices.length],
                y1 = wallWorldVertices[(i + 1) % wallWorldVertices.length],
                x2 = wallWorldVertices[(i + 2) % wallWorldVertices.length],
                y2 = wallWorldVertices[(i + 3) % wallWorldVertices.length];
            float angle1 = MathUtils.atan2(y1 - cy, x1 - cx);
            float angle2 = MathUtils.atan2(y2 - cy, x2 - cx);
            float otherAngle;
            if (Math.abs(angle2 - angle1) > MathUtils.PI){
                float added = angle1 + MathUtils.PI * 2;
                angle1 = angle2;
                angle2 = added;
                otherAngle = otherDirAngle + MathUtils.PI * 2 * ((otherDirAngle < 0)?1:0);
            }
            else
                otherAngle = otherDirAngle;
            if (
                angle1 < otherAngle && otherAngle <= angle2
            ) {
                float
                    dx = x2 - x1,
                    dy = y2 - y1;
                Vector2
                    p = new Vector2(-dy, dx),
                    ac = new Vector2(cx - x1, cy - y1);
                result.set(p.x, p.y, 0);
                if (p.dot(ac) > 0)
                    result.scl(-1);
                result.setLength(1);
                break;
            }

        }
        do {
            other.moveBy(result.x, result.y);
            other.updateBoundingBox();
        } while (Intersector.overlapConvexPolygons(
            this.getBoundingBox(),
            other.getBoundingBox()
        ));
    }

    @Override
    public boolean remove() {
        walls.remove(this);
        return super.remove();
    }
}
