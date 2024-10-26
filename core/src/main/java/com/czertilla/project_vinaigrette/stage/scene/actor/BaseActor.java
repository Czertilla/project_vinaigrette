package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {
    protected TextureRegion region;
    public Polygon boundingBox;
    public boolean collidesWith(BaseActor other) {
        return Intersector.overlapConvexPolygons(this.getBoundingBox(), other.getBoundingBox());
    }
    public BaseActor(TextureRegion region){
        this.region = region;
        super.setSize(region.getRegionWidth(), region.getRegionHeight());

    }

    public Vector3 getCenter(){
        return new Vector3(
            getX()+getWidth()/2,
            getY()+getHeight()/2,
            0
        );
    }

    public void rotateTowards(float x, float y) {
        // Находим центр актора
        Vector3 center = getCenter();

        // Рассчитываем угол между центром актора и курсором
        float angle = (float) Math.toDegrees(Math.atan2(y - center.y, x - center.x));
        setRotation(angle);
    }

    public Polygon getBoundingBox() {
        return boundingBox;
    }

}
