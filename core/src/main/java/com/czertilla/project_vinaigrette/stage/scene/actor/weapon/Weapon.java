package com.czertilla.project_vinaigrette.stage.scene.actor.weapon;

import com.badlogic.gdx.math.Vector3;

public interface Weapon{
    void mainAttack(Vector3 destination);
    void secondaryAttack(Vector3 destination);
    void reload();
    void update(float delta);
}
