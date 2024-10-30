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
import com.czertilla.project_vinaigrette.utils.Movable;

public class PlayerActor extends BaseActor implements Movable {
    private Ammo ammo;
    public boolean pressE=false;
    private Weapon weapon;
    private float dmgTime = 0;
    private final Vector3
        velocity,
        inputVelocity,
        acceleration;
    private float maxSpeed;
    private float friction = 35f;

    public PlayerActor(TextureRegion region) {
        super(region);
        ammo = new Ammo(100,100,100);
        maxSpeed = C.PLAYER_MAX_SPEED;
        velocity = new Vector3();
        inputVelocity = new Vector3();
        acceleration = new Vector3();
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
        update(delta);
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

    @Override
    public void setVelocity(Vector3 velocity) {
        inputVelocity.set(velocity.scl(maxSpeed));
    }

    @Override
    public void moveTo(Vector3 destination) {
        setPosition(destination.x, destination.y);
    }

    @Override
    public void moveOn(Vector3 moving) {
        moveBy(moving.x, moving.y);
    }

    @Override
    public void setAcceleration(Vector3 acceleration) {
        this.acceleration.set(acceleration);
    }

    @Override
    public void update(float delta) {
        float frictionForce = friction * C.G;
        int f = 0;
        if (velocity.isZero() && !acceleration.isZero()){
            acceleration.setLength(Math.max(0, acceleration.len() - frictionForce));
            f = 1;
        }
        else if (!velocity.isZero(C.FRICTION_BLOCK_MARGIN*delta*frictionForce))
            acceleration.mulAdd(velocity, -frictionForce / velocity.len());
        else
            velocity.setZero();
        velocity.mulAdd(acceleration, delta);
        Vector3 scopeVelocity = velocity.cpy().add(inputVelocity);
        moveBy(scopeVelocity.x * delta, scopeVelocity.y * delta);
        if (getX()!=500)
            System.out.print(f+""+scopeVelocity+" x:"+getX()+" y:"+getY()+"\r");
    }

    public void onReload() {
        if (weapon != null)
            weapon.reload();
    }
}
