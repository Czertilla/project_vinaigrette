package com.czertilla.project_vinaigrette.stage.scene.actor;

import static com.badlogic.gdx.scenes.scene2d.utils.ScissorStack.getViewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Null;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.Weapon;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm.FireArm;
import com.czertilla.project_vinaigrette.utils.Ammo;
import com.czertilla.project_vinaigrette.utils.C;

public class PlayerActor extends BaseActor{
    private Ammo ammo;
    public boolean pressE=false;
    private Weapon weapon;
    private float dmgTime = 0;
    public PlayerActor(TextureRegion region) {
        super(region);
        ammo = new Ammo(100,100,100);
    }

    public void setWeapon(Weapon weapon) {
        if(weapon instanceof FireArm) {
            ((FireArm) weapon).setAmmo(null);
        }
        this.weapon = weapon;
        if(weapon instanceof FireArm) {
            ((FireArm) weapon).setAmmo(ammo);
        }
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (weapon!=null) {
            Vector3 position = getCenter();
            ((BaseActor) weapon).setPosition(position.x, position.y);
            weapon.update(delta);
        }


    }

    public void damage(float dmg){
        if (dmgTime > 0) return;
        dmgTime = C.DAMAGE_COOLDOWN;
        setColor(Color.RED);
    }

    public void pressE() {
        pressE=true;
    }
    public void handGun(@Null Weapon weapon) {
        if (pressE) {
            setWeapon(weapon);
            pressE = false;
        }
    }


    public void attack() {
        if(weapon==null) return;
        Vector3 screenCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 destination = getStage().getViewport().unproject(screenCoords);
        weapon.mainAttack(destination);
    }
    public boolean isHandWeapon(Weapon other){
        return weapon==other;
    }
}
