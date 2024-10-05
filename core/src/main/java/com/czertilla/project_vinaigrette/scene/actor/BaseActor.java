package com.czertilla.project_vinaigrette.scene.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {
    private TextureRegion region;
    private Polygon boundingBox;

    public BaseActor(TextureRegion region) {
        this.region = region;
        super.setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2); // Устанавливаем точку вращения в центр
        boundingBox = new Polygon(new float[]{
            0, 0,  // нижний левый угол
            getWidth(), 0,  // нижний правый угол
            getWidth(), getHeight(),  // верхний правый угол
            0, getHeight()  // верхний левый угол
        });
    }
    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height); // Вызов родительского метода
        setOrigin(width / 2, height / 2); // Обновляем точку вращения
        boundingBox.setVertices(new float[]{
            0, 0,  // нижний левый угол
            width, 0,  // нижний правый угол
            width, height,  // верхний правый угол
            0, height  // верхний левый угол
        });
        updateBoundingBox();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        // Отрисовка текстуры актера
        batch.draw(region,
            getX(), getY(),               // позиция x и y
            getOriginX(), getOriginY(),   // точка вращения (центр текстуры)
            getWidth(), getHeight(),       // ширина и высота текстуры
            getScaleX(), getScaleY(),      // масштабирование по x и y
            getRotation());                // угол поворота
    }
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        updateBoundingBox();  // Обновляем границы при повороте
    }
    private void updateBoundingBox() {
        boundingBox.setPosition(getX(), getY());
        boundingBox.setOrigin(getOriginX(), getOriginY());
        boundingBox.setRotation(getRotation());
    }
    public void drawBoundingBox(ShapeRenderer shapeRenderer) {
        float[] vertices = boundingBox.getTransformedVertices();
        shapeRenderer.polygon(vertices);  // Отрисовка полигона
    }
    public void rotateTowards(float mouseX, float mouseY) {
        // Находим центр актора
        float centerX = getX() + getWidth() / 2;
        float centerY = getY() + getHeight() / 2;

        // Рассчитываем угол между центром актора и курсором
        float angle = (float) Math.toDegrees(Math.atan2(mouseY - centerY, mouseX - centerX));
        setRotation(angle);
    }
    public boolean collidesWith(BaseActor other) {
        return Intersector.overlapConvexPolygons(this.getBoundingBox(), other.getBoundingBox());
    }
    public boolean collidesWith(BulletActor other) {
        return Intersector.overlapConvexPolygons(this.getBoundingBox(), other.getCollisionPolygon());
    }
    public void act(float delta) {
        super.act(delta);
        // Проверка коллизии с другим объектом
        for (Actor other : getStage().getActors()) {
            if (other != this && other instanceof BaseActor) {
                BaseActor otherActor = (BaseActor) other;
                if (this.collidesWith(otherActor)) {
                    handleCollision(otherActor);
                }
            }
            if (other instanceof BulletActor) {
                BulletActor bullet = (BulletActor) other;
                if (this.collidesWith(bullet)) {
                    bulletCollision(this);
                }
            }
        }
        updateBoundingBox();  // Обновляем границы при каждом действии
    }
    public void handleCollision(BaseActor other) {
        System.out.println("Collision Detected!");
    }
    public void bulletCollision(BaseActor other) {
        System.out.println("die");
    }
    public Polygon getBoundingBox() {
        return boundingBox;

    }
}
