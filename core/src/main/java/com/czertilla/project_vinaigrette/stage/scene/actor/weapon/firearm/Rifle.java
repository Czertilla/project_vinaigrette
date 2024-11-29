package com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.czertilla.project_vinaigrette.stage.scene.actor.BulletActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.NoiseActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.Weapon;
import com.czertilla.project_vinaigrette.utils.R;

public class Rifle extends FireArm implements Weapon {
    private boolean isReloading;
    private float reloadTime;

    static final Sound
        cockSound = Gdx.audio.newSound(Gdx.files.internal(R.path.SHOTGUN_COCK_SOUND)),
        emptySound = Gdx.audio.newSound(Gdx.files.internal(R.path.SHOTGUN_EMPTY_SOUND)),
        loadingSound = Gdx.audio.newSound(Gdx.files.internal(R.path.SHOTGUN_LOADING_SOUND)),
        shotSound = Gdx.audio.newSound(Gdx.files.internal(R.path.RIFLE_SHOT_SOUND));
    @Override
    public void mainAttack(Vector3 destination) {
        isReleased = false;
        if (!isLoaded && isCocked) {
            emptySound.play();
            reload();
        }
        if (!isCocked) return;
        isCocked = false;
        if (!isLoaded) return;
        super.mainAttack();
        isReloading = false;
        Vector3
            start = getCenter(),
            delta = destination.cpy().sub(start);
        delta.scl(getWidth()/delta.len()/2);
        start.add(delta);
        Stage stage = getStage();
        Vector3 recoilVelocity = new Vector3();
        getStage().addActor(new NoiseActor(5000, this));
        shotSound.play();
        BulletActor bullet = new BulletActor(
            start,
            destination,
            super.stats.bulletStats()
        );
        stage.addActor(bullet);
        recoilVelocity.mulAdd(bullet.getVelocity(), -super.stats.bulletStats().bulletWeight());
        recoil(recoilVelocity);
    }

    @Override
    public void secondaryAttack(Vector3 destination) {

    }

    @Override
    void cock() {
        super.cock();
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
    public Rifle(TextureRegion region, String type) {
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

    @Override
    void load() {
        super.load();
        cock();
    }

    void onReloading(float delta){
        reloadTime -= delta;
        if (reloadTime > 0) return;
        if (magazine < super.stats.magazineSize() && ammo.getShotgunAmmo() > 0){
            magazine += ammo.getRifleAmmo(super.stats.magazineSize() - magazine);
            loadingSound.play();
        }
        else {
            isReloading = false;
            cockSound.play();
        }
    }

    int getAmmo(){
        return ammo.getRifleAmmo();
    }

    public void update(float delta) {
        super.update(delta);
        if (isReloading) onReloading(delta);
        isReleased = true;
    }
}
