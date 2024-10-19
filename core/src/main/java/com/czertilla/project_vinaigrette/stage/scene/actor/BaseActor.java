package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
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

    public Polygon getBoundingBox() {
        return boundingBox;
    }

}
