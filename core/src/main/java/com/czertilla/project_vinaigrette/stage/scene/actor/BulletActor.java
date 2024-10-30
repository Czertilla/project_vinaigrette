package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.czertilla.project_vinaigrette.stage.scene.actor.weapon.firearm.FireArm;

public class BulletActor extends BaseActor { // Полигон для коллизии

    private final Vector3 velocity;
    private float
        path,
        weight,
        firingRange,
        damage;
    static Texture bulletTexture = new Texture(Gdx.files.internal("ui/bullet.png"));
    static TextureRegion regionbullet = new TextureRegion(bulletTexture);

    public BulletActor(Vector3 start, Vector3 destination, FireArm.BulletStats stats) {
        super(regionbullet);
        path = 0;
        this.weight = stats.bulletWeight();
        this.firingRange = stats.firingRange();
        this.damage = stats.damage();

        // Устанавливаем размеры пули на основе текстуры
        setSize(15, 15);

        // Устанавливаем начальную позицию пули
        setPosition(start.x - getWidth() / 2, start.y); // центрирование пули
        velocity = destination.cpy().sub(start);
        velocity.scl(speed/velocity.len());
        rotateTowards(destination.x, destination.y);
        Vector3 barrel = velocity.cpy().scl((101+getWidth())/stats.bulletSpeed());
        moveBy(barrel.x, barrel.y);

        // Создаем полигон для коллизии (прямоугольник)
        this.boundingBox = new Polygon(new float[]{
            0, 0,                   // нижний левый угол
            getWidth(), 0,           // нижний правый угол
            getWidth(), getHeight(), // верхний правый угол
            0, getHeight()           // верхний левый угол
        });

        // Устанавливаем исходную позицию полигона
        boundingBox.setPosition(getX(), getY());
        boundingBox.setOrigin(getWidth() / 2, getHeight() / 2);
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Vector3 drag = velocity.cpy().scl(delta);
        path += drag.len();
        if (path >= firingRange) {
            remove();
            return;
        }
        moveBy(drag.x, drag.y);

        // Обновляем позицию полигона при движении
        boundingBox.setPosition(getX(), getY());
        boundingBox.setRotation(getRotation());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    // Возвращает полигон для проверки столкновений
}

