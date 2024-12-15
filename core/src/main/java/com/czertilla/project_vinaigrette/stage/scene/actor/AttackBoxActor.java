package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AttackBoxActor extends BaseActor {
    private float damage;

    public AttackBoxActor(TextureRegion region, float width, float height) {
        super(region);
        this.damage = 10;
        setSize(width, height);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setPositionRelativeToWeapon(float weaponX, float weaponY, float offsetX, float offsetY) {
        // Например, ставим attackBox впереди оружия:
        setPosition(weaponX + offsetX, weaponY + offsetY);
    }
    void processCollision(Actor other) {

        if (other instanceof EnemyActor enemyActor && isVisible()){
            enemyActor.getDamage(damage);
        }
    }
}
