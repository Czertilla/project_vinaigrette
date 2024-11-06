package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.czertilla.project_vinaigrette.utils.C;
import com.czertilla.project_vinaigrette.utils.Movable;

public class EnemyActor extends BaseActor implements Movable {
    protected float hp;
    private final Vector3
        velocity,
        control,
        acceleration;

    private float friction;

    public EnemyActor(TextureRegion region, float hp){
        super(region);
        this.hp= hp;
        velocity = new Vector3();
        acceleration = new Vector3();
        control = new Vector3();
        friction = C.PLAYER_FRICTION;
    }
    public void getDamage(float dmg){
        hp-=dmg;
        System.out.println("get damaged");
        if (hp<=0) {
//            System.out.println("is dead");
            setColor(Color.RED);
        }

    }

    @Override
    void processCollision(Actor other) {
        super.processCollision(other);
        if (other instanceof PlayerActor player){
            player.damage(0);
        }
    }

    @Override
    public void act(float delta) {
        update(delta);
        super.act(delta);
    }

    @Override
    public void setVelocity(Vector3 velocity) {
        this.control.set(velocity);
    }

    public void addImpulse(Vector3 velocity){
        this.velocity.mulAdd(velocity, (float) 1 /80);
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
        if (hp <= 0) control.setZero();
        float frictionForce = friction * C.G;
        int f = 0;
        velocity.setLength(Math.max(0, velocity.len()-frictionForce*delta));
        if (velocity.len() <= C.PLAYER_MAX_SPEED)
            velocity.mulAdd(acceleration, delta);
        moveBy((control.x + velocity.x) * delta, (control.y + velocity.y) * delta);
    }
}
