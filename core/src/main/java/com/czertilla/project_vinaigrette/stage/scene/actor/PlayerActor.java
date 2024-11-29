package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Null;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.Weapon;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm.FireArm;
import com.czertilla.project_vinaigrette.utils.Ammo;
import com.czertilla.project_vinaigrette.utils.C;
import com.czertilla.project_vinaigrette.utils.Movable;

public class PlayerActor extends BaseActor implements Movable {
    private Ammo ammo;
    public boolean
        isInteract = false,
        isAttack = false;
    private Weapon weapon;
    private float dmgTime = 0;
    private float velMod = 1f;
    private final Vector3
        velocity,
        acceleration;
    private float maxSpeed;
    private float friction = 35f;

    public PlayerActor(TextureRegion region) {
        super(region,new TextureAtlas("ui/Player.atlas"));
        ammo = new Ammo(100,1000,100);
        maxSpeed = C.PLAYER_MAX_SPEED;
        velocity = new Vector3();
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
        update(delta);
        super.act(delta);
        if (dmgTime <= 0) {
            dmgTime=0;
            setColor(Color.WHITE);
        } else {
            dmgTime-=delta;
        }
        if (isAttack) attack();
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

    public void interact() {
        isInteract = true;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }

    public void handGun(@Null Weapon weapon) {
        if (isInteract) {
            setWeapon(weapon);
            isInteract = false;
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

    public void updateAnimation(){
        String currStateName = animationHandler.stateName;

        Vector3 screenCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 destination = getStage().getViewport().unproject(screenCoords).sub(getCenter());
        String nextState = getStateName(destination);
        if (!nextState.equals(currStateName)) animationHandler.setAnimation(nextState, 6);
        animationHandler.setFrameDuration(
            velocity.isZero() ? C.DEFAULT_PLAYER_FRAME_DUR :
                C.DEFAULT_PLAYER_FRAME_DUR / velocity.len() * maxSpeed
        );
        Vector2
            dest2 = new Vector2(destination.x, destination.y),
            vels2 = new Vector2(velocity.x, velocity.y);
        float angle = vels2.angleDeg(dest2);
        float marg = C.REVERSE_ANIMATION_ANGLE;
        animationHandler.animation.setPlayMode(
            angle > 180 - marg && angle < 180 + marg ? Animation.PlayMode.LOOP_REVERSED
                : Animation.PlayMode.LOOP
        );
    }

    private String getStateName(Vector3 destination) {
        String nextState = C.State.DOWN;
        if (!destination.isZero()) {
            if (destination.x >= 0){
                if (destination.y > destination.x)
                    nextState = C.State.UP;
                else if (destination.y > -destination.x)
                    nextState = C.State.RIGHT;
                else
                    nextState = C.State.DOWN;
            }
            else {
                if (destination.y < destination.x)
                    nextState = C.State.DOWN;
                else if (destination.y < -destination.x)
                    nextState = C.State.LEFT;
                else
                    nextState = C.State.UP;
            }
        }
        if (velocity.isZero()) nextState = C.State.IDLE_PREFIX + nextState;
        return nextState;
    }

    @Override
    void processCollision(Actor other) {
        super.processCollision(other);
        if (other instanceof Weapon weaponActor){
            if (this.weapon != weaponActor) {
                handGun(weaponActor);
            }
        }
    }

    @Override
    public void setVelocity(Vector3 velocity) {
        this.velocity.set(velocity.scl(maxSpeed * velMod));
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
        velocity.setLength(Math.max(0, velocity.len()-frictionForce*delta));
        updateAnimation();
        velocity.mulAdd(acceleration, delta);
        moveBy(velocity.x * delta, velocity.y * delta);
    }

    public void onReload() {
        if (weapon != null)
            weapon.reload();
    }

    public void onCntrl(boolean b) {
        if (b){
            velMod = 0.5f;
        }
        else velMod = 1;
    }
}
