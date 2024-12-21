package com.czertilla.project_vinaigrette.stage.scene.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.czertilla.project_vinaigrette.utils.C;
import com.czertilla.project_vinaigrette.utils.Movable;
import com.czertilla.project_vinaigrette.utils.PathFinder;

import java.util.Optional;

public class EnemyActor extends BaseActor implements Movable {
    protected float hp;
    protected float death_hp = 5;
    private final Vector3
        velocity,
        control,
        acceleration,
        targetLoc;
    private BaseActor target;
    //    private CatmullRomSpline<Vector2> path;
    Vector2[] path;
    private int catmullIdx = 0;
    private Vector3 currentPathPoint;

    private PathFinder pathFinder;
    private int state = 0;

    private float friction;

    public EnemyActor(TextureRegion region, float hp) {
        super(region, new TextureAtlas("ui/cursed_slime.atlas")); // Используем Enemy.atlas для анимаций
        this.hp = hp;
        velocity = new Vector3();
        acceleration = new Vector3();
        targetLoc = new Vector3();
        control = new Vector3();
        friction = C.PLAYER_FRICTION;
        //animationHandler.setAnimation("idle", 4); // Например, Idle анимация
    }

    public void getDamage(float dmg) {
        hp -= dmg;
        System.out.println("get damaged");
        if (hp <= 0) {
//            System.out.println("is dead");
            setColor(Color.RED);
            dropPath();
            death_hp-=dmg;
        }

    }

    @Override
    void processCollision(Actor other) {
        super.processCollision(other);
        if (hp <= 0) return;
        if (other instanceof PlayerActor player) {
            player.damage(0);
        }
    }

    @Override
    public void act(float delta) {
        if (hp > 0) {
            if (pathFinder != null && path == null) {
                pathFinder.calculate();
            }
            findTarget();
        }
        update(delta);// Обновляем анимацию в каждом кадре
        super.act(delta);
    }

    public void setPath(Vector3 location) {
        setPath(new Vector2(location.x, location.y));
    }

    private void setPath(Vector2 location) {
        pathFinder = new PathFinder(this, location, 100);
        pathFinder.calculate();
    }

    private void initCatmull() {
//        this.path = new CatmullRomSpline<>(results, true);
        catmullIdx = 0;
        currentPathPoint = new Vector3();
        nextPathPoint();
    }

    public void updateAnimation() {
        // Обработка анимации смерти
        if (state > 0) {
            if (state == 1) {
                // Устанавливаем анимацию смерти при первом вызове
                animationHandler.setAnimation("death", 3); // Устанавливаем анимацию смерти
                animationHandler.setFrameDuration(0.24f); // Длительность кадров анимации смерти
                animationHandler.animation.setPlayMode(Animation.PlayMode.NORMAL); // Один цикл проигрывания
                state = 2; // Обновляем состояние, чтобы анимация не устанавливалась повторно
                return;
            }

            // Проверяем, завершилась ли анимация
            if (state == 2 && animationHandler.animation.isAnimationFinished(0.75f)) {
                // Получаем последний кадр как AtlasRegion
                TextureAtlas.AtlasRegion lastFrame = animationHandler.animation.getKeyFrame(animationHandler.animation.getAnimationDuration());

                // Устанавливаем последний кадр как единственный в новой анимации
                animationHandler.animation = new Animation<>(Float.MAX_VALUE, lastFrame);
                state = 3;
            }
            return;
        }

        // Если объект жив, обрабатываем обычные анимации
        String currStateName = animationHandler.stateName;

        // Рассчитываем направление движения
        String nextState = getStateName(control);
        if (!nextState.equals(currStateName)) animationHandler.setAnimation(nextState, 4);

        // Устанавливаем скорость кадров анимации
        animationHandler.setFrameDuration(0.13f);

        // Устанавливаем режим проигрывания анимации
        Vector2 vel2D = new Vector2(control.x, control.y);
        animationHandler.animation.setPlayMode(
            hp > 0 ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL
        );
    }

    private String getStateName(Vector3 velocity) {
        String nextState = C.State.DOWN;
        if (hp <= 0) {
            state = 1; // Устанавливаем состояние смерти
            return "death"; // Возвращаем состояние смерти
        }
        if (!velocity.isZero()) {
            if (velocity.x >= 0) {
                if (velocity.y > velocity.x)
                    nextState = C.State.UP;
                else if (velocity.y > -velocity.x)
                    nextState = C.State.RIGHT;
                else
                    nextState = C.State.DOWN;
            } else {
                if (velocity.y < velocity.x)
                    nextState = C.State.DOWN;
                else if (velocity.y < -velocity.x)
                    nextState = C.State.LEFT;
                else
                    nextState = C.State.UP;
            }
        }
        if (velocity.isZero()) nextState = "idle";
        return nextState;
    }


    private void nextPathPoint(){
//        path.valueAt(currentPathPoint, catmullIdx);
        if (catmullIdx >= path.length){
            dropPath();
            target = null;
            currentPathPoint = null;
            return;
        }
        currentPathPoint.set(path[catmullIdx], 0);
//        path.derivativeAt(currentPathPoint, catmullIdx);
        catmullIdx ++;
    }

