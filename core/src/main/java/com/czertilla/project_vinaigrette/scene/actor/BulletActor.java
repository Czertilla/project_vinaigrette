package com.czertilla.project_vinaigrette.scene.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BulletActor extends BaseActor { // Полигон для коллизии
    private float speed;
    private float direction; // Угол направления пули

    public BulletActor(TextureRegion texture, float startX, float startY, float direction, float speed) {
        super(texture);
        this.direction = direction;
        this.speed = speed;

        // Устанавливаем размеры пули на основе текстуры
        setSize(region.getRegionWidth(), region.getRegionHeight());

        // Устанавливаем начальную позицию пули
        setPosition(startX - getWidth() / 2, startY - getHeight() / 2); // центрирование пули

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

    @Override
    public void act(float delta) {
        super.act(delta);

        // Движение пули
        float velocityX = MathUtils.cosDeg(direction) * speed;
        float velocityY = MathUtils.sinDeg(direction) * speed;
        moveBy(velocityX * delta, velocityY * delta);

        // Обновляем позицию полигона при движении
        boundingBox.setPosition(getX(), getY());
        boundingBox.setRotation(getRotation());

        // Если пуля выходит за пределы экрана, удаляем её
        if (getX() < -getWidth() || getX() > getStage().getWidth() || getY() < -getHeight() || getY() > getStage().getHeight()) {
            remove(); // Удаляем пулю из сцены
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    // Возвращает полигон для проверки столкновений
}

