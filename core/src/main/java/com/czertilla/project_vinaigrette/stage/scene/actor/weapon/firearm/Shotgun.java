package com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.czertilla.project_vinaigrette.stage.scene.actor.BulletActor;
import com.czertilla.project_vinaigrette.utils.R;

public class Shotgun extends FireArm{
    private boolean isReloading;
    private float reloadTime;
    @Override
    public void mainAttack(Vector3 destination) {
        if (!isLoaded) {
            reload();
            return;
        }
        super.mainAttack();
        isReloading = false;
        Vector3
            start = getCenter(),
            delta = destination.cpy().sub(start);
        delta.scl(getWidth()/delta.len()/2);
        start.add(delta);
        Stage stage = getStage();
        float
            spread = stats.pelletSpread(),
            distance = destination.dst(start);
        Vector3 recoilVelocity = new Vector3();
        for (int i=0; i < stats.pelletNum(); i++){
            Vector3 dest = destination.cpy();
            BulletActor bullet = new BulletActor(
                start,
                dest.add(
                    MathUtils.random(-spread, spread)*distance,
                    MathUtils.random(-spread, spread)*distance,
                    0
                ),
                super.stats.bulletSpeed()
            );
            stage.addActor(bullet);
            recoilVelocity.mulAdd(bullet.getVelocity(), -super.stats.bulletWeight());
        }
        recoil(recoilVelocity);
    }

    @Override
    public void secondaryAttack(Vector3 destination) {

    }

    @Override
    public void reload() {
        isReloading = !isReloading;
        if (isReloading) reloadTime = super.stats.reloadTime();
        else if (!isCocked) cock();
    }


    protected record Stats(
        int pelletNum,
        float pelletSpread
    ){};
    Stats stats;
    public Shotgun(TextureRegion region, String type) {
        super(region, type);
        setStats();
    }

    void setStats() {
        super.setStats();
        stats = new Stats(
            Integer.parseInt(bundle.get(R.id.PELLET_NUM)),
            Float.parseFloat(bundle.get(R.id.PELLET_SPREAD))
        );
    }

    public void update(float delta) {
        super.update(delta);
        if (!isReloading) return;
        reloadTime -= delta;
        if (reloadTime > 0) return;
        if (magazine < super.stats.magazineSize() && ammo.getShotgunAmmo() > 0){
            magazine += ammo.getShotgunAmmo((int) ( -reloadTime / super.stats.reloadTime()) + 1);
        }
        else {
            isReloading = false;
            cock();
        }
        if (isReloading) reloadTime += super.stats.reloadTime();
    }
}
