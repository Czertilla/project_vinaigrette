package com.czertilla.project_vinaigrette.utils;

import com.badlogic.gdx.math.Vector3;

public interface Movable {
    void setVelocity(Vector3 velocity);
    void moveTo(Vector3 destination);
    void moveOn(Vector3 moving);
    void setAcceleration(Vector3 acceleration);
    void update(float delta);
}
