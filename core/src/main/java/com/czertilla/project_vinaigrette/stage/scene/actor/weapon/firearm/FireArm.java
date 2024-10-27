package com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.czertilla.project_vinaigrette.stage.scene.actor.PlayerActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.Weapon;
import com.czertilla.project_vinaigrette.utils.Ammo;
import com.czertilla.project_vinaigrette.utils.C;
import com.czertilla.project_vinaigrette.utils.R;
import com.czertilla.project_vinaigrette.utils.StatImport;

public abstract class FireArm extends PlayerActor implements Weapon {

    protected record Stats(
        float damage,
        float bulletSpeed,
        float bulletWeight,
        int magazineSize,
        float firingRange,
        float fireRate,
        float reloadTime
    ){};
    final I18NBundle bundle;
    Ammo ammo;
    Stats stats;
    float cooldown = 0;
    Vector3 recoilVelocity;
    int magazine;
    boolean
        isLoaded,
        isCocked;

    public FireArm(TextureRegion region, String type) {
        super(region);
        bundle = StatImport.getFireArm(type);
        isLoaded = false;
        isCocked = false;
        recoilVelocity = new Vector3();
        magazine = 0;
    }

    public void setAmmo(Ammo ammo) {
        this.ammo = ammo;
    }

    void setStats(){
        stats = new Stats(
            Float.parseFloat(bundle.get(R.id.DAMAGE)),
            Float.parseFloat(bundle.get(R.id.BULLET_SPEED)),
            Float.parseFloat(bundle.get(R.id.BULLET_WEIGHT)),
            Integer.parseInt(bundle.get(R.id.MAGAZINE_SIZE)),
            Float.parseFloat(bundle.get(R.id.FIRING_RANGE)),
            60f/Float.parseFloat(bundle.get(R.id.FIRE_RATE)),
            Float.parseFloat(bundle.get(R.id.RELOAD_TIME))
        );
    }

    protected void mainAttack() {
        if (isLoaded){
            isLoaded = false;
            cooldown = stats.fireRate;
        }
    }

    void cock(){
        isCocked = true;
    }

    void recoil(Vector3 velocity){
        recoilVelocity.mulAdd(velocity, 1/ C.CAM_WEIGHT);
    }

    @Override
    public void update(float delta){
        if (!isLoaded && isCocked && magazine > 0 && cooldown <= 0) {
            isLoaded = true;
            magazine -= 1;
        }
        else if (cooldown > 0){
            cooldown -= delta;
        }
        else if (isCocked && !isLoaded && magazine <= 0){
            isCocked = false;
        }
        float len = recoilVelocity.len();
        len -= C.RECOIL_DRAG * delta;
        if (len <= 0)
            recoilVelocity.set(0, 0, 0);
        else
            recoilVelocity.scl(len/recoilVelocity.len());
        getStage().getCamera().position.add(recoilVelocity.cpy().scl(delta));
        System.out.print((isLoaded?"+":"-")+"  "+magazine+"/"+ammo.getShotgunAmmo()+" --"+recoilVelocity.len()+'\r');
    }
}
