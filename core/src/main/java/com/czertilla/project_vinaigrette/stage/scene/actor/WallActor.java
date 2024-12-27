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
        int wallVerticesCount = getBoundingBox().getVertexCount();
        float
            cx = wallCenter.x,
            cy = wallCenter.y;
        Vector3 otherCenter = new Vector3(
            other.getBoundingBox().getOriginX(),
            other.getBoundingBox().getOriginY(),
            0);
        Vector3 otherDirection = otherCenter.cpy().sub(wallCenter).setLength(1);
        float otherDirAngle = MathUtils.atan2(otherDirection.y, otherDirection.x);
        Vector3 result = new Vector3(1, 0, 0);
        for (int i=0; i <= wallVerticesCount; i += 2){
            float
                x1 = wallWorldVertices[i % wallVerticesCount],
                y1 = wallWorldVertices[(i + 1) % wallVerticesCount],
                x2 = wallWorldVertices[(i + 2) % wallVerticesCount],
                y2 = wallWorldVertices[(i + 3) % wallVerticesCount];
            float angle1 = MathUtils.atan2(y1 - cy, x1 - cx);
            float angle2 = MathUtils.atan2(y2 - cy, x2 - cx);
            if (angle1 < otherDirAngle && otherDirAngle <= angle2) {
                float
                    dx = x2 - x1,
                    dy = y2 - y1;
                Vector2
                    p = new Vector2(-dy, dx),
                    ac = new Vector2(cx - x1, cy - y1);
                result.set(p.x, p.y, 0);
                if (p.dot(ac) < 0)
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
