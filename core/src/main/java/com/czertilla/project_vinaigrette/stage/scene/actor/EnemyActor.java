package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyActor extends BaseActor{
    protected float hp;
    public EnemyActor(TextureRegion region, float hp){
        super(region);
        this.hp= hp;
    }
    public void getDamage(float dmg){
        hp-=dmg;
        System.out.print("get damaged");
        if (hp<=0) {
            System.out.print("is dead");
            setColor(Color.RED);
        }

    }
}
