package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.czertilla.project_vinaigrette.utils.C;

public class PlayerActor extends BaseActor {

    private float dmgTime = 0;

    public PlayerActor(TextureRegion region) {
        super(region);
    }

    public void damage(float dmg){
        if (dmgTime > 0) return;
        dmgTime = C.DAMAGE_COOLDOWN;
        setColor(Color.RED);
    }

    public void act(float delta) {
        super.act(delta);
        dmgTime -= delta;
        if (dmgTime <= 0)
            setColor(1, 1, 1, 1);
    }

    public void handleCollision(PlayerActor other) {
//        TODO replace sout by logging
        System.out.println("Collision Detected!");
    }
}