    private void dropPath(){
        if (this.pathFinder == null) return;
        this.path = null;
        currentPathPoint = null;
        pathFinder = null;
    }

    private void findTarget() {
        if (target == null) {
            setVelocity(Vector3.Zero);
            findPlayer();
            return;
        }
        if (isTargetVisible()) {
            dropPath();
            targetLoc.set(target.getCenter());
            chase(target);
            return;
        }
        if (path == null){
            if (pathFinder == null)
                setPath(targetLoc);
            path = pathFinder.getResult();
        }
        else if (currentPathPoint == null)
            initCatmull();
        else if (getCenter().dst(currentPathPoint) < 5)
            nextPathPoint();
        findPlayer();
        if (currentPathPoint != null){
            setVelocity(currentPathPoint.cpy().sub(getCenter()).setLength(150));
        }
        else setVelocity(Vector3.Zero);
    }

    private void setNewTarget(BaseActor target){
        dropPath();
        this.target = target;
        targetLoc.set(target.getCenter());
    }

    public void chase(BaseActor actor){
        setVelocity(actor.getCenter().sub(getCenter()).setLength(200));
    }

    private boolean isTargetVisible() {
        if (target instanceof NoiseActor) return false;
        if (target == null) return false;
        return isTargetVisible(target);
    }

    private boolean isTargetVisible(BaseActor targetActor) {
        return getVisibleTargetDistance(targetActor) >= 0;
    }

    private float getVisibleTargetDistance(BaseActor targetActor) {
        return getVisibleTargetDistance(targetActor, Float.POSITIVE_INFINITY);
    }

    private float getVisibleTargetDistance(BaseActor targetActor, float minDistance) {
        Vector3 center = getCenter();
        Vector2 centerV2 = new Vector2(center.x, center.y);
        float[] vertices = targetActor.getBoundingBox().getTransformedVertices();
        for (int i = 0; i < vertices.length; i += 2) {
            Vector2 vertex = new Vector2(vertices[i], vertices[i + 1]);
            float dst = vertex.dst(centerV2);
            if (dst > 500 || dst > minDistance) continue;
            boolean isOver = false;
            for (WallActor wallActor : WallActor.walls) {
                if (Intersector.intersectSegmentRectangle(
                    centerV2,
                    vertex,
                    wallActor.boundingBox.getBoundingRectangle()
                )
                ) {
                    isOver = true;
                    break;
                }
            }
            if (isOver) continue;
            return dst;
        }
        return -1;
    }

    private void findPlayer() {
        float minDistance = Float.POSITIVE_INFINITY;
        BaseActor currentTarget = null;
        for (Actor actor : getStage().getActors()) {
            if (actor instanceof PlayerActor playerActor) {
                float distance = getVisibleTargetDistance(playerActor, minDistance);
                if (distance < 0) continue;
                currentTarget = playerActor;
                minDistance = distance;
            }
        }
        if (currentTarget == null) findNoise();
        else {
            setNewTarget(currentTarget);
        }
    }

    private void findNoise() {
        float minDistance = Float.POSITIVE_INFINITY;
        for (NoiseActor noise: NoiseActor.instances){
            Vector3 noiseLoc = noise.getCenter();
            float distance = getCenter().dst(noiseLoc);
            if (
                distance < minDistance
                    && distance <= noise.getCurrentRadius()
                    && target != noise
            ){
                minDistance = distance;
                setNewTarget(noise);
            }
        }
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        super.drawDebugBounds(shapes);
        shapes.setColor(Color.BLUE);
        if (pathFinder != null){
            if (pathFinder.destination == null)
                for (PathFinder.Node node: pathFinder.currentNodes){
                    shapes.circle(node.x, node.y, 5);
                }
            shapes.setColor(Color.RED);
            PathFinder.Node node = pathFinder.destination;
            while (node != null){
                shapes.circle(node.x, node.y, 5);
                node = node.getPrevious();
            }
        }
        if (currentPathPoint != null){
            shapes.setColor(Color.FOREST);
            shapes.circle(currentPathPoint.x, currentPathPoint.y, 5);
        }
    }

    @Override
    public void setVelocity(Vector3 velocity) {
        this.control.set(velocity);
    }

    public void addImpulse(Vector3 velocity){
        this.velocity.mulAdd(velocity, (float) 1 /80);
    }

    @Override
    public void moveTo(Vector3 destination) {
        setPosition(destination.x, destination.y);
    }

    @Override
    public void moveOn(Vector3 moving) {
        moveBy(moving.x, moving.y);
    }

    @Override
    public void setAcceleration(Vector3 acceleration) {
        this.acceleration.set(acceleration);
    }

    @Override
    public void update(float delta) {
        if (hp <= 0) control.setZero();
        float frictionForce = friction * C.G;
        updateAnimation();
        velocity.setLength(Math.max(0, velocity.len() - frictionForce * delta));
        if (velocity.len() <= C.PLAYER_MAX_SPEED) {
            velocity.mulAdd(acceleration, delta);
        }
        Gdx.app.log("VelocityDebug", "Velocity: " + control);
        moveBy((control.x ) * delta, (control.y ) * delta);
    }

    @Override
    public String toString() {
        return super.toString()+
            "\nhp: "+hp+
            "\ntarget: "+target+
            "\nt: "+targetLoc;
    }
}
