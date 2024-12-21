package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.czertilla.project_vinaigrette.utils.C;

import java.util.HashSet;
import java.util.Set;

public class BaseActor extends Actor {
    protected TextureRegion region;
    public Polygon boundingBox;
    Set<BaseActor> collides;
    AnimationHandler animationHandler;

    public class AnimationHandler{
        TextureAtlas atlas;
        Animation<TextureAtlas.AtlasRegion> animation;
        float frameDuration = 0.1f;
        float stateTime = 0f;
        protected String stateName = C.State.IDLE_DOWN;

        protected AnimationHandler(TextureAtlas atlas){
            this.atlas = atlas;
        }
        Array<TextureAtlas.AtlasRegion> getFrames(String actionName, int length){
            Array<TextureAtlas.AtlasRegion> frames = new Array<>();
            for (int i = 1; i <= length; i++) { // Подставляем количество кадров
                TextureAtlas.AtlasRegion frame = atlas.findRegion(actionName + i);
                if (frame != null) {
                    frames.add(frame);
                } else {
                    Gdx.app.error(C.Tag.ANIMATION, "frame "+i+" not found: atlas "+atlas);
                }
            }
            return frames;
        }

        public void setFrameDuration(float frameDuration) {
            this.frameDuration = frameDuration;
            animation.setFrameDuration(frameDuration);
            Gdx.app.log(C.Tag.ANIMATION, "frame dur: "+frameDuration);
        }

        public void setAnimation(String actionName, int framesCount){
            Array<TextureAtlas.AtlasRegion> frames = getFrames(actionName, framesCount);
            if (frames.size == 0) {
                Gdx.app.error(C.Tag.ANIMATION, "animation is not created: no frames");
            }
            animation = new Animation<>(frameDuration, frames);
            stateName = actionName;
        }

        public void setAnimation(String actionName, int framesCount, float frameDuration){
            setFrameDuration(frameDuration);
            setAnimation(actionName, framesCount);
        }

        void act(float delta){
            stateTime += delta;
            Gdx.app.debug(C.Tag.ANIMATION, "stateTime: "+stateTime);
            region.setRegion(animation.getKeyFrame(stateTime, true));
        }
    }

    static final BitmapFont font = new BitmapFont(){{
    }};

    public boolean collidesWith(BaseActor other) {
        boolean isCollides = collides.contains(other);
        if (other == this) return false;
        if (isCollides) return true;
        isCollides = Intersector.overlapConvexPolygons(
            this.getBoundingBox(),
            other.getBoundingBox()
        );
        if (isCollides){
            collides.add(other);
            other.collides.add(this);
        }
        return isCollides;
    }

    public BaseActor(TextureRegion region){
        this.region = region;
        collides = new HashSet<>();
        super.setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        boundingBox = new Polygon(new float[]{
            0, 0,
            getWidth(), 0,
            getWidth(), getHeight(),
            0, getHeight()
        });
    }

    public BaseActor(TextureRegion region, TextureAtlas atlas){
        this(region);
        animationHandler = new AnimationHandler(atlas);
    }

    public Vector3 getCenter(){
        return new Vector3(
            getX()+getWidth()/2,
            getY()+getHeight()/2,
            0
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // угол поворота
        if (getDebug()) {


            // Дебаг отрисовка полигона
            font.draw(batch, toString(), getX(), getY());
        }
        batch.setColor(getColor());
        // Отрисовка текстуры актера
        batch.draw(region,
            getX(), getY(),               // позиция x и y
            getOriginX(), getOriginY(),   // точка вращения (центр текстуры)
            getWidth(), getHeight(),       // ширина и высота текстуры
            getScaleX(), getScaleY(),      // масштабирование по x и y
            getRotation());

    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);

        // Вызов родительского метода
        setOrigin(width / 2, height / 2); // Обновляем точку вращения
        boundingBox.setVertices(new float[]{
            0, 0,  // нижний левый угол
            width, 0,  // нижний правый угол
            width, height,  // верхний правый угол
            0, height  // верхний левый угол
        });
        updateBoundingBox();
    }

    void checkCollisions(){
        for (Actor other: getStage().getActors()){
            if (!(other instanceof BaseActor)) continue;
            if (collidesWith((BaseActor) other)) this.processCollision(other);
        }
    }

    void processCollision(Actor other) {
        if (other instanceof WallActor wallActor) {
            wallActor.move(this);
        }
    }

    public void act(float delta){
        super.act(delta);
        if (animationHandler != null) animationHandler.act(delta);
        updateBoundingBox();
        collides.clear();
        checkCollisions();
    }

    public void setRotation(float degrees) {
        super.setRotation(degrees);
        updateBoundingBox();  // Обновляем границы при повороте
    }
    void updateBoundingBox() {
        boundingBox.setPosition(getX(), getY());
        boundingBox.setOrigin(getOriginX(), getOriginY());
        boundingBox.setRotation(getRotation());
    }

    public void rotateTowards(float x, float y) {
        // Находим центр актора
        Vector3 center = getCenter();

        // Рассчитываем угол между центром актора и курсором
        float angle = (float) Math.toDegrees(Math.atan2(y - center.y, x - center.x));
        setRotation(angle);
    }

    public Polygon getBoundingBox() {
        return boundingBox;
    }

    @Override
    public String toString() {
        return super.toString()+"#"+hashCode()+"\n"+
            getX()+":"+getY();
    }

    @Override
    public String getName() {
        return BaseActor.this.toString();
    }
}
